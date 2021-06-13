package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class State {

    @JsonProperty("state_id")
    private int stateId;

    @JsonProperty("state_name")
    private String stateName;

    @JsonProperty("state_name_l")
    private String stateNameL;
}
