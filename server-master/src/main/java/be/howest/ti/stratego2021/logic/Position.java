package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Position {

    private final int row;
    private final int col;

    @JsonCreator
    public Position(
        @JsonProperty("row") int row,
        @JsonProperty("col") int col
    ) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @JsonIgnore
    public boolean isWater() {
        if (row == 4 || row == 5) {
            return col == 2 || col == 3 || col == 6 || col == 7;
        }
        return false;
    }

    public boolean isInBoard() {
        return (0 <= row && row <= 9) && (0 <= col && col <= 9);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
