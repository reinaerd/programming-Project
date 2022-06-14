package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Locale;

public enum Rank {
    MARSHAL(10),
    GENERAL(9),
    COLONEL(8),
    MAJOR(7),
    CAPTAIN(6),
    LIEUTENANT(5),
    SERGEANT(4),
    MINER(3),
    SCOUT(2),
    SPY(1),
    INFILTRATOR(1),
    BOMB(0),
    FLAG(0);

    private final int power;

    Rank(int power) { this.power = power; }

    public int getPower() {
        return power;
    }

    @Override @JsonValue
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @JsonCreator
    public static Rank findByRank(String rank) {
        return Arrays.stream(Rank.values())
                .filter(ranks -> rank.equalsIgnoreCase(ranks.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No rank found"));
    }
}

