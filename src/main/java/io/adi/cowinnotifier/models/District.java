package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class District {

    @JsonProperty("state_id")
    private int stateId;

    @JsonProperty("district_id")
    private int districtId;

    @JsonProperty("district_name")
    private String districtName;

    @JsonProperty("district_name_l")
    private String districtNameL;
}
