package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Position;
import be.howest.ti.stratego2021.logic.Rank;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class MakeMoveBody {

    private final Position src;
    private final Position tar;
    private final Rank infiltrate;

    @JsonCreator
    public MakeMoveBody(
        @JsonProperty("src") Position src,
        @JsonProperty("tar") Position tar,
        @JsonProperty("infiltrate") Rank infiltrate
    ) {
        this.src = src;
        this.tar = tar;
        this.infiltrate = infiltrate;
    }

    public Position getSrc() {
        return src;
    }

    public Position getTar() {
        return tar;
    }

    public Rank getInfiltrate() {
        return infiltrate;
    }
}
