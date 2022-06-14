package be.howest.ti.stratego2021.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private final Pawn marshal = new Pawn(Rank.MARSHAL, Player.RED);

    private final Pawn[][] boardPawns = new Pawn[][] {
        {null, null, null, null, null, null, null, null, null, null},
        {null, marshal, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null}
    };

    @Test
    void moveUp() {
        Move up = new Move(Player.RED, new Position(1, 1), new Position(0, 1));
        assertTrue(up.isValidNormalMove());
    }

    @Test
    void moveDown() {
        Move down = new Move(Player.RED, new Position(1, 1), new Position(2, 1));
        assertTrue(down.isValidNormalMove());
    }

    @Test
    void moveLeft() {
        Move left = new Move(Player.RED, new Position(1, 1), new Position(1, 0));
        assertTrue(left.isValidNormalMove());
    }

    @Test
    void moveRight() {
        Move right = new Move(Player.RED, new Position(1, 1), new Position(1, 2));
        assertTrue(right.isValidNormalMove());
    }

    @Test
    void moveDiagonalUpLeft() {
        Move move = new Move(Player.RED, new Position(1, 1), new Position(0, 0));
        assertFalse(move.isValidNormalMove());
    }

    @Test
    void moveDiagonalUpRight() {
        Move move = new Move(Player.RED, new Position(1, 1), new Position(0, 2));
        assertFalse(move.isValidNormalMove());
    }

    @Test
    void moveDiagonalDownLeft() {
        Move move = new Move(Player.RED, new Position(1, 1), new Position(2, 0));
        assertFalse(move.isValidNormalMove());
    }

    @Test
    void moveDiagonalDownRight() {
        Move move = new Move(Player.RED, new Position(1, 1), new Position(2, 2));
        assertFalse(move.isValidNormalMove());
    }

    @Test
    void moveOutsideBoard() {
        Move out1 = new Move(Player.RED, new Position(-1, 0), new Position(0, 0));
        assertFalse(out1.isInBoard());

        Move out2 = new Move(Player.RED, new Position(0, -1), new Position(0, 0));
        assertFalse(out2.isInBoard());

        Move out3 = new Move(Player.RED, new Position(0, 0), new Position(-1, 0));
        assertFalse(out3.isInBoard());

        Move out4 = new Move(Player.RED, new Position(0, 0), new Position(0, -1));
        assertFalse(out4.isInBoard());

        Move out5 = new Move(Player.RED, new Position(10, 0), new Position(0, 0));
        assertFalse(out5.isInBoard());

        Move out6 = new Move(Player.RED, new Position(0, 10), new Position(0, 0));
        assertFalse(out6.isInBoard());

        Move out7 = new Move(Player.RED, new Position(0, 0), new Position(10, 0));
        assertFalse(out7.isInBoard());

        Move out8 = new Move(Player.RED, new Position(0, 0), new Position(0, 10));
        assertFalse(out8.isInBoard());
    }

    @Test
    void getPlayer() {
        Position src = new Position(1, 1);
        Position tar = new Position(1, 2);

        Move moveRed = new Move(Player.RED, src, tar);
        assertEquals(Player.RED, moveRed.getPlayer());

        Move moveBlue = new Move(Player.BLUE, src, tar);
        assertEquals(Player.BLUE, moveBlue.getPlayer());
    }

    @Test
    void getSrc() {
        Position src = new Position(1, 1);
        Position tar = new Position(1, 2);

        Move moveRed = new Move(Player.RED, src, tar);
        assertEquals(src, moveRed.getSrc());

        Move moveBlue = new Move(Player.BLUE, src, tar);
        assertEquals(src, moveBlue.getSrc());
    }

    @Test
    void getTar() {
        Position src = new Position(1, 1);
        Position tar = new Position(1, 2);

        Move moveRed = new Move(Player.RED, src, tar);
        assertEquals(tar, moveRed.getTar());

        Move moveBlue = new Move(Player.BLUE, src, tar);
        assertEquals(tar, moveBlue.getTar());
    }

    @Test
    void getAttack() {
        Attack attack = new Attack(Rank.MARSHAL, Rank.CAPTAIN, Winner.ATTACKER);
        Position src = new Position(1, 1);
        Position tar = new Position(1, 2);

        Move moveRed = new Move(Player.RED, src, tar);
        assertNull(moveRed.getAttack());

        Move moveBlue = new Move(Player.BLUE, src, tar, null, attack);
        assertEquals(attack, moveBlue.getAttack());
    }

    @Test
    void addAttack() {
        Position src = new Position(1, 1);
        Position tar = new Position(1, 2);

        Move move = new Move(Player.RED, src, tar);
        assertNull(move.getAttack());

        move.addAttack(Rank.MARSHAL, Rank.CAPTAIN, Winner.ATTACKER);
        assertEquals(Rank.MARSHAL, move.getAttack().getAttacker());
        assertEquals(Rank.CAPTAIN, move.getAttack().getDefender());
        assertEquals(Winner.ATTACKER, move.getAttack().getWinner());
    }

    @Test
    void moveScoutUp() {
        Move up = new Move(Player.RED, new Position(9, 0), new Position(0, 0));
        assertTrue(up.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutDown() {
        Move down = new Move(Player.RED, new Position(0, 0), new Position(9, 0));
        assertTrue(down.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutLeft() {
        Move left = new Move(Player.RED, new Position(9, 0), new Position(0, 0));
        assertTrue(left.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutRight() {
        Move right = new Move(Player.RED, new Position(9, 0), new Position(0, 0));
        assertTrue(right.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutDiagonalUpLeft() {
        Move move = new Move(Player.RED, new Position(9, 9), new Position(7, 7));
        assertFalse(move.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutDiagonalUpRight() {
        Move move = new Move(Player.RED, new Position(9, 0), new Position(7, 2));
        assertFalse(move.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutDiagonalDownLeft() {
        Move move = new Move(Player.RED, new Position(0, 9), new Position(2, 7));
        assertFalse(move.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutDiagonalDownRight() {
        Move move = new Move(Player.RED, new Position(0, 0), new Position(2, 2));
        assertFalse(move.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutVerticallyOverLeftLake() {
        Move move = new Move(Player.RED, new Position(0, 2), new Position(9, 2));
        assertFalse(move.isValidScoutMove(boardPawns));

        Move move2 = new Move(Player.RED, new Position(0, 3), new Position(9, 3));
        assertFalse(move2.isValidScoutMove(boardPawns));

        Move move3 = new Move(Player.RED, new Position(9, 3), new Position(0, 3));
        assertFalse(move3.isValidScoutMove(boardPawns));

        Move move4 = new Move(Player.RED, new Position(9, 3), new Position(0, 3));
        assertFalse(move4.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutVerticallyOverRightLake() {
        Move move = new Move(Player.RED, new Position(0, 6), new Position(9, 6));
        assertFalse(move.isValidScoutMove(boardPawns));

        Move move2 = new Move(Player.RED, new Position(0, 7), new Position(9, 7));
        assertFalse(move2.isValidScoutMove(boardPawns));

        Move move3 = new Move(Player.RED, new Position(9, 6), new Position(0, 6));
        assertFalse(move3.isValidScoutMove(boardPawns));

        Move move4 = new Move(Player.RED, new Position(9, 7), new Position(0, 7));
        assertFalse(move4.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutHorizontallyOverLeftLake() {
        Move move = new Move(Player.RED, new Position(4, 0), new Position(4, 4));
        assertFalse(move.isValidScoutMove(boardPawns));

        Move move2 = new Move(Player.RED, new Position(5, 0), new Position(5, 4));
        assertFalse(move2.isValidScoutMove(boardPawns));

        Move move3 = new Move(Player.RED, new Position(4, 4), new Position(4, 0));
        assertFalse(move3.isValidScoutMove(boardPawns));

        Move move4 = new Move(Player.RED, new Position(5, 4), new Position(5, 0));
        assertFalse(move4.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutHorizontallyOverRightLake() {
        Move move = new Move(Player.RED, new Position(4, 5), new Position(4, 9));
        assertFalse(move.isValidScoutMove(boardPawns));

        Move move2 = new Move(Player.RED, new Position(5, 5), new Position(5, 9));
        assertFalse(move2.isValidScoutMove(boardPawns));

        Move move3 = new Move(Player.RED, new Position(4, 9), new Position(4, 5));
        assertFalse(move3.isValidScoutMove(boardPawns));

        Move move4 = new Move(Player.RED, new Position(5, 9), new Position(5, 5));
        assertFalse(move4.isValidScoutMove(boardPawns));
    }

    @Test
    void moveScoutOverPiece() {
        Move move = new Move(Player.RED, new Position(0, 1), new Position(3, 1));
        assertFalse(move.isValidScoutMove(boardPawns));

        Move move2 = new Move(Player.RED, new Position(3, 1), new Position(0, 1));
        assertFalse(move2.isValidScoutMove(boardPawns));

        Move move3 = new Move(Player.RED, new Position(1, 0), new Position(1, 3));
        assertFalse(move3.isValidScoutMove(boardPawns));

        Move move4 = new Move(Player.RED, new Position(1, 3), new Position(3, 0));
        assertFalse(move4.isValidScoutMove(boardPawns));
    }

    @Test
    void infiltratorAttackOnNormalTerritory() {
        Move move = new Move(Player.RED, new Position(0, 0), new Position(1, 0));
        assertTrue(move.isValidInfiltratorAttack());

        Move move2 = new Move(Player.RED, new Position(3, 0), new Position(4, 0));
        assertTrue(move2.isValidInfiltratorAttack());
    }

    @Test
    void infiltratorAttackOnEnemyTerritory() {
        Move move = new Move(Player.RED, new Position(1, 0), new Position(3, 0));
        assertTrue(move.isValidInfiltratorAttack());

        Move move2 = new Move(Player.RED, new Position(3, 0), new Position(1, 0));
        assertTrue(move2.isValidInfiltratorAttack());

        Move move3 = new Move(Player.RED, new Position(0, 0), new Position(0, 2));
        assertTrue(move3.isValidInfiltratorAttack());

        Move move4 = new Move(Player.RED, new Position(0, 9), new Position(0, 7));
        assertTrue(move4.isValidInfiltratorAttack());
    }
}
