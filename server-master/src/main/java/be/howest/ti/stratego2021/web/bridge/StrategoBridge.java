package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.*;
import be.howest.ti.stratego2021.logic.exceptions.StrategoGameRuleException;
import be.howest.ti.stratego2021.logic.exceptions.StrategoResourceNotFoundException;

import be.howest.ti.stratego2021.web.StrategoWebController;
import be.howest.ti.stratego2021.web.exceptions.ForbiddenAccessException;
import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;
import be.howest.ti.stratego2021.web.tokens.EncodedTextTokens;
import be.howest.ti.stratego2021.web.tokens.TokenManager;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.impl.HttpStatusException;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.Deque;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StrategoBridge implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(StrategoBridge.class.getName());

    private final StrategoWebController controller;
    private final TokenManager tokenManager = new EncodedTextTokens();

    public StrategoBridge(StrategoWebController controller) {
        this.controller = controller;
    }

    public StrategoBridge() {
        this(new StrategoController());
    }

    private void getStrategoVersions(RoutingContext ctx) {
        String[] versions = controller.getStrategoVersions();
        StrategoResponses.sendStrategoVersions(ctx, versions);
    }

    private void getStrategoVersion(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Version version = controller.getStrategoVersion(VersionName.getVersionName(r.getVersionName()));
        StrategoResponses.sendStrategoVersion(ctx, version);
    }

    private void makeMove(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        try {
            Move move = controller.makeMove(r.getGameId(), r.getPlayer(), r.getMoveSrc(), r.getMoveTar(), r.getInfiltrate());
            StrategoResponses.sendMove(ctx, move);
        } catch (IllegalStateException ex) {
            StrategoResponses.sendFailure(ctx, 400, ex.getMessage());
        }
    }

    private void getMoves(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Deque<Move> moves = controller.getMoves(r.getGameId(), r.getPlayer());
        StrategoResponses.sendMoves(ctx, moves);
    }

    private void joinGame(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Game game = controller.joinGame(r.getRoomId(), r.getVersion(), r.getStartConfiguration());
        StrategoResponses.sendJoinedGameInfo(ctx, game);
    }

    private void getGameState(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Pawn[][] gameState = controller.getGameState(r.getGameId(), r.getPlayer());
        StrategoResponses.sendGameState(ctx, gameState);
    }

    private void getLosers(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Player[] losers = controller.getLosers(r.getGameId());
        StrategoResponses.sendLosers(ctx, losers);
    }

    private void setLoser(RoutingContext ctx) {
        StrategoRequestParameters r = StrategoRequestParameters.from(ctx);
        Player[] losers = controller.setLoser(r.getGameId(), r.getPlayer());
        StrategoResponses.sendLosers(ctx, losers);
    }

    private void authorize(RoutingContext ctx) {
        StrategoRequestParameters requestParameters = StrategoRequestParameters.from(ctx);

        String wantedGameId = requestParameters.getGameId();
        String authorizedGameId = requestParameters.getAuthorizedGameId();

        if (!wantedGameId.equals(authorizedGameId)) {
            throw new ForbiddenAccessException();
        }

        ctx.next();
    }

    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("player_auth", BearerAuthHandler.create(this));

        LOGGER.log(Level.INFO, "Installing cors handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing failure handler for all operations");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing dedicated handler for each operation");
        routerBuilder.operation("getStrategoVersions").handler(this::getStrategoVersions);

        LOGGER.log(Level.INFO, "Installing handler for: getStrategoVersion");
        routerBuilder.operation("getStrategoVersion").handler(this::getStrategoVersion);

        LOGGER.log(Level.INFO, "Installing handler for: joinGame");
        routerBuilder.operation("joinGame").handler(this::joinGame);

        LOGGER.log(Level.INFO, "Installing handler for: getGameState");
        routerBuilder.operation("getGameState")
                .handler(this::authorize)
                .handler(this::getGameState);

        LOGGER.log(Level.INFO, "Installing handler for: makeMove");
        routerBuilder.operation("makeMove")
                .handler(this::authorize)
                .handler(this::makeMove);

        LOGGER.log(Level.INFO, "Installing handler for: getMoves");
        routerBuilder.operation("getMoves")
                .handler(this::authorize)
                .handler(this::getMoves);

        LOGGER.log(Level.INFO, "Installing handler for: getLosers");
        routerBuilder.operation("getLosers")
                .handler(this::authorize)
                .handler(this::getLosers);

        LOGGER.log(Level.INFO, "Installing handler for: setLoser");
        routerBuilder.operation("setLoser")
                .handler(this::authorize)
                .handler(this::setLoser);


        LOGGER.log(Level.INFO, "All handlers are installed, creating router.");
        return routerBuilder.createRouter();
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code;
        String message = Objects.isNull(cause) ? null : cause.getMessage();

        if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof StrategoResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof StrategoGameRuleException) {
            code = 409;
        } else if (cause instanceof HttpStatusException) {
            code = ctx.statusCode();
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
            code = 500;
            message = "Something went wrong on the server side, check the logs for more information.";
        }

        StrategoResponses.sendFailure(ctx, code, message);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create();
    }

    @Override public void authenticate(JsonObject credentials, Handler<AsyncResult<User>> handler) {
        TokenCredentials tokenCredentials = credentials.mapTo(TokenCredentials.class);
        String token = tokenCredentials.getToken();

        try {
            handler.handle(Future.succeededFuture(
                    tokenManager.createUser(token)
            ));
        } catch (InvalidTokenException ex) {
            handler.handle(Future.failedFuture(ex));
        }
    }
}
