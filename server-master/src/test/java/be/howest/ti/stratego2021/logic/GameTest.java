package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private int gameNumber;
    private String roomId;
    private String gameId;
    private Game game;
    private Rank[][] startConfig;
    private Rank[][] duelStartConfig;
    private Rank[][] originalStartConfig;
    private Rank[][] infiltratorStartConfig;
    private Rank[][] miniStartConfig;
    private Rank[][] tinyStartConfig;
    private Rank[][] noMoveablePawnsConfig;
    private Rank[][] flagCapturedConfig;
    private Rank[][] blockedConfig;
    private Version version;

    @BeforeEach
    void setUp() {
        gameNumber = 1;
        roomId = "room23";
        gameId = roomId + "000" + gameNumber;
        version = new Version(VersionName.ORIGINAL);

        startConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, Rank.CAPTAIN, null, null, null, Rank.COLONEL, null, null, null, null},
                {null, Rank.CAPTAIN, null, null, null, null, null, null, null, null},
                {Rank.SCOUT, null, null, null, null,Rank.SERGEANT, null, null, null, null},
                {Rank.MARSHAL, Rank.CAPTAIN, null, null, null,Rank.COLONEL, null, null, null, null}};

        duelStartConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.SCOUT, null, null, Rank.MINER, Rank.MINER, Rank.GENERAL, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.SCOUT, null, null, Rank.SPY, null, null, null, null, null, null},
                {Rank.MARSHAL, Rank.BOMB, null, Rank.FLAG, null,Rank.BOMB, null, null, null, null}};

        originalStartConfig = new Rank[][] {
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.MINER, Rank.MINER, Rank.GENERAL, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
            {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
            {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SPY, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
            {Rank.MARSHAL, Rank.BOMB, Rank.SCOUT, Rank.FLAG, Rank.SCOUT,Rank.BOMB, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT}};

        infiltratorStartConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.MINER, Rank.MINER, Rank.GENERAL, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.FLAG},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SPY, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
                {Rank.MARSHAL, Rank.BOMB, Rank.SCOUT, Rank.FLAG, Rank.SCOUT,Rank.BOMB, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.INFILTRATOR}};

        miniStartConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.MARSHAL, Rank.CAPTAIN, null, null, null, Rank.COLONEL, null, null, null, null}};

        tinyStartConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.MARSHAL, null, null, null, null, Rank.COLONEL, null, null, null, null}};

        noMoveablePawnsConfig = new Rank[][] {{null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.FLAG},
                {Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB},
                {Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB},
                {Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB,Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.BOMB}};

        flagCapturedConfig = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.MINER, Rank.MINER, Rank.GENERAL, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.FLAG},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
                {Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SPY, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT},
                {Rank.MARSHAL, Rank.BOMB, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT,Rank.BOMB, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT}};

        blockedConfig = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.BOMB, Rank.BOMB, Rank.MAJOR, Rank.MINER, Rank.BOMB, Rank.BOMB, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.BOMB},
                {Rank.MARSHAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SERGEANT, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.GENERAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.COLONEL, Rank.CAPTAIN, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.FLAG, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.SERGEANT}
        };

        game = new Game(roomId, gameNumber, version, originalStartConfig);
    }


    @Test
    void getGameId() {
        assertEquals(gameId, game.getGameId());
    }

    @Test
    void getRoomId() {
        assertEquals(roomId, game.getRoomId());
    }

    @Test
    void getVersion() {
        assertEquals(version, game.getVersion());
    }

    @Test
    void addPlayer() {
        assertTrue(game.spotIsEmpty(0));
        assertTrue(game.spotIsEmpty(1));

        game.addPlayer();
        assertEquals(Player.RED, game.getLastJoinedPlayer());
        assertFalse(game.spotIsEmpty(0));

        game.addPlayer();
        assertEquals(Player.BLUE, game.getLastJoinedPlayer());
        assertFalse(game.spotIsEmpty(0));
        assertFalse(game.spotIsEmpty(1));

        assertThrows(IllegalStateException.class, () -> game.addPlayer());
    }

    @Test
    void getPlayerToken() {
        assertEquals("rpqp68678:7]QQ", game.getPlayerToken(Player.RED));
        assertEquals("rpqp68678:7MXbS", game.getPlayerToken(Player.BLUE));
    }

    @Test
    void addSecondPlayer() {
        assertTrue(game.spotIsEmpty(0));
        assertTrue(game.spotIsEmpty(1));

        game.addPlayer();
        assertFalse(game.spotIsEmpty(0));
        assertTrue(game.spotIsEmpty(1));

        game.addSecondPlayerToGame(originalStartConfig);
        assertFalse(game.spotIsEmpty(0));
        assertFalse(game.spotIsEmpty(1));

        assertEquals(Player.BLUE, game.getLastJoinedPlayer());
    }

    @Test
    void getMoves() {
        game.addPlayer();
        assertEquals(1, game.getMoves().size());
        assertEquals(new Move(Player.RED), game.getMoves().getLast());

        game.addSecondPlayerToGame(originalStartConfig);
        assertEquals(2, game.getMoves().size());
        assertEquals(new Move(Player.BLUE), game.getMoves().getLast());

        // Player RED moves
        Position src1 = new Position(6, 0);
        Position tar1 = new Position(5, 0);
        Move move1 = new Move(Player.RED, src1, tar1);
        game.makeMove(move1);
        assertEquals(3, game.getMoves().size());
        assertEquals(move1, game.getMoves().getLast());

        // Player BLUE moves
        Position src2 = new Position(6, 9);
        Position tar2 = new Position(5, 9);
        Move move2 = new Move(Player.BLUE, src2, tar2);
        game.makeMove(move2);
        assertEquals(4, game.getMoves().size());
        assertEquals(move2, game.getMoves().getLast());

        // Player RED moves
        Position src3 = new Position(5, 0);
        Position tar3 = new Position(4, 0);
        Move move3 = new Move(Player.RED, src3, tar3);
        game.makeMove(move3);
        assertEquals(5, game.getMoves().size());
        assertEquals(move3, game.getMoves().getLast());
    }

    @Test
    void getTotalPawnsFromStartConfig() {
        Version duel = new Version(VersionName.DUEL);
        Game gameDuel = new Game(roomId, gameNumber, duel, duelStartConfig);
        Version original = new Version(VersionName.ORIGINAL);
        Game gameOriginal = new Game(roomId, gameNumber, original, originalStartConfig);


        assertEquals(10, gameDuel.getTotalPawnsFromStartConfig(duelStartConfig));
        assertEquals(40, gameOriginal.getTotalPawnsFromStartConfig(originalStartConfig));
        assertThrows(StrategoGameRuleException.class, ()->
                game.isValidStartConfig(startConfig));
    }

    @Test
    void isValidStartConfigDuel() {
        Version duel = new Version(VersionName.DUEL);
        Game gameDuel = new Game(roomId, gameNumber, duel, duelStartConfig);

        assertTrue(gameDuel.isValidStartConfig(duelStartConfig));
    }

    @Test
    void isValidStartConfigOriginal() {
        Version original = new Version(VersionName.ORIGINAL);
        Game gameOriginal = new Game(roomId, gameNumber, original, originalStartConfig);

        assertTrue(gameOriginal.isValidStartConfig(originalStartConfig));
    }

    @Test
    void isValidInfiltratorStartConfig() {
        Version infiltrator = new Version(VersionName.INFILTRATOR);
        Game gameInfiltrator = new Game(roomId, gameNumber, infiltrator, infiltratorStartConfig);

        assertTrue(gameInfiltrator.isValidStartConfig(infiltratorStartConfig));
    }

    @Test
    void decideOriginalOrInfiltrator() {
        Version original = new Version(VersionName.ORIGINAL);
        Game gameOriginal = new Game(roomId, gameNumber, original, originalStartConfig);

        Version infiltrator = new Version(VersionName.INFILTRATOR);
        Game gameInfiltrator = new Game(roomId, gameNumber, infiltrator, infiltratorStartConfig);

        assertEquals(original, gameOriginal.decideOriginalOrInfiltrator(originalStartConfig));
        assertEquals(infiltrator, gameInfiltrator.decideOriginalOrInfiltrator(infiltratorStartConfig));
    }

    @Test
    void makeMove() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));
        Move move2 = new Move(Player.BLUE, new Position(6, 1), new Position(5, 1));

        game.makeMove(move1);
        Move moveReturned = game.makeMove(move2);

        assertEquals(Player.BLUE, moveReturned.getPlayer());
        assertEquals(new Position(6, 1), moveReturned.getSrc());
        assertEquals(new Position(5, 1), moveReturned.getTar());
        assertNull(moveReturned.getInfiltrate());
        assertNull(moveReturned.getAttack());
    }

    @Test
    void makeMoveWithInfiltrator() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0), Rank.CAPTAIN);

        Move moveReturned = game.makeMove(move1);

        assertEquals(Player.RED, moveReturned.getPlayer());
        assertEquals(new Position(6, 0), moveReturned.getSrc());
        assertEquals(new Position(5, 0), moveReturned.getTar());
        assertEquals(Rank.CAPTAIN, moveReturned.getInfiltrate());
        assertNull(moveReturned.getAttack());
    }

    @Test
    void makeMoveWithAttack() {
        Rank[][] redConfig = new Rank[][] {
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

        Rank[][] blueConfig = new Rank[][] {
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

        Game game2 = new Game("roomId", 0, new Version(VersionName.ORIGINAL), redConfig);
        game2.addSecondPlayerToGame(blueConfig);

        // Move 1: Red's Scout MOVES in front of Blue's Lieutenant
        assertEquals(new Pawn(Rank.SCOUT, Player.RED), game2.getBoard().getPawnsOf(Player.RED)[6][9]);
        Move redMove1 = new Move(Player.RED, new Position(6, 9), new Position(4, 9));
        game2.makeMove(redMove1);

        // Move 2: Blue's Lieutenant ATTACKS the Red's Scout
        assertEquals(new Pawn(Rank.LIEUTENANT, Player.BLUE), game2.getBoard().getPawnsOf(Player.BLUE)[6][0]);
        Move blueMoveSent = new Move(Player.BLUE, new Position(6, 0), new Position(5, 0));
        Move blueMoveReturned = game2.makeMove(blueMoveSent);

        // Confirm information of the returned Move
        assertEquals(blueMoveSent.getPlayer(), blueMoveReturned.getPlayer());
        assertEquals(blueMoveSent.getSrc(), blueMoveReturned.getSrc());
        assertEquals(blueMoveSent.getTar(), blueMoveReturned.getTar());
        assertEquals(new Attack(Rank.LIEUTENANT, Rank.SCOUT, Winner.ATTACKER), blueMoveReturned.getAttack());

        // Confirm information stored in Moves
        Move lastMove = game2.getMoves().getLast();
        assertEquals(blueMoveSent.getPlayer(), lastMove.getPlayer());
        assertEquals(blueMoveSent.getSrc(), lastMove.getSrc());
        assertEquals(blueMoveSent.getTar(), lastMove.getTar());
        assertEquals(new Attack(Rank.LIEUTENANT, Rank.SCOUT, Winner.ATTACKER), lastMove.getAttack());
    }

    @Test
    void makeMoveValidatesTrajectory() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move = new Move(Player.RED, new Position(6, 0), new Position(5, 1));

        assertThrows(StrategoGameRuleException.class, () -> game.makeMove(move));
    }

    @Test
    void makeMoveValidatesWater() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move = new Move(Player.RED, new Position(6, 2), new Position(5, 2));

        assertThrows(StrategoGameRuleException.class, () -> game.makeMove(move));
    }

    @Test
    void makeMoveValidatesTurn() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));
        Move move2 = new Move(Player.RED, new Position(6, 1), new Position(5, 1));

        game.makeMove(move1);

        assertThrows(StrategoGameRuleException.class, () -> game.makeMove(move2));
    }

    @Test
    void makeMoveValidatesTurnIsKeptAfterException() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);

        Move move1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));
        Move move2 = new Move(Player.BLUE, new Position(6, 0), new Position(5, 1));

        game.makeMove(move1);
        assertThrows(StrategoGameRuleException.class, () -> game.makeMove(move2));

        Move move3 = new Move(Player.BLUE, new Position(6, 1), new Position(5, 1));
        Move moveResult = game.makeMove(move3);

        assertEquals(move3.getSrc().getRow(), moveResult.getSrc().getRow());
    }

    @Test
    void getLosersEmpty() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        Player[] losers = game.getLosers();

        assertNull(losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void getLosers() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        game.setLoser(Player.RED);
        Player[] losers = game.getLosers();

        assertEquals(Player.RED, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void getLosersBoth() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        game.setLoser(Player.RED);
        game.setLoser(Player.BLUE);
        Player[] losers = game.getLosers();

        assertEquals(Player.RED, losers[0]);
        assertEquals(Player.BLUE, losers[1]);
    }

    @Test
    void setLosers() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        Player[] losers = game.setLoser(Player.RED);

        assertEquals(Player.RED, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void setLosersBoth() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        game.setLoser(Player.RED);
        Player[] losers = game.setLoser(Player.BLUE);

        assertEquals(Player.RED, losers[0]);
        assertEquals(Player.BLUE, losers[1]);
    }

    @Test
    void setLosersRepeated() {
        game.addPlayer();
        game.addSecondPlayerToGame(originalStartConfig);
        game.setLoser(Player.RED);

        assertThrows(IllegalStateException.class, () -> game.setLoser(Player.RED));
    }

    @Test
    void blockedBoardConfigBlue() {
        game.addPlayer();
        game.addSecondPlayerToGame(blockedConfig);

        Player[] losers = game.getLosers();
        Move move1 = new Move(Player.RED, new Position(6, 0), new Position(5, 0));

        game.makeMove(move1);

        assertEquals(Player.BLUE, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void blockedBoardConfigRed() {
        Game game2 = new Game("roomId", 0, new Version(VersionName.ORIGINAL), blockedConfig);
        game2.addSecondPlayerToGame(originalStartConfig);
        game2.setLoserIfPlayersCantMove();
        Player[] losers = game2.getLosers();


        assertEquals(Player.RED, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void blockedBoardConfigDraw() {
        Game game2 = new Game("roomId", 0, new Version(VersionName.ORIGINAL), blockedConfig);

        game2.addPlayer();
        game2.addSecondPlayerToGame(blockedConfig);
        game2.setLoserIfPlayersCantMove();

        Player[] losers = game2.getLosers();
        System.err.println(Arrays.toString(game2.getLosers()));
        assertEquals(Player.RED, losers[0]);
        assertEquals(Player.BLUE, losers[1]);
    }

    @Test
    void testNoValidMoveablePawns() {
        Version original = new Version(VersionName.ORIGINAL);
        Game gameNoMoves = new Game(roomId, gameNumber, original, originalStartConfig);

        Position src1 = new Position(6, 0);
        Position tar1 = new Position(3, 0);
        Move move1 = new Move(Player.RED, src1, tar1);

        gameNoMoves.addPlayer();
        gameNoMoves.addSecondPlayerToGame(noMoveablePawnsConfig);

        assertThrows(IllegalStateException.class, ()->
                gameNoMoves.makeMove(move1));
    }

    @Test
    void flagHasBeenCaptured() {
        Version original = new Version(VersionName.ORIGINAL);
        Game gameNoMoves = new Game(roomId, gameNumber, original, originalStartConfig);

        Position src1 = new Position(6, 0);
        Position tar1 = new Position(3, 0);
        Move move1 = new Move(Player.RED, src1, tar1);

        gameNoMoves.addPlayer();
        gameNoMoves.addSecondPlayerToGame(flagCapturedConfig);

        Position src2 = new Position(3, 0);
        Position tar2 = new Position(4, 0);
        Move move2 = new Move(Player.BLUE, src2, tar2);

        gameNoMoves.makeMove(move1);

        assertThrows(IllegalStateException.class, ()->
                gameNoMoves.makeMove(move2));
    }
 }