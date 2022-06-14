package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VersionNameTest {

    @Test
    void getVersionName() {
        assertEquals(VersionName.UNKNOWN, VersionName.getVersionName("notAVersion"));
    }

    @Test
    void getVersionNames() {
        assertArrayEquals(new String[]{"original", "infiltrator", "duel"}, VersionName.getVersionNames());
    }
}