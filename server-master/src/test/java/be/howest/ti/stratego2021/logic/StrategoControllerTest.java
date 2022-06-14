package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrategoControllerTest {

    private StrategoController controller;

    private Player player;
    private Position src;
    private Position tar;

    // Needed by getGameState
    private String roomId;
    private Version original;
    private Rank[][] redConfig;
    private Rank[][] blueConfig;
    String gameId_firstGame;
    String gameId_secondGame;

    Pawn marshalRed;
    Pawn colonelBlue;

    @BeforeEach
    void setup() {
        controller = new StrategoController();

        player = Player.RED;
        src = new Position(6, 0);
        tar = new Position(5, 0);

        // Needed by getGameState
        original = new Version(VersionName.ORIGINAL);
        roomId = "room23";
        gameId_firstGame = roomId + "000" + 0;
        gameId_secondGame = roomId + "000" + 1;

        redConfig = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.MARSHAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SPY, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.GENERAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.COLONEL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.COLONEL, Rank.FLAG, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.BOMB, Rank.SERGEANT}
        };

        blueConfig = new Rank[][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {Rank.COLONEL, Rank.FLAG, Rank.MAJOR, Rank.MINER, Rank.LIEUTENANT, Rank.SCOUT, Rank.BOMB, Rank.BOMB, Rank.SERGEANT, Rank.BOMB},
                {Rank.MARSHAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SPY, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.GENERAL, Rank.MAJOR, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT},
                {Rank.COLONEL, Rank.CAPTAIN, Rank.CAPTAIN, Rank.LIEUTENANT, Rank.MINER, Rank.SCOUT, Rank.SCOUT, Rank.SCOUT, Rank.BOMB, Rank.SERGEANT}
        };

        marshalRed = new Pawn(Rank.MARSHAL, Player.RED);
        colonelBlue = new Pawn(Rank.COLONEL, Player.BLUE);
    }

    @Test
    void getStrategoVersions() {
        assertArrayEquals(new String[] {"original", "infiltrator", "duel"}, controller.getStrategoVersions());
    }

    @Test
    void makeMove() {
        Game game = controller.joinGame(roomId, original, redConfig);
        Move move = controller.makeMove(game.getGameId(), player, src, tar);

        assertEquals(player, move.getPlayer());
        assertEquals(src, move.getSrc());
        assertEquals(tar, move.getTar());
        assertNull(move.getInfiltrate());
        assertNull(move.getAttack());
    }

    @Test
    void makeMoveWithInfiltrate() {
        Game game = controller.joinGame(roomId, original, redConfig);
        Move move = controller.makeMove(game.getGameId(), player, src, tar, Rank.MARSHAL);

        assertEquals(player, move.getPlayer());
        assertEquals(src, move.getSrc());
        assertEquals(tar, move.getTar());
        assertEquals(Rank.MARSHAL, move.getInfiltrate());
        assertNull(move.getAttack());
    }

    @Test
    void makeMoveThrows404GameDoesNotExist() {
        assertThrows(
            StrategoResourceNotFoundException.class,
            () -> controller.makeMove("fakeGameId", player, src, tar)
        );
    }

    @Test
    void getGameState() {
        // Create new game with players Red and Blue
        controller.joinGame(roomId, original, redConfig);
        controller.joinGame(roomId, original, blueConfig);

        Pawn[][] gameStateRed = controller.getGameState(gameId_firstGame, Player.RED);
        Pawn[][] gameStateBlue = controller.getGameState(gameId_firstGame, Player.BLUE);

        // Testing board state of player Red
        assertEquals(marshalRed, gameStateRed[6][0]);
        assertEquals(new Pawn(null, Player.BLUE), gameStateRed[3][9]);

        // Testing board state of player Blue
        assertEquals(colonelBlue, gameStateBlue[6][0]);
        assertEquals(new Pawn(null, Player.RED), gameStateBlue[3][9]);
    }

    @Test
    void setLoser() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        Player[] losers = controller.setLoser(game.getGameId(), Player.RED);

        assertEquals(Player.RED, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void setLoserBoth() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        controller.setLoser(game.getGameId(), Player.RED);
        Player[] losers = controller.setLoser(game.getGameId(), Player.BLUE);

        assertEquals(Player.RED, losers[0]);
        assertEquals(Player.BLUE, losers[1]);
    }

    @Test
    void setLoserRepeated() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        String gameId = game.getGameId();
        controller.setLoser(game.getGameId(), Player.RED);

        assertThrows(
            IllegalStateException.class,
            () -> controller.setLoser(gameId, Player.RED)
        );
    }

    @Test
    void setLoserGameNotFound() {
        controller.joinGame(roomId, original, redConfig);
        controller.joinGame(roomId, original, blueConfig);

        assertThrows(
            StrategoResourceNotFoundException.class,
            () -> controller.setLoser("FAKE_GAME_ID", Player.RED)
        );
    }

    @Test
    void getLosers() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        controller.setLoser(game.getGameId(), Player.RED);
        Player[] losers = controller.getLosers(game.getGameId());

        assertEquals(Player.RED, losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void getLosersBoth() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        controller.setLoser(game.getGameId(), Player.RED);
        controller.setLoser(game.getGameId(), Player.BLUE);
        Player[] losers = controller.getLosers(game.getGameId());

        assertEquals(Player.RED, losers[0]);
        assertEquals(Player.BLUE, losers[1]);
    }

    @Test
    void getLosersEmpty() {
        controller.joinGame(roomId, original, redConfig);
        Game game = controller.joinGame(roomId, original, blueConfig);
        Player[] losers = controller.getLosers(game.getGameId());

        assertNull(losers[0]);
        assertNull(losers[1]);
    }

    @Test
    void getLosersGameNotFound() {
        controller.joinGame(roomId, original, redConfig);
        controller.joinGame(roomId, original, blueConfig);

        assertThrows(
            StrategoResourceNotFoundException.class,
            () -> controller.getLosers("FAKE_GAME_ID")
        );
    }
}