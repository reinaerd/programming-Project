package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum Player {
    RED,
    BLUE;

    public Player getNextPlayer() {
        if (this == RED) {
            return BLUE;
        } else {
            return RED;
        }
    }

    @Override @JsonValue
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}


