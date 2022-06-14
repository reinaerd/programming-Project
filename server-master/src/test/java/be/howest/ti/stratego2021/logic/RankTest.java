package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankTest {

    @Test
    void testFindByRank() {
        assertEquals(Rank.MARSHAL, Rank.findByRank("marshal"));
        assertEquals(Rank.GENERAL, Rank.findByRank("general"));
        assertEquals(Rank.COLONEL, Rank.findByRank("colonel"));
        assertEquals(Rank.MAJOR, Rank.findByRank("major"));
        assertEquals(Rank.CAPTAIN, Rank.findByRank("captain"));
        assertEquals(Rank.LIEUTENANT, Rank.findByRank("lieutenant"));
        assertEquals(Rank.SERGEANT, Rank.findByRank("sergeant"));
        assertEquals(Rank.MINER, Rank.findByRank("miner"));
        assertEquals(Rank.SCOUT, Rank.findByRank("scout"));
        assertEquals(Rank.SPY, Rank.findByRank("spy"));
        assertEquals(Rank.INFILTRATOR, Rank.findByRank("infiltrator"));
        assertEquals(Rank.BOMB, Rank.findByRank("bomb"));
        assertEquals(Rank.FLAG, Rank.findByRank("flag"));
    }

    @Test
    void testFindUnknown() {
        assertThrows(IllegalArgumentException.class, () -> Rank.findByRank(""));
    }

    @Test
    void testToString() {
        assertEquals("marshal", Rank.MARSHAL.toString());
        assertEquals("general", Rank.GENERAL.toString());
        assertEquals("colonel", Rank.COLONEL.toString());
        assertEquals("major", Rank.MAJOR.toString());
        assertEquals("captain", Rank.CAPTAIN.toString());
        assertEquals("lieutenant", Rank.LIEUTENANT.toString());
        assertEquals("sergeant", Rank.SERGEANT.toString());
        assertEquals("miner", Rank.MINER.toString());
        assertEquals("scout", Rank.SCOUT.toString());
        assertEquals("spy", Rank.SPY.toString());
        assertEquals("infiltrator", Rank.INFILTRATOR.toString());
        assertEquals("bomb", Rank.BOMB.toString());
        assertEquals("flag", Rank.FLAG.toString());
    }

    @Test
    void testGetPower() {
        assertEquals(10, Rank.MARSHAL.getPower());
        assertEquals(9, Rank.GENERAL.getPower());
        assertEquals(8, Rank.COLONEL.getPower());
        assertEquals(7, Rank.MAJOR.getPower());
        assertEquals(6, Rank.CAPTAIN.getPower());
        assertEquals(5, Rank.LIEUTENANT.getPower());
        assertEquals(4, Rank.SERGEANT.getPower());
        assertEquals(3, Rank.MINER.getPower());
        assertEquals(2, Rank.SCOUT.getPower());
        assertEquals(1, Rank.SPY.getPower());
        assertEquals(1, Rank.INFILTRATOR.getPower());
        assertEquals(0, Rank.BOMB.getPower());
        assertEquals(0, Rank.FLAG.getPower());
    }
}