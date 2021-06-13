package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VaccineFee{

    @JsonProperty("vaccine")
    private String vaccine;

    @JsonProperty("fee")
    private String fee;
}
