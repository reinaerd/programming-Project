package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.*;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.Deque;

public class StrategoResponses {

    public static final String SPEC_VERSIONS = "versions";
    public static final String SPEC_GAME_ID = "gameId";
    public static final String SPEC_NAME = "name";
    public static final String SPEC_PIECE_COUNT = "pieceCount";
    private static final String SPEC_MOVE = "move";
    private static final String SPEC_MOVES = "moves";
    private static final String SPEC_PLAYER_TOKEN = "playerToken";
    private static final String SPEC_CONFIGURATION = "configuration";
    private static final String SPEC_PLAYER = "player";
    private static final String SPEC_LOSERS = "losers";

    private StrategoResponses() { /* utility class */ }

    private static void sendJsonResponse(RoutingContext ctx, int statusCode, Object response) {
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .setStatusCode(statusCode)
            .end(Json.encodePrettily(response));
    }

    public static void sendStrategoVersions(RoutingContext ctx, String[] versions) {
        sendJsonResponse(ctx, 200, new JsonObject()
            .put(SPEC_VERSIONS, versions));
    }

    public static void sendStrategoVersion(RoutingContext ctx, Version selectedVersion) {
        if (selectedVersion.getName() != VersionName.UNKNOWN)
            sendJsonResponse(ctx, 200, new JsonObject()
                .put(SPEC_NAME, selectedVersion.getName())
                .put(SPEC_PIECE_COUNT, selectedVersion.getPieceCount()));
        else
            sendFailure(ctx, 404, "No such version");
    }

    public static void sendMove(RoutingContext ctx, Move move) {
        sendJsonResponse(ctx, 200, new JsonObject()
            .put(SPEC_MOVE, move));
    }

    public static void sendMoves(RoutingContext ctx, Deque<Move> moves) {
        sendJsonResponse(ctx, 200, new JsonObject()
            .put(SPEC_MOVES, moves));
    }

    public static void sendGameState(RoutingContext ctx, Pawn[][] gameState) {
        sendJsonResponse(ctx, 200, new JsonObject()
            .put(SPEC_CONFIGURATION, gameState)
        );
    }

    public static void sendJoinedGameInfo(RoutingContext ctx, Game game) {
        sendJsonResponse(ctx, 201, new JsonObject()
            .put(SPEC_GAME_ID, game.getGameId())
            .put(SPEC_PLAYER, game.getLastJoinedPlayer())
            .put(SPEC_PLAYER_TOKEN, game.getPlayerToken(game.getLastJoinedPlayer()))
        );
    }

    public static void sendLosers(RoutingContext ctx, Player[] losers) {
        sendJsonResponse(ctx, 200, new JsonObject()
            .put(SPEC_LOSERS, losers));
    }

    public static void sendFailure(RoutingContext ctx, int code, String message) {
        sendJsonResponse(ctx, code, new JsonObject()
            .put("failure", code)
            .put("cause", message)
        );
    }
}
