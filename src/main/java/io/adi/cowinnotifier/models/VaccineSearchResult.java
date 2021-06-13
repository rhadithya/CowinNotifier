package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VaccineSearchResult {

    @JsonProperty("center")
    private Center center;

    @JsonProperty("session")
    private Session session;

    @Override
    public String toString() {
        return "Center: " + center.getName() + " "  + center.getAddress() + " " + center.getBlockName() + " " + center.getPincode() +
        "\n\t" + session.getMinAgeLimit() + "+ " + session.getDate() + " " + session.getVaccine() + " " + session.getAvailableCapacity() + " dose1:" + session.getAvailableCapacityDose1() + " dose2: " + session.getAvailableCapacityDose2() + "\n\n";
    }
}
