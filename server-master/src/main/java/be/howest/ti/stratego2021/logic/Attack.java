package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Attack {

    private final Rank attacker;
    private final Rank defender;
    private final Winner winner;

    @JsonCreator
    public Attack(
        @JsonProperty("attacker") Rank attacker,
        @JsonProperty("defender") Rank defender,
        @JsonProperty("winner") Winner winner
    ) {
        this.attacker = attacker;
        this.defender = defender;
        this.winner = winner;
    }

    public Rank getAttacker() {
        return attacker;
    }

    public Rank getDefender() {
        return defender;
    }

    public Winner getWinner() {
        return winner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attack)) return false;
        Attack attack = (Attack) o;
        return attacker == attack.attacker && defender == attack.defender && winner == attack.winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attacker, defender, winner);
    }

    @Override
    public String toString() {
        return "Attack{" +
                "attacker=" + attacker +
                ", defender=" + defender +
                ", winner=" + winner +
                '}';
    }
}
