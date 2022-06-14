package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Locale;

public enum Winner {
    ATTACKER,
    DEFENDER,
    DRAW;

    @Override @JsonValue
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
