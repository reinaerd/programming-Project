package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPlayerRed() {
        Player player = Player.RED;
        assertEquals("red", player.toString());
    }

    @Test
    void getPlayerBlue() {
        Player player = Player.BLUE;
        assertEquals("blue", player.toString());
    }

    @Test
    void getNextPlayer() {
        Player playerRed = Player.RED;
        assertEquals("blue", playerRed.getNextPlayer().toString());

        Player playerBlue = Player.BLUE;
        assertEquals("red", playerBlue.getNextPlayer().toString());
    }
}