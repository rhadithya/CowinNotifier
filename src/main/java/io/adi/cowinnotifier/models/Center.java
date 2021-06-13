package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Center{

    @JsonProperty("center_id")
    private int centerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("state_name")
    private String stateName;

    @JsonProperty("district_name")
    private String districtName;

    @JsonProperty("block_name")
    private String blockName;

    @JsonProperty("pincode")
    private int pincode;

    @JsonProperty("lat")
    public int latitude;

    @JsonProperty("long")
    public int longitude;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("sessions")
    private List<Session> sessions;

    @JsonProperty("vaccine_fees")
    private List<VaccineFee> vaccineFees;

}
