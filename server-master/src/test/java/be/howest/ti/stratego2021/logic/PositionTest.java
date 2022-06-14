package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void getRow() {
        Position position = new Position(0, 1);
        assertEquals(0, position.getRow());
    }

    @Test
    void getCol() {
        Position position = new Position(0, 1);
        assertEquals(1, position.getCol());
    }

    @Test
    void isWater() {
        Position position42 = new Position(4, 2);
        assertTrue(position42.isWater());

        Position position43 = new Position(4, 3);
        assertTrue(position43.isWater());

        Position position46 = new Position(4, 6);
        assertTrue(position46.isWater());

        Position position47 = new Position(4, 7);
        assertTrue(position47.isWater());

        Position position52 = new Position(5, 2);
        assertTrue(position52.isWater());

        Position position53 = new Position(5, 3);
        assertTrue(position53.isWater());

        Position position56 = new Position(5, 6);
        assertTrue(position56.isWater());

        Position position57 = new Position(5, 7);
        assertTrue(position57.isWater());
    }
}