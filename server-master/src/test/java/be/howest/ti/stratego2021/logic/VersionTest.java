package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Test
    void getVersionName() {
        Version original = new Version(VersionName.ORIGINAL);

        assertEquals(VersionName.ORIGINAL, original.getName());
    }

    @Test
    void getVersionPieceCount() {
        Version original = new Version(VersionName.ORIGINAL);

        assertEquals(new HashMap<>(Map.ofEntries(
                Map.entry(Rank.LIEUTENANT, 4),
                Map.entry(Rank.COLONEL, 2),
                Map.entry(Rank.SERGEANT, 4),
                Map.entry(Rank.BOMB, 6),
                Map.entry(Rank.MARSHAL, 1),
                Map.entry(Rank.MAJOR, 3),
                Map.entry(Rank.CAPTAIN, 4),
                Map.entry(Rank.MINER, 5),
                Map.entry(Rank.SCOUT, 8),
                Map.entry(Rank.SPY, 1),
                Map.entry(Rank.GENERAL, 1),
                Map.entry(Rank.FLAG, 1))), original.getPieceCount());
    }

    @Test
    void getAmountOfPieces() {
         Version original = new Version(VersionName.ORIGINAL);

         assertEquals(1, original.getAmountOfPieces(Rank.FLAG));
         assertEquals(8, original.getAmountOfPieces(Rank.SCOUT));
    }

    @Test
    void getTotalPieces(){
        Version original = new Version(VersionName.ORIGINAL);
        assertEquals(40,original.getTotalPieces());
    }
}
