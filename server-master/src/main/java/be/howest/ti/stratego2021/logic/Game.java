package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;
import be.howest.ti.stratego2021.web.tokens.EncodedTextTokens;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Locale;

public class Game {

    private final String roomId;
    private final int gameNumber;
    private final Player[] players;
    private final Version version;
    private final Board board;
    private final Deque<Move> moves;
    private final Player[] losers;
    private Player currentPlayer;


    public Game(String roomId, int gameNumber, Version version, Rank[][] startConfig) {
        this.roomId = roomId;
        this.gameNumber = gameNumber;
        this.version = version;
        this.board = new Board(startConfig);
        this.players = new Player[2];
        this.moves = new LinkedList<>();
        this.losers = new Player[2];
        this.currentPlayer = Player.RED;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getGameId() {
        return roomId + getAffix();
    }

    public Version getVersion() {
        return version;
    }

    public Board getBoard() {
        return board;
    }

    public Deque<Move> getMoves() {
        return moves;
    }

    private String getAffix() {
        StringBuilder affix = new StringBuilder(String.valueOf(gameNumber));

        while (affix.length() < 4) {
            affix.insert(0, "0");
        }

        return affix.toString();
    }

    public Player[] getLosers() {
        return losers;
    }

    public Player[] setLoser(Player loser) {
        if (losers[0] == null) {
            losers[0] = loser;
        } else if (losers[0] != loser && losers[1] == null) { // Avoid forfeit x2 cheat
            losers[1] = loser;
        } else {
            throw new IllegalStateException("The game is already lost");
        }
        return losers;
    }

    public void addPlayer() {
        if (spotIsEmpty(0)) {
            players[0] = Player.RED;
            getMoves().add(new Move(Player.RED));
        } else if (spotIsEmpty(1)) {
            players[1] = Player.BLUE;
            getMoves().add(new Move(Player.BLUE));
        } else {
            throw new IllegalStateException("The game is already full");
        }
    }

    public Player getLastJoinedPlayer() {
        if (!spotIsEmpty(1)) {
            return players[1];
        } else if (!spotIsEmpty(0)) {
            return players[0];
        } else {
            throw new StrategoResourceNotFoundException("There are no players in this game");
        }
    }

    public boolean spotIsEmpty(int spot) {
        return players[spot] == null;
    }


    public String getPlayerToken(Player player) {
        return new EncodedTextTokens().createToken(getGameId(), player.toString().toUpperCase(Locale.ROOT));
    }

    public void addSecondPlayerToGame(Rank[][] startConfig) {
        if (isValidStartConfig(startConfig)) {
            addPlayer();
            board.addPlayerBlueStartConfig(startConfig);
        }
    }

    public boolean isValidStartConfig(Rank[][] startConfig) {
        return getVersionStartConfig(startConfig).equals(version);
    }

    public Version getVersionStartConfig(Rank[][] startConfig) {
        int amountOfPieces = getTotalPawnsFromStartConfig(startConfig);
        switch (amountOfPieces) {
            case 40:
                return decideOriginalOrInfiltrator(startConfig);
            case 10:
                return new Version(VersionName.DUEL);
            default:
                throw new StrategoGameRuleException("This is not a valid start configuration");
        }
    }

    public int getTotalPawnsFromStartConfig(Rank[][] startConfig) {
        int totalPieces = 0;
        for (int i = 6; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (startConfig[i][j] != null) { totalPieces++; }
            }
        }
        return totalPieces;
    }

    public Version decideOriginalOrInfiltrator(Rank[][] startConfig) {
        for (int i = 6; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (startConfig[i][j] == Rank.INFILTRATOR) { return new Version(VersionName.INFILTRATOR); }
            }
        }
        return new Version(VersionName.ORIGINAL);
    }

    public Move makeMove(Move move) {
        board.validateBoardIsLocked();
        validateIsPlayerTurn(move.getPlayer());
        Move moveOutcome = board.flipValidateAndExecuteMove(move);
        getMoves().addLast(moveOutcome);
        setLoserIfPlayersCantMove();
        setLoserIfPlayerLostFlag(moveOutcome.getPlayer(), moveOutcome.getAttack());
        currentPlayer = currentPlayer.getNextPlayer();

        return moveOutcome;
    }

    public void setLoserIfPlayersCantMove() {
        for (Player player: players) {
            if (getBoard().hasAllPawnsBlocked(player)) {
                setLoser(player);
            }
        }
    }

    private void setLoserIfPlayerLostFlag(Player player, Attack attack) {
        if (attack != null && attack.getDefender() == Rank.FLAG) {
            setLoser(player.getNextPlayer());
        }
    }

    private void validateIsPlayerTurn(Player player) {
        if (currentPlayer != player) {
            throw new StrategoGameRuleException("It is not your turn");
        }
    }
}
