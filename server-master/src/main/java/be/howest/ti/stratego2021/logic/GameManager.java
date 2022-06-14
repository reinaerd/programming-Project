package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class GameManager {

    private final List<Game> games;

    public GameManager() {
        games = new LinkedList<>();
    }

    public Game joinGame(String roomId, Version version, Rank[][] startConfig) {
        Game game = findGameByRoomId(roomId, version);

        if (game == null) {
            game = createAndJoinGame(roomId, version, startConfig);

        } else {
            game.addSecondPlayerToGame(startConfig);
            game.setLoserIfPlayersCantMove();
        }

        return game;
    }

    private Game createAndJoinGame(String roomId, Version version, Rank[][] startConfig) {
        Game game = new Game(roomId, games.size(), version, startConfig);
        game.addPlayer();

        games.add(game);

        return game;
    }

    private Game findGameByRoomId(String roomId, Version version) {
        for (Game game: games) {
            if (game.getRoomId().equals(roomId) && game.getVersion().equals(version) && game.spotIsEmpty(1)) {
                return game;
            }
        }
        return null;
    }

    public Game findGameByGameId(String gameId) {
        for (Game game: games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        throw new StrategoResourceNotFoundException("No game with such gameId found");
    }
}
