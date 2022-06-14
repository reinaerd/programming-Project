package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Objects;

public class Version {
    private final VersionName name;
    private final Map<Rank, Integer> pieceCount;

    @JsonCreator
    public Version(VersionName name) {
        this.name = name;
        pieceCount = getPieceCountForVersion();
    }

    private Map<Rank, Integer> getPieceCountForVersion() {
        switch (name) {
            case ORIGINAL:
                return getPieceCountForOriginal();
            case INFILTRATOR:
                return getPieceCountForInfiltrator();
            case DUEL:
                return getPieceCountForDuel();
            default:
                return null;
        }
    }

    private Map<Rank, Integer> getPieceCountForOriginal() {
         return Map.ofEntries(Map.entry(Rank.LIEUTENANT,4),
                              Map.entry(Rank.COLONEL, 2),
                              Map.entry(Rank.SERGEANT, 4),
                              Map.entry(Rank.BOMB, 6),
                              Map.entry(Rank.MARSHAL, 1),
                              Map.entry(Rank.MAJOR, 3),
                              Map.entry(Rank.CAPTAIN, 4),
                              Map.entry(Rank.MINER, 5),
                              Map.entry(Rank.SCOUT, 8),
                              Map.entry(Rank.SPY, 1),
                              Map.entry(Rank.GENERAL, 1),
                              Map.entry(Rank.FLAG, 1));
    }

    private Map<Rank, Integer> getPieceCountForInfiltrator() {
        return Map.ofEntries(Map.entry(Rank.LIEUTENANT,4),
                             Map.entry(Rank.COLONEL, 2),
                             Map.entry(Rank.SERGEANT, 4),
                             Map.entry(Rank.BOMB, 6),
                             Map.entry(Rank.MARSHAL, 1),
                             Map.entry(Rank.MAJOR, 3),
                             Map.entry(Rank.CAPTAIN, 4),
                             Map.entry(Rank.MINER, 5),
                             Map.entry(Rank.SCOUT, 7),
                             Map.entry(Rank.SPY, 1),
                             Map.entry(Rank.GENERAL, 1),
                             Map.entry(Rank.INFILTRATOR, 1),
                             Map.entry(Rank.FLAG,1));
    }

    private Map<Rank, Integer> getPieceCountForDuel() {
        return Map.ofEntries(Map.entry(Rank.BOMB, 2),
                             Map.entry(Rank.MARSHAL, 1),
                             Map.entry(Rank.MINER, 2),
                             Map.entry(Rank.SCOUT, 2),
                             Map.entry(Rank.SPY, 1),
                             Map.entry(Rank.GENERAL, 1),
                             Map.entry(Rank.FLAG,1));
    }

    public VersionName getName() {
        return name;
    }

    public Map<Rank, Integer> getPieceCount() { return pieceCount; }

    public Integer getAmountOfPieces(Rank rank) {
        Map<Rank, Integer> chosenGameMode = getPieceCount();
        return chosenGameMode.getOrDefault(rank, 0);
    }

    public int getTotalPieces() {
        int totalPieces = 0;
        for (Integer count: getPieceCount().values()) {
            totalPieces += count;
        }
        return totalPieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return name == version.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " " + pieceCount;
    }
}

