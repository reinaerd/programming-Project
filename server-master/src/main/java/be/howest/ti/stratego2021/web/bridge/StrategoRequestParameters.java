package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Player;
import be.howest.ti.stratego2021.logic.Position;
import be.howest.ti.stratego2021.logic.Rank;
import be.howest.ti.stratego2021.logic.Version;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;

public class StrategoRequestParameters {

    public static StrategoRequestParameters from(RoutingContext ctx) {
        return new StrategoRequestParameters(ctx);
    }

    public static final String SPEC_VERSION = "version";
    public static final String SPEC_ROOM_ID = "roomId";
    public static final String SPEC_GAME_ID = "gameId";
    private static final String SPEC_PLAYER = "player";

    private final RequestParameters params;
    private final User user;

    private StrategoRequestParameters(RoutingContext ctx) {
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
        this.user = ctx.user();
    }

    public String getAuthorizedPlayer() {
        return user.get(SPEC_PLAYER);
    }

    public String getVersionName() {
        return params.pathParameter(SPEC_VERSION).getString();
    }

    public String getRoomId() {
        return params.pathParameter(SPEC_ROOM_ID).getString();
    }

    public String getGameId() {
        return params.pathParameter(SPEC_GAME_ID).getString();
    }

    public Rank[][] getStartConfiguration() {
        AddNewStartConfigurationSetBody body = params.body().getJsonObject().mapTo(AddNewStartConfigurationSetBody.class);

        return body.getStartConfiguration();
    }

    public Version getVersion() {
        AddNewStartConfigurationSetBody body = params.body().getJsonObject().mapTo(AddNewStartConfigurationSetBody.class);

        return body.getVersion();
    }

    private MakeMoveBody getMakeMoveBody() {
        return params.body().getJsonObject().mapTo(MakeMoveBody.class);
    }

    public Position getMoveSrc() {
        return getMakeMoveBody().getSrc();
    }

    public Position getMoveTar() {
        return getMakeMoveBody().getTar();
    }

    public Rank getInfiltrate() {
        return getMakeMoveBody().getInfiltrate();
    }

    public Player getPlayer() {
        return Player.valueOf(user.get(SPEC_PLAYER));
    }

    public String getAuthorizedGameId() {
        return user.get(SPEC_GAME_ID);
    }
}
