package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
public class Files {
    @NotEmpty
    @JsonSerialize
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("directory")
    private Boolean directory;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("lastModified")
    private Number lastModified;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("length")
    private Number length;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("path")
    private String path;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("Subrepository")
    private Subrepository subrepository;

}
