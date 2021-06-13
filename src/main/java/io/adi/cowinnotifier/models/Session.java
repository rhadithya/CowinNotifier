package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Session{

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("available_capacity")
    private int availableCapacity;

    @JsonProperty("min_age_limit")
    private int minAgeLimit;

    @JsonProperty("vaccine")
    private String vaccine;

    @JsonProperty("slots")
    private List<String> slots;

    @JsonProperty("available_capacity_dose1")
    private int availableCapacityDose1;

    @JsonProperty("available_capacity_dose2")
    private int availableCapacityDose2;
}