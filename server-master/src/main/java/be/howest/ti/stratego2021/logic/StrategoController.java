package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.StrategoWebController;

import java.util.Deque;
import java.util.LinkedList;

public class StrategoController implements StrategoWebController {

    private final GameManager gameManager = new GameManager();

    @Override
    public String[] getStrategoVersions() {
        return VersionName.getVersionNames();
    }

    @Override
    public Version getStrategoVersion(VersionName name) {
         return new Version(name);
    }

    @Override
    public Game joinGame(String roomId, Version version, Rank[][] startConfig) {
        return gameManager.joinGame(roomId, version, startConfig);
    }

    @Override
    public Move makeMove(String gameId, Player player, Position src, Position tar) {
        return makeMove(gameId, player, src, tar, null);
    }

    @Override
    public Move makeMove(String gameId, Player player, Position src, Position tar, Rank infiltrate) {
        Game game = gameManager.findGameByGameId(gameId);
        Move move = new Move(player, src, tar, infiltrate);

        return game.makeMove(move);
    }

    @Override
    public Deque<Move> getMoves(String gameId, Player player) {
        Game game = gameManager.findGameByGameId(gameId);
        Deque<Move> moves = new LinkedList<>();

        for (Move move : game.getMoves()) {
            moves.add(game.getBoard().flipMoveIfIsNotFromPlayer(move, player));
        }

        return moves;
    }

    @Override
    public Pawn[][] getGameState(String gameId, Player player) {
        Game game = gameManager.findGameByGameId(gameId);
        Board board = game.getBoard();

        return board.getPawnsOf(player);
    }

    @Override
    public Player[] getLosers(String gameId) {
        Game game = gameManager.findGameByGameId(gameId);

        return game.getLosers();
    }

    @Override
    public Player[] setLoser(String gameId, Player player) {
        Game game = gameManager.findGameByGameId(gameId);

        return game.setLoser(player);
    }
}
