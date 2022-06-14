package be.howest.ti.stratego2021.web;

import be.howest.ti.stratego2021.logic.*;

import java.util.Deque;

public interface StrategoWebController {

    String[] getStrategoVersions();

    Version getStrategoVersion(VersionName name);

    Game joinGame(String roomId, Version version, Rank[][] startConfiguration);

    Move makeMove(String gameId, Player player, Position src, Position tar);

    Move makeMove(String gameId, Player player, Position src, Position tar, Rank infiltrate);

    Deque<Move> getMoves(String gameId, Player player);

    Pawn[][] getGameState(String gameId, Player player);

    Player[] getLosers(String gameId);

    Player[] setLoser(String gameId, Player player);
}
