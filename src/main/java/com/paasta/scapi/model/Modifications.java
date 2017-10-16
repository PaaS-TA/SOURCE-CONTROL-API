package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Setter
@Getter
public class Modifications {

    @NotEmpty
    @JsonSerialize
    @JsonProperty("added")
    private List<String> added;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("modified")
    private List<String> modified;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("removed")
    private List<String> removed;

}
