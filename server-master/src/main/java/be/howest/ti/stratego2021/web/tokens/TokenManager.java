package be.howest.ti.stratego2021.web.tokens;

import io.vertx.ext.auth.User;

public interface TokenManager {

    String createToken(String gameId, String player);

    String token2gameId(String token);
    String token2player(String token);

    default User createUser(String token) {
        User user = User.fromToken(token);
        user.principal()
                .put("gameId", token2gameId(token))
                .put("player", token2player(token));
        return user;
    }

}
