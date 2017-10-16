package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Setter
@Getter
public class Changesets {

    @NotEmpty
    @JsonSerialize
    @JsonProperty("changesets")
    private List<Changeset> changesets;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("total")
    private Number total;

}
