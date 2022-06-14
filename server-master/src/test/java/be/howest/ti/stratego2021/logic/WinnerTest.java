package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinnerTest {

    @Test
    void getAttacker() {
        Winner attacker = Winner.ATTACKER;
        assertEquals("attacker", attacker.toString());
    }

    @Test
    void getDefender() {
        Winner defender = Winner.DEFENDER;
        assertEquals("defender", defender.toString());
    }

    @Test
    void getDraw() {
        Winner draw = Winner.DRAW;
        assertEquals("draw", draw.toString());
    }
}