package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    private GameManager gameManager;
    private Rank[][] startConfig;
    private Version original;
    String roomId;
    String gameId_firstGame;
    String gameId_secondGame;

    @BeforeEach
    void setup() {
        gameManager = new GameManager();
        original = new Version(VersionName.ORIGINAL);
        roomId = "room23";
        gameId_firstGame = roomId + "000" + 0;
        gameId_secondGame = roomId + "000" + 1;

        startConfig = new Rank[][] {
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
    }

    @Test
    void joinFirstGameAsRedPlayer() {
        // Red joins the game
        Game game = gameManager.joinGame(roomId, original, startConfig);

        // joinGame creates a new Game
        assertEquals(roomId, game.getRoomId());
        assertEquals(original, game.getVersion());
        assertEquals(Player.RED, game.getLastJoinedPlayer());
        assertEquals(game, gameManager.findGameByGameId(game.getGameId()));

        // joinGame creates a first Move
        assertEquals(1, game.getMoves().size());
        assertEquals(Player.RED, game.getMoves().getLast().getPlayer());
        assertNull(game.getMoves().getLast().getSrc());
        assertNull(game.getMoves().getLast().getTar());
    }

    @Test
    void joinFirstGameAsBluePlayer() {
        // Red joins the game
        gameManager.joinGame(roomId, original, startConfig);

        // Blue joins the game
        Game game = gameManager.joinGame(roomId, original, startConfig);

        // joinGame joins an existing Game
        assertEquals(roomId, game.getRoomId());
        assertEquals(original, game.getVersion());
        assertEquals(Player.BLUE, game.getLastJoinedPlayer());
        assertEquals(game, gameManager.findGameByGameId(game.getGameId()));

        // joinGame creates a second Move
        assertEquals(2, game.getMoves().size());
        assertEquals(Player.BLUE, game.getMoves().getLast().getPlayer());
        assertNull(game.getMoves().getLast().getSrc());
        assertNull(game.getMoves().getLast().getTar());
    }

    @Test
    void joinSecondGameAsRedPlayer() {
        // Red and blue join the first game
        Game firstGame = gameManager.joinGame(roomId, original, startConfig);
        gameManager.joinGame(roomId, original, startConfig);

        // Red joins the second game
        Game secondGame = gameManager.joinGame(roomId, original, startConfig);
        assertEquals(roomId, secondGame.getRoomId());
        assertEquals(original, secondGame.getVersion());
        assertEquals(Player.RED, secondGame.getLastJoinedPlayer());
        assertEquals(secondGame, gameManager.findGameByGameId(secondGame.getGameId()));

        // It created two different games
        assertNotEquals(firstGame.getGameId(), secondGame.getGameId());
    }

    @Test
    void joinSecondGameAsBluePlayer() {
        // Red and blue join the first game
        Game firstGame = gameManager.joinGame(roomId, original, startConfig);
        gameManager.joinGame(roomId, original, startConfig);

        // Red joins the second game
        gameManager.joinGame(roomId, original, startConfig);

        // Blue joins the second game
        Game secondGame = gameManager.joinGame(roomId, original, startConfig);
        assertEquals(roomId, secondGame.getRoomId());
        assertEquals(original, secondGame.getVersion());
        assertEquals(Player.BLUE, secondGame.getLastJoinedPlayer());
        assertEquals(secondGame, gameManager.findGameByGameId(secondGame.getGameId()));

        // It created two different games
        assertNotEquals(firstGame.getGameId(), secondGame.getGameId());
    }

    @Test
    void findGameByGameId() {
        Game game = gameManager.joinGame(roomId, original, startConfig);

        assertEquals(game, gameManager.findGameByGameId(gameId_firstGame));
    }

    @Test
    void exceptionThrownOnWrongGameId() {
        gameManager.joinGame(roomId, original, startConfig);

        assertThrows(StrategoResourceNotFoundException.class, ()->
                gameManager.findGameByGameId("notAGameId"));
    }
}