package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Rank[][] redConfig;
    Rank[][] blueConfig;
    Rank[][] redConfigInfiltrator;
    Rank[][] blueConfigInfiltrator;


    @BeforeEach
    void setup() {
        redConfig = new Rank[][] {
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {Rank.BOMB, Rank.SCOUT, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.BOMB, Rank.SCOUT, Rank.SERGEANT, Rank.MAJOR, Rank.SCOUT, Rank.SCOUT},
            {Rank.GENERAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.MINER, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.SERGEANT},
            {Rank.COLONEL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
            {Rank.COLONEL, Rank.MINER, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.FLAG, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.SERGEANT}
        };

        blueConfig = new Rank[][] {
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {Rank.LIEUTENANT, Rank.SCOUT, Rank.MAJOR, Rank.MINER, Rank.BOMB, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.SPY, Rank.SCOUT},
            {Rank.MARSHAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SERGEANT, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
            {Rank.GENERAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
            {Rank.COLONEL, Rank.CAPTAIN, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.FLAG, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.SERGEANT}
        };

        redConfigInfiltrator = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.INFILTRATOR, Rank.INFILTRATOR, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
        };

        blueConfigInfiltrator = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, Rank.SERGEANT, null},
                {null, null, null, null, null, null, null, null, null, Rank.COLONEL},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.MARSHAL, null, null, null, null, null, null, null, null, null}
        };
    }

    @Test
    void addPlayerRedStartConfig() {
        Board board = new Board(redConfig);
        Pawn[][] config = board.getPawnsOf(Player.RED);

        assertEquals(new Pawn(Rank.BOMB, Player.RED), config[6][0]);
    }

    @Test
    void addPlayerBlueStartConfig() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Pawn[][] config = board.getPawnsOf(Player.BLUE);

        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), config[6][0]);
    }

    @Test
    void getBoardForPlayerRed() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Pawn[][] config = board.getPawnsOf(Player.RED);

        assertEquals(new Pawn(Rank.BOMB, Player.RED), config[6][0]);
        assertEquals(new Pawn(null, Player.BLUE), config[3][0]);
    }

    @Test
    void getBoardForPlayerBlue() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Pawn[][] config = board.getPawnsOf(Player.BLUE);

        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), config[6][0]);
        assertEquals(new Pawn(null, Player.RED), config[3][0]);
    }

    @Test
    void validateSourceIsYourPawn() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(5, 0);
        Position tar = new Position(4, 0);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateSourceIsNotBomb() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 0);
        Position tar = new Position(5, 0);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateSourceIsNotFlag() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 4);
        Position tar = new Position(5, 4);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateTargetIsNotWater() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 3);
        Position tar = new Position(5, 3);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateNormalMoveDiagonal() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 0);
        Position tar = new Position(5, 1);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateScoutMoveOverWater() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(5, 8);
        Position tar = new Position(5, 5);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void validateScoutMoveOverPiece() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 7);
        Position tar = new Position(3, 7);
        Move move = new Move(Player.RED, src, tar);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(move)
        );
    }

    @Test
    void infiltratorMovesOneTileInNormalTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][0]);

        Move m2 = new Move(Player.BLUE, new Position(9, 0), new Position(9, 1));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][1]);

        Move m3 = new Move(Player.RED, new Position(6, 1), new Position(4, 0));

        assertThrows(StrategoGameRuleException.class, () -> board.flipValidateAndExecuteMove(m3));
    }

    @Test
    void infiltratorMovesOneTileInEnemyTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][0]);

        Move m2 = new Move(Player.BLUE, new Position(9, 0), new Position(9, 1));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][1]);

        Move m3 = new Move(Player.RED, new Position(5, 0), new Position(4, 0));
        board.flipValidateAndExecuteMove(m3);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[4][0]);

        Move m4 = new Move(Player.BLUE, new Position(9, 1), new Position(9, 2));
        board.flipValidateAndExecuteMove(m4);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][2]);

        Move m5 = new Move(Player.RED, new Position(4, 0), new Position(3, 0));
        board.flipValidateAndExecuteMove(m5);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[3][0]);

        Move m6 = new Move(Player.BLUE, new Position(7, 9), new Position(7, 8));
        board.flipValidateAndExecuteMove(m6);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][8]);

        Move m7 = new Move(Player.RED, new Position(3, 0), new Position(2, 0));
        board.flipValidateAndExecuteMove(m7);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[2][0]);

        Move m8 = new Move(Player.BLUE, new Position(9, 2), new Position(9, 3));
        board.flipValidateAndExecuteMove(m8);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][3]);

        Move m9 = new Move(Player.RED, new Position(2, 0), new Position(0, 0));

        assertThrows(StrategoGameRuleException.class, () -> board.flipValidateAndExecuteMove(m9));
    }

    @Test
    void infiltratorAttacksOneTileInNormalTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 1), new Position(5, 1));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][1]);

        Move m2 = new Move(Player.BLUE, new Position(6, 8), new Position(5, 8));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[5][8]);

        Move m3 = new Move(Player.RED, new Position(5, 1), new Position(4, 1));
        board.flipValidateAndExecuteMove(m3);

        // Infiltrator attacks 1 tile away and loses
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[4][1]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[5][8]);
    }

    @Test
    void infiltratorAttacksTwoTilesInEnemyTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 1), new Position(5, 1));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][1]);

        Move m2 = new Move(Player.BLUE, new Position(6, 8), new Position(7, 8));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][8]);

        Move m3 = new Move(Player.RED, new Position(5, 1), new Position(4, 1));
        board.flipValidateAndExecuteMove(m3);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[4][1]);

        Move m4 = new Move(Player.BLUE, new Position(7, 8), new Position(8, 8));
        board.flipValidateAndExecuteMove(m4);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[8][8]);

        Move m5 = new Move(Player.RED, new Position(4, 1), new Position(3, 1));
        board.flipValidateAndExecuteMove(m5);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[3][1]);

        Move m6 = new Move(Player.BLUE, new Position(9, 0), new Position(9, 1));
        board.flipValidateAndExecuteMove(m6);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][1]);

        Move m7 = new Move(Player.RED, new Position(3, 1), new Position(1, 1));
        board.flipValidateAndExecuteMove(m7);

        // Infiltrator attacks 2 tiles away and loses
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[1][1]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[8][8]);
    }

    @Test
    void infiltratorInfiltratesInNormalTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 1), new Position(5, 1));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][1]);

        Move m2 = new Move(Player.BLUE, new Position(6, 8), new Position(5, 8));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[5][8]);

        Move m3 = new Move(Player.RED, new Position(5, 1), new Position(4, 1), Rank.SERGEANT);

        assertThrows(
            StrategoGameRuleException.class,
            () -> board.flipValidateAndExecuteMove(m3)
        );
    }

    @Test
    void infiltratorInfiltratesInEnemyTerritory() {
        Board board = new Board(redConfigInfiltrator);
        board.addPlayerBlueStartConfig(blueConfigInfiltrator);

        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][0]);
        assertEquals(new Pawn(Rank.COLONEL, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][9]);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        Move m1 = new Move(Player.RED, new Position(6, 1), new Position(5, 1));
        board.flipValidateAndExecuteMove(m1);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[5][1]);

        Move m2 = new Move(Player.BLUE, new Position(6, 8), new Position(7, 8));
        board.flipValidateAndExecuteMove(m2);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[7][8]);

        Move m3 = new Move(Player.RED, new Position(5, 1), new Position(4, 1));
        board.flipValidateAndExecuteMove(m3);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[4][1]);

        Move m4 = new Move(Player.BLUE, new Position(7, 8), new Position(8, 8));
        board.flipValidateAndExecuteMove(m4);
        assertEquals(new Pawn(Rank.SERGEANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[8][8]);

        Move m5 = new Move(Player.RED, new Position(4, 1), new Position(3, 1));
        board.flipValidateAndExecuteMove(m5);
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[3][1]);

        Move m6 = new Move(Player.BLUE, new Position(9, 0), new Position(9, 1));
        board.flipValidateAndExecuteMove(m6);
        assertEquals(new Pawn(Rank.MARSHAL, Player.BLUE), board.getPawnsOf(Player.BLUE)[9][1]);

        Move m7 = new Move(Player.RED, new Position(3, 1), new Position(1, 1), Rank.SERGEANT);
        board.flipValidateAndExecuteMove(m7);

        // Infiltrator attacks 2 tiles away and wins
        assertEquals(new Pawn(Rank.INFILTRATOR, Player.RED), board.getPawnsOf(Player.RED)[3][1]);
        assertNull(board.getPawnsOf(Player.BLUE)[8][8]);
    }

    @Test
    void executeRedMove() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 1);
        Position tar = new Position(5, 1);

        Pawn[][] config = board.getPawnsOf(Player.RED);
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), config[6][1]);
        assertNull(config[5][0]);

        Move moveSent = new Move(Player.RED, src, tar);
        Move moveReturned = board.flipValidateAndExecuteMove(moveSent);

        // Confirm information of the returned Move
        assertEquals(moveSent.getPlayer(), moveReturned.getPlayer());
        assertEquals(moveSent.getSrc(), moveReturned.getSrc());
        assertEquals(moveSent.getTar(), moveReturned.getTar());
        assertNull(moveReturned.getAttack());

        Pawn[][] configAfterMove = board.getPawnsOf(Player.RED);
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), configAfterMove[5][1]);
        assertNull(configAfterMove[6][1]);
    }

    @Test
    void executeRedAttackWinnerAttacker() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Attacker
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[6][1]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][8]);

        // Defender
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][1]);
        assertEquals(new Pawn(Rank.SPY, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][8]);

        // Execute move
        Move moveSent = new Move(Player.RED, new Position(6, 1), new Position(3, 1));
        Move moveReturned = board.flipValidateAndExecuteMove(moveSent);

        // Confirm information of the returned Move
        assertEquals(moveSent.getPlayer(), moveReturned.getPlayer());
        assertEquals(moveSent.getSrc(), moveReturned.getSrc());
        assertEquals(moveSent.getTar(), moveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.SPY, Winner.ATTACKER), moveReturned.getAttack());

        // Confirm attacker outcome
        assertNull(board.getPawnsOf(Player.RED)[6][1]);
        assertNull(board.getPawnsOf(Player.BLUE)[3][8]);

        // Confirm defender outcome
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[3][1]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[6][8]);
    }

    @Test
    void executeRedAttackWinnerDefender() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 9);
        Position tar = new Position(3, 9);

        Pawn[][] redConfig = board.getPawnsOf(Player.RED);
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), redConfig[6][9]); // Source
        assertEquals(new Pawn(null, Player.BLUE), redConfig[3][9]); // Target

        Pawn[][] blueConfig = board.getPawnsOf(Player.BLUE);
        assertEquals(new Pawn(null, Player.RED), blueConfig[3][0]); // Source
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), blueConfig[6][0]); // Target

        Move moveSent = new Move(Player.RED, src, tar);
        Move moveReturned = board.flipValidateAndExecuteMove(moveSent);

        // Confirm information of the returned Move
        assertEquals(moveSent.getPlayer(), moveReturned.getPlayer());
        assertEquals(moveSent.getSrc(), moveReturned.getSrc());
        assertEquals(moveSent.getTar(), moveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.LIEUTENANT, Winner.DEFENDER), moveReturned.getAttack());

        Pawn[][] redConfigAfterMove = board.getPawnsOf(Player.RED);
        assertNull(redConfigAfterMove[6][9]);                            // Source
        assertEquals(new Pawn(null, Player.BLUE), redConfig[3][9]); // Target

        Pawn[][] blueConfigAfterMove = board.getPawnsOf(Player.BLUE);
        assertNull(blueConfigAfterMove[3][0]);                                          // Source
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), blueConfigAfterMove[6][0]); // Target
    }

    @Test
    void executeRedAttackWinnerDraw() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);
        Position src = new Position(6, 8);
        Position tar = new Position(3, 8);

        Pawn[][] redConfig = board.getPawnsOf(Player.RED);
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), redConfig[6][8]); // Source
        assertEquals(new Pawn(null, Player.BLUE), redConfig[3][8]); // Target

        Pawn[][] blueConfig = board.getPawnsOf(Player.BLUE);
        assertEquals(new Pawn(null, Player.RED), blueConfig[3][1]); // Source
        assertEquals(new Pawn(Rank.SCOUT, Player.BLUE), blueConfig[6][1]); // Target

        Move moveSent = new Move(Player.RED, src, tar);
        Move moveReturned = board.flipValidateAndExecuteMove(moveSent);

        // Confirm information of the returned Move
        assertEquals(moveSent.getPlayer(), moveReturned.getPlayer());
        assertEquals(moveSent.getSrc(), moveReturned.getSrc());
        assertEquals(moveSent.getTar(), moveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.SCOUT, Winner.DRAW), moveReturned.getAttack());

        Pawn[][] redConfigAfterMove = board.getPawnsOf(Player.RED);
        assertNull(redConfigAfterMove[6][8]); // Source
        assertNull(redConfigAfterMove[3][8]); // Target

        Pawn[][] blueConfigAfterMove = board.getPawnsOf(Player.BLUE);
        assertNull(blueConfigAfterMove[3][1]); // Source
        assertNull(blueConfigAfterMove[6][1]); // Target
    }

    @Test
    void executeRedExplodesBomb() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Red's view
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[6][5]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][5]);

        // Blue's view
        assertEquals(new Pawn(Rank.BOMB, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][4]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][4]);

        // Execute move
        Move moveSent = new Move(Player.RED, new Position(6, 5), new Position(3, 5));
        Move moveReturned = board.flipValidateAndExecuteMove(moveSent);

        // Confirm information of the returned Move
        assertEquals(moveSent.getPlayer(), moveReturned.getPlayer());
        assertEquals(moveSent.getSrc(), moveReturned.getSrc());
        assertEquals(moveSent.getTar(), moveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.BOMB, Winner.DEFENDER), moveReturned.getAttack());

        // Confirm outcome from Red's view
        assertNull(board.getPawnsOf(Player.RED)[6][5]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][5]);

        // Confirm outcome from Blue's view
        assertEquals(new Pawn(Rank.BOMB, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][4]);
        assertNull(board.getPawnsOf(Player.BLUE)[3][4]);
    }

    @Test
    void executeBlueMove() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Move 1: red moves his scout
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[6][9]);
        Move redMove1 = new Move(Player.RED, new Position(6, 9), new Position(5, 9));
        board.flipValidateAndExecuteMove(redMove1);

        // Move 2: blue moves his lieutenant
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][0]);
        Move blueMoveSent = new Move(Player.BLUE, new Position(6, 0), new Position(5, 0));
        Move blueMoveReturned = board.flipValidateAndExecuteMove(blueMoveSent);

        // Confirm information of the returned Move
        assertEquals(blueMoveSent.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMoveSent.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMoveSent.getTar(), blueMoveReturned.getTar());
        assertNull(blueMoveReturned.getAttack());

        // Confirm moving of red piece in red config
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[5][9]);
        assertNull(board.getPawnsOf(Player.RED)[3][9]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[4][9]);

        // Confirm moving of blue lieutenant in blue config
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[5][0]);
        assertNull(board.getPawnsOf(Player.BLUE)[3][0]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[4][0]);
    }

    @Test
    void executeBlueAttackWinnerAttacker() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Move 1: Red's Scout MOVES in front of Blue's Lieutenant
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[6][9]);
        Move redMove1 = new Move(Player.RED, new Position(6, 9), new Position(4, 9));
        board.flipValidateAndExecuteMove(redMove1);

        // Move 2: Blue's Lieutenant ATTACKS the Red's Scout
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][0]);
        Move blueMoveSent = new Move(Player.BLUE, new Position(6, 0), new Position(5, 0));
        Move blueMoveReturned = board.flipValidateAndExecuteMove(blueMoveSent);

        // Confirm information of the returned Move
        assertEquals(blueMoveSent.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMoveSent.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMoveSent.getTar(), blueMoveReturned.getTar());
        assertEquals(new Attack(Rank.LIEUTENANT, Rank.SCOUT, Winner.ATTACKER), blueMoveReturned.getAttack());

        // Confirm deletion of red piece in red config
        assertNull(board.getPawnsOf(Player.RED)[6][9]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[4][9]);

        // Confirm moving of blue lieutenant after attack in blue config
        assertNull(board.getPawnsOf(Player.BLUE)[6][0]);
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), board.getPawnsOf(Player.BLUE)[5][0]);
    }

    @Test
    void executeBlueAttackWinnerDefender() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Blue's view
        assertEquals(new Pawn(Rank.SCOUT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][9]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][9]);

        // Red's view
        assertEquals(new Pawn(Rank.BOMB, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][0]);

        // Execute move 1 (red moves first)
        Move redMove = new Move(Player.RED, new Position(6, 9), new Position(5, 9));
        board.flipValidateAndExecuteMove(redMove);

        // Execute move 2
        Move blueMove = new Move(Player.BLUE, new Position(6, 9), new Position(3, 9));
        Move blueMoveReturned = board.flipValidateAndExecuteMove(blueMove);

        // Confirm information of the returned Move
        assertEquals(blueMove.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMove.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMove.getTar(), blueMoveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.BOMB, Winner.DEFENDER), blueMoveReturned.getAttack());

        // Confirm outcome from Blue's view
        assertNull(board.getPawnsOf(Player.BLUE)[6][9]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][9]);

        // Confirm outcome from Red's view
        assertEquals(new Pawn(Rank.BOMB, Player.RED), board.getPawnsOf(Player.RED)[6][0]);
        assertNull(board.getPawnsOf(Player.RED)[3][0]);
    }

    @Test
    void executeBlueAttackWinnerDraw() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Blue's view
        assertEquals(new Pawn(Rank.SCOUT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][1]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][1]);

        // Red's view
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), board.getPawnsOf(Player.RED)[6][8]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][8]);

        // Execute move 1 (red moves first)
        Move redMove = new Move(Player.RED, new Position(6, 9), new Position(5, 9));
        board.flipValidateAndExecuteMove(redMove);

        // Execute move 2
        Move blueMove = new Move(Player.BLUE, new Position(6, 1), new Position(3, 1));
        Move blueMoveReturned = board.flipValidateAndExecuteMove(blueMove);

        // Confirm information of the returned Move
        assertEquals(blueMove.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMove.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMove.getTar(), blueMoveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.SCOUT, Winner.DRAW), blueMoveReturned.getAttack());

        // Confirm outcome from Blue's view
        assertNull(board.getPawnsOf(Player.BLUE)[6][1]);
        assertNull(board.getPawnsOf(Player.BLUE)[3][1]);

        // Confirm outcome from Red's view
        assertNull(board.getPawnsOf(Player.RED)[6][8]);
        assertNull(board.getPawnsOf(Player.RED)[3][8]);
    }

    @Test
    void executeBlueExplodesBomb() {
        Board board = new Board(redConfig);
        board.addPlayerBlueStartConfig(blueConfig);

        // Blue's view
        assertEquals(new Pawn(Rank.SCOUT, Player.BLUE), board.getPawnsOf(Player.BLUE)[6][5]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][5]);

        // Red's view
        assertEquals(new Pawn(Rank.BOMB, Player.RED), board.getPawnsOf(Player.RED)[6][4]);
        assertEquals(new Pawn(null, Player.BLUE), board.getPawnsOf(Player.RED)[3][4]);

        // Execute move 1 (red moves first)
        Move redMove = new Move(Player.RED, new Position(6, 9), new Position(5, 9));
        board.flipValidateAndExecuteMove(redMove);

        // Execute move 2
        Move blueMove = new Move(Player.BLUE, new Position(6, 5), new Position(3, 5));
        Move blueMoveReturned = board.flipValidateAndExecuteMove(blueMove);

        // Confirm information of the returned Move
        assertEquals(blueMove.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMove.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMove.getTar(), blueMoveReturned.getTar());
        assertEquals(new Attack(Rank.SCOUT, Rank.BOMB, Winner.DEFENDER), blueMoveReturned.getAttack());

        // Confirm outcome from Blue's view
        assertNull(board.getPawnsOf(Player.BLUE)[6][5]);
        assertEquals(new Pawn(null, Player.RED), board.getPawnsOf(Player.BLUE)[3][5]);

        // Confirm outcome from Red's view
        assertEquals(new Pawn(Rank.BOMB, Player.RED), board.getPawnsOf(Player.RED)[6][4]);
        assertNull(board.getPawnsOf(Player.RED)[3][4]);
    }
}
