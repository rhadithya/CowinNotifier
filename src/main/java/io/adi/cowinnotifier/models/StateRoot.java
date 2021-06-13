package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class StateRoot {

    @JsonProperty("states")
    private List<State> states;

    @JsonProperty("ttl")
    private int ttl;
}
