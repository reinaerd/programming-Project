package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum VersionName {
    ORIGINAL,
    INFILTRATOR,
    DUEL,
    UNKNOWN;

    public static String[] getVersionNames() {
        String[] versionNamesWithUnknown = new String[VersionName.values().length];

        int versionNameIndex = 0;
        for (VersionName versionName: VersionName.values()) {
            versionNamesWithUnknown[versionNameIndex] = versionName.toString();
            versionNameIndex++;
        }

        return getPlayableVersionNames(versionNamesWithUnknown);
    }

    private static String[] getPlayableVersionNames(String[] versionNamesWithUnknown) {
        String[] versionNamesWithoutUnknown = new String[versionNamesWithUnknown.length - 1];

        System.arraycopy(versionNamesWithUnknown, 0, versionNamesWithoutUnknown, 0, versionNamesWithUnknown.length - 1);

        return versionNamesWithoutUnknown;
    }

    public static VersionName getVersionName(String name) {
        List<String> versions = new ArrayList<>(Arrays.asList(getVersionNames()));

        VersionName versionName = VersionName.UNKNOWN;
        if (versions.contains(name))
            versionName = VersionName.valueOf(name.toUpperCase(Locale.ROOT));

        return versionName;
    }

    @Override @JsonValue
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
