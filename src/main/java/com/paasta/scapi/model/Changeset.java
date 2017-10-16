package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Setter
@Getter
public class Changeset {
    @NotEmpty
    @JsonSerialize
    @JsonProperty("author")
    private Author author;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("modifications")
    private Modifications modifications;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("branches")
    private List<String> branch;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("date")
    private Number date;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("id")
    private String id;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("parents")
    private List<String> parents;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("tags")
    private List<String> tags;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("properties")
    private List<Property> properties;

}
