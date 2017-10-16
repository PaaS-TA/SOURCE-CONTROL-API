package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
public class Subrepository {
    @NotEmpty
    @JsonSerialize
    @JsonProperty("browserUrl")
    private String description;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("repositoryUrl")
    private String directory;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("revision")
    private String revision;
}
