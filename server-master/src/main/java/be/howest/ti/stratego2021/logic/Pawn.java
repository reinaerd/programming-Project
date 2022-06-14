package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pawn {

    private final Rank rank;
    private final int power;
    private final Player player;

    @JsonCreator
    public Pawn(Rank rank, Player player) {
        this.rank = rank;
        this.power = calculatePower();
        this.player = player;
    }

    public Pawn(Player player) {
        this(null, player);
    }

    public Rank getRank() {
        return rank;
    }

    private int calculatePower() {
        if (rank != null) {
            return rank.getPower();
        } else {
            return 0;
        }
    }

    @JsonIgnore
    public int getPower() {
        return power;
    }

    public Player getPlayer() {
        return player;
    }

    public Winner attack(Pawn defender) {

        if (getPower() == defender.getPower()) {
            return Winner.DRAW;
        } else if (spySeducesMarshal(defender)) {
            return Winner.ATTACKER;
        } else if (pawnExplodesBomb(defender) || defender.getPower() > getPower()) {
            return Winner.DEFENDER;
        }
        return Winner.ATTACKER;
    }

    public Winner infiltrate(Rank infiltrate, Pawn defender) {
        if (infiltrate.getPower() == defender.getPower()) {
            return Winner.ATTACKER;
        }

        return Winner.DEFENDER;
    }

    private boolean pawnExplodesBomb(Pawn defender) {
        return defender.getRank() == Rank.BOMB && getRank() != Rank.MINER;
    }

    private boolean spySeducesMarshal(Pawn defender) {
        return defender.getRank() == Rank.MARSHAL && getRank() == Rank.SPY;
    }

    @JsonIgnore
    public boolean isMoveable() {
        return rank != Rank.FLAG && rank != Rank.BOMB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return rank == pawn.rank;
    }

    @Override
    public int hashCode() {return Objects.hash(rank); }

    @Override
    public String toString() {
        return "Pawn{" +
                "rank=" + rank +
                ", power=" + power +
                ", player=" + player +
                '}';
    }
}
