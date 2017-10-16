package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Setter
@Getter
public class Browse {
        @NotEmpty
        @JsonSerialize
        @JsonProperty("branch")
        private String branch;

        @NotEmpty
        @JsonSerialize
        @JsonProperty("files")
        private List<Files> files;

        @NotEmpty
        @JsonSerialize
        @JsonProperty("revision")
        private String revision;

        @NotEmpty
        @JsonSerialize
        @JsonProperty("tag")
        private String tag;

}
