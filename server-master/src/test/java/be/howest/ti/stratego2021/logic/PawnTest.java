package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    Pawn redMarshal;
    Pawn redGeneral;
    Pawn redColonel;
    Pawn redMajor;
    Pawn redCaptain;
    Pawn redLieutenant;
    Pawn redSergeant;
    Pawn redMiner;
    Pawn redScout;
    Pawn redSpy;
    Pawn redInfiltrator;
    Pawn redBomb;
    Pawn redFlag;

    Pawn blueMarshal;
    Pawn blueGeneral;
    Pawn blueColonel;
    Pawn blueMajor;
    Pawn blueCaptain;
    Pawn blueLieutenant;
    Pawn blueSergeant;
    Pawn blueMiner;
    Pawn blueScout;
    Pawn blueSpy;
    Pawn blueInfiltrator;
    Pawn blueBomb;
    Pawn blueFlag;


    @BeforeEach
    void setup() {
        redMarshal = new Pawn(Rank.MARSHAL, Player.RED);
        redGeneral = new Pawn(Rank.GENERAL, Player.RED);
        redColonel = new Pawn(Rank.COLONEL, Player.RED);
        redMajor = new Pawn(Rank.MAJOR, Player.RED);
        redCaptain = new Pawn(Rank.CAPTAIN, Player.RED);
        redLieutenant = new Pawn(Rank.LIEUTENANT, Player.RED);
        redSergeant = new Pawn(Rank.SERGEANT, Player.RED);
        redMiner = new Pawn(Rank.MINER, Player.RED);
        redScout = new Pawn(Rank.SCOUT, Player.RED);
        redSpy = new Pawn(Rank.SPY, Player.RED);
        redInfiltrator = new Pawn(Rank.INFILTRATOR, Player.RED);
        redBomb = new Pawn(Rank.BOMB, Player.RED);
        redFlag = new Pawn(Rank.FLAG, Player.RED);

        blueMarshal = new Pawn(Rank.MARSHAL, Player.BLUE);
        blueGeneral = new Pawn(Rank.GENERAL, Player.BLUE);
        blueColonel = new Pawn(Rank.COLONEL, Player.BLUE);
        blueMajor = new Pawn(Rank.MAJOR, Player.BLUE);
        blueCaptain = new Pawn(Rank.CAPTAIN, Player.BLUE);
        blueLieutenant = new Pawn(Rank.LIEUTENANT, Player.BLUE);
        blueSergeant = new Pawn(Rank.SERGEANT, Player.BLUE);
        blueMiner = new Pawn(Rank.MINER, Player.BLUE);
        blueScout = new Pawn(Rank.SCOUT, Player.BLUE);
        blueSpy = new Pawn(Rank.SPY, Player.BLUE);
        blueInfiltrator = new Pawn(Rank.INFILTRATOR, Player.BLUE);
        blueBomb = new Pawn(Rank.BOMB, Player.BLUE);
        blueFlag = new Pawn(Rank.FLAG, Player.BLUE);
    }

    @Test
    void getPlayer() {
        assertEquals(Player.RED, redSpy.getPlayer());
        assertEquals(Player.BLUE, blueSpy.getPlayer());
    }

    @Test
    void getRank() {
        assertEquals(Rank.MARSHAL, redMarshal.getRank());
        assertEquals(Rank.GENERAL, redGeneral.getRank());
        assertEquals(Rank.COLONEL, redColonel.getRank());
        assertEquals(Rank.MAJOR, redMajor.getRank());
        assertEquals(Rank.CAPTAIN, redCaptain.getRank());
        assertEquals(Rank.LIEUTENANT, redLieutenant.getRank());
        assertEquals(Rank.SERGEANT, redSergeant.getRank());
        assertEquals(Rank.MINER, redMiner.getRank());
        assertEquals(Rank.SCOUT, redScout.getRank());
        assertEquals(Rank.SPY, redSpy.getRank());
        assertEquals(Rank.INFILTRATOR, redInfiltrator.getRank());
        assertEquals(Rank.BOMB, redBomb.getRank());
        assertEquals(Rank.FLAG, redFlag.getRank());
    }

    @Test
    void getPower() {
        assertEquals(10, redMarshal.getPower());
        assertEquals(9, redGeneral.getPower());
        assertEquals(8, redColonel.getPower());
        assertEquals(7, redMajor.getPower());
        assertEquals(6, redCaptain.getPower());
        assertEquals(5, redLieutenant.getPower());
        assertEquals(4, redSergeant.getPower());
        assertEquals(3, redMiner.getPower());
        assertEquals(2, redScout.getPower());
        assertEquals(1, redSpy.getPower());
        assertEquals(1, redInfiltrator.getPower());
        assertEquals(0, redBomb.getPower());
        assertEquals(0, redFlag.getPower());
    }

   @Test
    void marshalDefends() {
       assertEquals(Winner.DRAW, blueMarshal.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueGeneral.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueColonel.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueMajor.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueCaptain.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueLieutenant.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueSergeant.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueMiner.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueScout.attack(redMarshal));
       assertEquals(Winner.ATTACKER, blueSpy.attack(redMarshal));
       assertEquals(Winner.DEFENDER, blueInfiltrator.attack(redMarshal));
   }

    @Test
    void spyDefends() {
        assertEquals(Winner.ATTACKER, blueMarshal.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueGeneral.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueColonel.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueMajor.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueCaptain.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueLieutenant.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueSergeant.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueMiner.attack(redSpy));
        assertEquals(Winner.ATTACKER, blueScout.attack(redSpy));
        assertEquals(Winner.DRAW, blueSpy.attack(redSpy));
        assertEquals(Winner.DRAW, blueInfiltrator.attack(redSpy));
    }

    @Test
    void bombDefends() {
        assertEquals(Winner.DEFENDER, blueMarshal.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueGeneral.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueColonel.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueMajor.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueCaptain.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueLieutenant.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueSergeant.attack(redBomb));
        assertEquals(Winner.ATTACKER, blueMiner.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueScout.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueSpy.attack(redBomb));
        assertEquals(Winner.DEFENDER, blueInfiltrator.attack(redBomb));
    }

    @Test
    void flagDefends() {
        assertEquals(Winner.ATTACKER, blueMarshal.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueGeneral.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueColonel.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueMajor.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueCaptain.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueLieutenant.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueSergeant.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueMiner.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueScout.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueSpy.attack(redFlag));
        assertEquals(Winner.ATTACKER, blueInfiltrator.attack(redFlag));
    }

    @Test
    void infiltratorAttacks() {
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueMarshal.getRank(), blueMarshal));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueGeneral.getRank(), blueGeneral));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueColonel.getRank(), blueColonel));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueMajor.getRank(), blueMajor));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueCaptain.getRank(), blueCaptain));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueLieutenant.getRank(), blueLieutenant));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueSergeant.getRank(), blueSergeant));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueMiner.getRank(), blueMiner));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueScout.getRank(), blueScout));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueSpy.getRank(), blueSpy));
        assertEquals(Winner.ATTACKER, redInfiltrator.infiltrate(blueInfiltrator.getRank(), blueInfiltrator));
    }
}
