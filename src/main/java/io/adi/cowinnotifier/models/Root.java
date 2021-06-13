package io.adi.cowinnotifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Root{

    @JsonProperty("centers")
    private List<Center> centers;
}
