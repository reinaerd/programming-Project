package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;

public class Board {

    private static final int BOARD_SIZE = 10;
    private final Pawn[][] pawns;
    private boolean isLocked;

    public Board(Rank[][] playerRedStartConfig) {
        this.pawns = new Pawn[BOARD_SIZE][BOARD_SIZE];
        this.isLocked = false;
        addPlayerRedStartConfig(playerRedStartConfig);
    }

    public void validateBoardIsLocked() {
        if (isLocked) {
            throw new IllegalStateException("The game has already ended");
        }
    }

    private void addPlayerRedStartConfig(Rank[][] playerRedStartConfig) {
        Pawn[][] playerRedPawns = getPawnConfig(playerRedStartConfig, Player.RED);

        for (int row = 6; row < BOARD_SIZE; row++) {
            System.arraycopy(playerRedPawns[row], 0, pawns[row], 0, BOARD_SIZE);
        }
    }

    public void addPlayerBlueStartConfig(Rank[][] playerBlueStartConfig) {
        Pawn[][] playerBluePawns = getPawnConfig(playerBlueStartConfig, Player.BLUE);

        for (int row = 6; row < BOARD_SIZE ; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                pawns[BOARD_SIZE - row - 1][BOARD_SIZE - col - 1] = playerBluePawns[row][col];
            }
        }
    }

    public Pawn[][] getPawnsOf(Player player) {
        if (player == Player.RED) {
            return getPawnsOfPlayerRed();
        } else {
            return getPawnsOfPlayerBlue();
        }
    }

    private Pawn[][] getPawnsOfPlayerRed() {
        return removeEnemyRanks(pawns, Player.RED);
    }

    private Pawn[][] getPawnsOfPlayerBlue() {
        Pawn[][] tempBoardConfig = copyBoardConfig(pawns);
        Pawn[][] copyOfCurrentBoard = copyBoardConfig(pawns);

        for (int row = 5; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Pawn temp = copyOfCurrentBoard[row][col];
                copyOfCurrentBoard[row][col] = tempBoardConfig[BOARD_SIZE - row - 1][BOARD_SIZE - col - 1];
                tempBoardConfig[BOARD_SIZE - row - 1][BOARD_SIZE - col - 1] = temp;
                tempBoardConfig[row][col] = copyOfCurrentBoard[row][col];
            }
        }
        return removeEnemyRanks(tempBoardConfig, Player.BLUE);
    }

    private Pawn[][] getPawnConfig(Rank[][] startConfig, Player player) {
        Pawn[][] res = new Pawn[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (startConfig[row][col] != null) {
                    res[row][col] = new Pawn(startConfig[row][col], player);
                }
            }
        }
        return res;
    }

    private Pawn[][] removeEnemyRanks(Pawn[][] board, Player player) {
        Pawn[][] filteredBoard = copyBoardConfig(board);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (filteredBoard[i][j] != null && player != filteredBoard[i][j].getPlayer()) {
                    filteredBoard[i][j] = new Pawn(player.getNextPlayer());
                }
            }
        }
        return filteredBoard;
    }

    private Pawn[][] copyBoardConfig(Pawn[][] boardConfig) {
        Pawn[][] res = new Pawn[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(boardConfig[i], 0, res[i], 0, BOARD_SIZE);
        }
        return res;
    }

    private Pawn getPawnAtPosition(Position position) {
        return pawns[position.getRow()][position.getCol()];
    }

    public Move flipValidateAndExecuteMove(Move move) {
        Move flippedMove = flipMoveIfIsFromPlayerBlue(move);
        validateMove(flippedMove);

        if (isEmptyTargetField(flippedMove.getTar())) {
            executeMove(flippedMove.getSrc(), flippedMove.getTar());
            return move;
        } else {
            return flipMoveIfIsFromPlayerBlue(executeAttack(flippedMove));
        }
    }

    private void validateMove(Move move) {
        validateSourceIsYourPawn(move.getSrc(), move.getPlayer());
        validateSourceIsNotBomb(move.getSrc());
        validateSourceIsNotFlag(move.getSrc());
        validateTargetIsNotWater(move.getTar());
        validateTargetIsNotYours(move.getSrc(), move.getTar());
        validateMoveTrajectory(move.getSrc(), move.getTar(), move);
    }

    public Move flipMoveIfIsFromPlayerBlue(Move move) {
        return flipMoveIfIsNotFromPlayer(move, Player.RED);
    }

    public Move flipMoveIfIsNotFromPlayer(Move move, Player player) {
        if (move.getSrc() != null && move.getPlayer() != player) {
            Position src = new Position(BOARD_SIZE - 1 - move.getSrc().getRow(), BOARD_SIZE - 1 - move.getSrc().getCol());
            Position tar = new Position(BOARD_SIZE - 1 - move.getTar().getRow(), BOARD_SIZE - 1 - move.getTar().getCol());
            return new Move(move.getPlayer(), src, tar, move.getInfiltrate(), move.getAttack());
        }

        return move;
    }

    private boolean isEmptyTargetField(Position target) {
        return getPawnAtPosition(target) == null;
    }

    private void executeMove(Position source, Position target) {
        pawns[target.getRow()][target.getCol()] = getAttacker(source);
        removePawnFromPosition(source);
    }

    private Move executeAttack(Move move) {
        lockBoardIfFlagIsCaptured(move.getTar());

        Pawn attacker = getAttacker(move.getSrc());
        Pawn defender = getDefender(move.getTar());
        Winner winner = getAttackWinner(attacker, defender, move);
        move.addAttack(attacker.getRank(), defender.getRank(), winner);

        lockBoardIfNoMovablePawnsLeft();

        return move;
    }

    private Winner getAttackWinner(Pawn attacker, Pawn defender, Move move) {
        if (move.getInfiltrate() == null) {
            return manageAttack(attacker, defender, move.getSrc(), move.getTar());
        } else if (move.isInEnemyTerritory()) {
            return manageInfiltration(attacker, defender, move.getInfiltrate(), move.getTar());
        }
        throw new StrategoGameRuleException("Invalid attack");
    }

    private Winner manageAttack(Pawn attacker, Pawn defender, Position source, Position target) {
        Winner winner = attacker.attack(defender);
        resolveAttack(winner, source, target);

        return winner;
    }

    private void resolveAttack(Winner winner, Position source, Position target) {
        if (winner == Winner.ATTACKER) {
            executeMove(source, target);
        } else if (winner == Winner.DEFENDER) {
            removePawnFromPosition(source);
        } else if (winner == Winner.DRAW) {
            removePawnFromPosition(source);
            removePawnFromPosition(target);
        }
    }

    private Winner manageInfiltration(Pawn attacker, Pawn defender, Rank infiltrate, Position target) {
        Winner winner = attacker.infiltrate(infiltrate, defender);
        resolveInfiltration(winner, target);

        return winner;
    }

    private void resolveInfiltration(Winner winner, Position target) {
        if (winner == Winner.ATTACKER) {
            removePawnFromPosition(target);
        }
    }

    private void lockBoardIfFlagIsCaptured(Position target) {
        if (!isEmptyTargetField(target) && getDefender(target).getRank() == Rank.FLAG) {
            isLocked = true;
        }
    }

    public void lockBoardIfNoMovablePawnsLeft() {
        if (hasAllPawnsBlocked(Player.RED) || hasAllPawnsBlocked(Player.BLUE)) {
            isLocked = true;
        }
    }

    public boolean hasAllPawnsBlocked(Player player) {
        boolean movablePawns = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Pawn currentPawn = pawns[row][col];
                if (currentPawn != null && currentPawn.isMoveable() && currentPawn.getPlayer() == player && hasVacantAdjacentFields(row, col)) {
                    movablePawns = true;
                }
            }
        }

        return !movablePawns;
    }

    private boolean hasVacantAdjacentFields(int row, int col) {
        Position up = new Position(row-1, col);
        Position down = new Position(row+1, col);
        Position right = new Position(row, col+1);
        Position left = new Position(row, col-1);

        return (up.isInBoard() && getPawnAtPosition(up) == null && !up.isWater()) ||
                (down.isInBoard() && getPawnAtPosition(down) == null && !down.isWater()) ||
                (right.isInBoard() && getPawnAtPosition(right) == null && !right.isWater()) ||
                (left.isInBoard() && getPawnAtPosition(left) == null && !left.isWater());
    }

    private void removePawnFromPosition(Position position) {
        pawns[position.getRow()][position.getCol()] = null;
    }

    private void validateMoveTrajectory(Position source, Position target, Move move) {
        if (getAttacker(source).getRank() == Rank.SCOUT) {
            validateScoutMove(move);
        } else if (!isEmptyTargetField(target) && getAttacker(source).getRank() == Rank.INFILTRATOR) {
            validateInfiltratorAttack(move);
        } else {
            validateNormalMove(move);
        }
    }

    private void validateInfiltratorAttack(Move move) {
        if (!move.isValidInfiltratorAttack()) {
            throw new StrategoGameRuleException("Invalid infiltrator move");
        }
    }

    private void validateNormalMove(Move move) {
        if (!move.isValidNormalMove()) {
            throw new StrategoGameRuleException("Invalid move");
        }
    }

    private void validateScoutMove(Move move) {
        if (!move.isValidScoutMove(pawns)) {
            throw new StrategoGameRuleException("Invalid move");
        }
    }

    private void validateSourceIsYourPawn(Position source, Player player) {
        if (getAttacker(source) == null || getAttacker(source).getPlayer() != player) {
            throw new StrategoGameRuleException("You don't have a pawn in this location");
        }
    }

    private void validateTargetIsNotWater(Position target) {
        if (target.isWater()) {
            throw new StrategoGameRuleException("You can't move to a water field");
        }
    }

    private void validateTargetIsNotYours(Position source, Position target) {
        if (!isEmptyTargetField(target) && getAttacker(source).getPlayer() == getDefender(target).getPlayer()) {
            throw new StrategoGameRuleException("You cannot attack your own pawn");
        }
    }

    private void validateSourceIsNotBomb(Position source) {
        if (getAttacker(source).getRank() == Rank.BOMB) {
            throw new StrategoGameRuleException("You can't move a Bomb");
        }
    }

    private void validateSourceIsNotFlag(Position source) {
        if (getAttacker(source).getRank() == Rank.FLAG) {
            throw new StrategoGameRuleException("You can't move the Flag");
        }
    }

    private Pawn getAttacker(Position source) {
        return getPawnAtPosition(source);
    }

    private Pawn getDefender(Position target) {
        return getPawnAtPosition(target);
    }
}
