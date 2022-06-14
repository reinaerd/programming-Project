package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Move {

    private static final Integer INFILTRATOR_ATTACK_DISTANCE_ON_NORMAL_TERRITORY = 1;
    private static final Integer INFILTRATOR_ATTACK_DISTANCE_ON_ENEMY_TERRITORY = 2;
    private static final Integer NORMAL_TERRITORY_PLAYER_RED = 4;
    private static final Integer NORMAL_TERRITORY_PLAYER_BLUE = 5;

    private final Player player;
    private final Position src;
    private final Position tar;
    private final Rank infiltrate;
    private Attack attack;

    @JsonCreator
    public Move(
        @JsonProperty("player") Player player
    ) {
        this(player, null, null, null, null);
    }

    @JsonCreator
    public Move(
        @JsonProperty("player") Player player,
        @JsonProperty("src") Position src,
        @JsonProperty("tar") Position tar
    ) {
        this(player, src, tar, null, null);
    }

    @JsonCreator
    public Move(
        @JsonProperty("player") Player player,
        @JsonProperty("src") Position src,
        @JsonProperty("tar") Position tar,
        @JsonProperty("infiltrate") Rank infiltrate
    ) {
        this(player, src, tar, infiltrate, null);
    }

    @JsonCreator
    public Move(
        @JsonProperty("player") Player player,
        @JsonProperty("src") Position src,
        @JsonProperty("tar") Position tar,
        @JsonProperty("infiltrate") Rank infiltrate,
        @JsonProperty("attack") Attack attack
    ) {
        this.player = player;
        this.src = src;
        this.tar = tar;
        this.infiltrate = infiltrate;
        this.attack = attack;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getSrc() {
        return src;
    }

    public Position getTar() {
        return tar;
    }

    public Attack getAttack() {
        return attack;
    }

    @JsonIgnore
    public void addAttack(Rank attacker, Rank defender, Winner winner) {
        this.attack = new Attack(attacker, defender, winner);
    }

    @JsonIgnore
    public Rank getInfiltrate() {
        return infiltrate;
    }

    @JsonIgnore
    public boolean isInBoard() {
        return src.isInBoard() && tar.isInBoard();
    }

    @JsonIgnore
    public boolean isValidNormalMove() {
        return isRowMove() || isColMove();
    }

    @JsonIgnore
    public boolean isValidScoutMove(Pawn[][] boardConfig) {
        if (rowsAreTheSame()) {
            return calculateScoutMovementOnRow(boardConfig);
        } else if (colsAreTheSame()) {
            return calculateScoutMovementOnCol(boardConfig);
        } else {
            return false;
        }
    }

    private boolean calculateScoutMovementOnRow(Pawn[][] boardConfig) {
        int destinationCol = tar.getCol();

        if (src.getCol() < destinationCol) {
            return executePositiveScoutMovementOnRow(boardConfig, destinationCol);
        } else {
            return executeNegativeScoutMovementOnRow(boardConfig, destinationCol);
        }
    }

    private boolean executePositiveScoutMovementOnRow(Pawn[][] boardConfig, int destinationCol) {
        for (int colCounter = src.getCol() + 1; colCounter < destinationCol; colCounter++) {
            Position position = new Position(src.getRow(), colCounter + 1);
            if (boardConfig[src.getRow()][colCounter] != null || position.isWater()) { return false; }
        }
        return true;
    }

    private boolean executeNegativeScoutMovementOnRow(Pawn[][] boardConfig, int destinationCol) {
        for (int colCounter = src.getCol() - 1; colCounter > destinationCol; colCounter--) {
            Position position = new Position(src.getRow(), colCounter - 1);
            if (boardConfig[src.getRow()][colCounter] != null || position.isWater()) { return false; }
        }
        return true;
    }

    private boolean calculateScoutMovementOnCol(Pawn[][] boardConfig) {
        int destinationRow = tar.getRow();

        if (src.getRow() < destinationRow) {
            return executePositiveScoutMovementOnCol(boardConfig, destinationRow);
        } else {
            return executeNegativeScoutMovementOnCol(boardConfig, destinationRow);
        }
    }

    private boolean executePositiveScoutMovementOnCol(Pawn[][] boardConfig, int destinationRow) {
        for (int rowCounter = src.getRow() + 1; rowCounter < destinationRow; rowCounter++) {
            Position position = new Position(rowCounter + 1, src.getCol());
            if (boardConfig[rowCounter][src.getCol()] != null || position.isWater()) { return false; }
        }
        return true;
    }

    private boolean executeNegativeScoutMovementOnCol(Pawn[][] boardConfig, int destinationRow) {
        for (int rowCounter = src.getRow() - 1; rowCounter > destinationRow; rowCounter--) {
            Position position = new Position(rowCounter - 1, src.getCol());
            if (boardConfig[rowCounter][src.getCol()] != null || position.isWater()) { return false; }
        }
        return true;
    }

    private boolean isColMove() {
        return colDiffIsOne() && rowsAreTheSame();
    }

    private boolean isRowMove() {
        return rowDiffIsOne() && colsAreTheSame();
    }

    private boolean colsAreTheSame() {
        return src.getCol() == tar.getCol();
    }

    private boolean rowsAreTheSame() {
        return src.getRow() == tar.getRow();
    }

    private boolean rowDiffIsOne() {
        return Math.abs(src.getRow() - tar.getRow()) == 1;
    }

    private boolean colDiffIsOne() {
        return Math.abs(src.getCol() - tar.getCol()) == 1;
    }

    @JsonIgnore
    public boolean isValidInfiltratorAttack() {
        if (isInEnemyTerritory() && isMoreThanTilesDistance(INFILTRATOR_ATTACK_DISTANCE_ON_ENEMY_TERRITORY)) {
            return false;
        } else return isInEnemyTerritory() || !isMoreThanTilesDistance(INFILTRATOR_ATTACK_DISTANCE_ON_NORMAL_TERRITORY);
    }

    @JsonIgnore
    public boolean isInEnemyTerritory() {
        if (getPlayer() == Player.RED) {
            return src.getRow() < NORMAL_TERRITORY_PLAYER_RED && tar.getRow() < NORMAL_TERRITORY_PLAYER_RED;
        }
        return src.getRow() > NORMAL_TERRITORY_PLAYER_BLUE && tar.getRow() > NORMAL_TERRITORY_PLAYER_BLUE;
    }

    private boolean isMoreThanTilesDistance(Integer distance) {
        return (isFurtherThan(src.getRow(), tar.getRow(), distance) || isFurtherThan(src.getCol(), tar.getCol(), distance));
    }

    private boolean isFurtherThan(Integer source, Integer target, Integer distance) {
        return (source - target) > distance || (target - source) > distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return player == move.player && Objects.equals(src, move.src) && Objects.equals(tar, move.tar) && infiltrate == move.infiltrate && Objects.equals(attack, move.attack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, src, tar, infiltrate, attack);
    }

    @Override
    public String toString() {
        return "Move{" +
                "player=" + player +
                ", src=" + src +
                ", tar=" + tar +
                ", infiltrate=" + infiltrate +
                ", attack=" + attack +
                '}';
    }
}
