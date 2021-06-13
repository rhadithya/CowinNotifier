package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class DistrictRoot {

    @JsonProperty("districts")
    private List<District> districts;

    @JsonProperty("ttl")
    private int ttl;
}
