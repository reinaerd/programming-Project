package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackTest {

    @Test
    void getAttacker() {
        Attack attack = new Attack(Rank.MARSHAL, Rank.CAPTAIN, Winner.ATTACKER);
        assertEquals(Rank.MARSHAL, attack.getAttacker());
    }

    @Test
    void getDefender() {
        Attack attack = new Attack(Rank.MARSHAL, Rank.CAPTAIN, Winner.ATTACKER);
        assertEquals(Rank.CAPTAIN, attack.getDefender());
    }

    @Test
    void getWinnerAttacker() {
        Attack attack = new Attack(Rank.MARSHAL, Rank.CAPTAIN, Winner.ATTACKER);
        assertEquals(Winner.ATTACKER, attack.getWinner());
    }

    @Test
    void getWinnerDefender() {
        Attack attack = new Attack(Rank.CAPTAIN, Rank.MARSHAL, Winner.DEFENDER);
        assertEquals(Winner.DEFENDER, attack.getWinner());
    }

    @Test
    void getWinnerDraw() {
        Attack attack = new Attack(Rank.MARSHAL, Rank.MARSHAL, Winner.DRAW);
        assertEquals(Winner.DRAW, attack.getWinner());
    }
}