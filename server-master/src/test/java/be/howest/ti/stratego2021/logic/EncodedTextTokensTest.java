package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.tokens.EncodedTextTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EncodedTextTokensTest {
    private EncodedTextTokens token;
    private String playerTokenRed;

    @BeforeEach
    void setUp() {
        token = new EncodedTextTokens();
        playerTokenRed = token.createToken("group^230001", "RED");
    }

    @Test
    void createToken() {
        String playerToken = token.createToken("group^230001", "RED");

        assertEquals("gsqxt79789;8^RR", playerToken);
    }

    @Test
    void getGameId() {
        String playerToken = token.createToken("group%230001","RED");
        String gameId = token.token2gameId(playerToken);

        assertEquals("group230001", gameId);
    }

    @Test
    void getPlayer() {
        String playerToken = token.createToken("group%230001","RED");
        String player = token.token2player(playerToken);

        assertEquals("RED", player);
    }
    @Test
    void transferEncryptedTokenToNewString() {
        assertEquals("gsqxt79789;",token.encodeFilteredRoomId("group////230001"));
    }

    @Test
    void returnDecryptedToken() {
        assertEquals("group230001-RED",token.getDecryptedToken(playerTokenRed));
    }

}


