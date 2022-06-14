package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class AddNewStartConfigurationSetBody {

    private final Version version;
    private final Rank[][] startConfiguration;

    @JsonCreator
    public AddNewStartConfigurationSetBody(
        @JsonProperty("version") Version version,
        @JsonProperty("startConfiguration") Rank[][] startConfiguration
    ) {
        this.version = version;
        this.startConfiguration = startConfiguration;
    }

    public Version getVersion() {
        return version;
    }

    public Rank[][] getStartConfiguration() {
        return startConfiguration;
    }

    @Override
    public String toString() {
        return "StartConfiguration{" +
                "version=" + version +
                ", startConfiguration=" + Arrays.toString(startConfiguration) +
                '}';
    }
}
