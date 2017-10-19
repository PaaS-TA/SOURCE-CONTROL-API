package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by injeong Lee on 2017-06-23.
 */
@Setter
@Getter
public class Branch {

    @NotEmpty
    @JsonSerialize
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("revision")
    private String revision;


    @Override
    public String toString() {
        return "Branch{" +
                "name='" + this.name + '\'' +
                ", revision='" + this.revision + '\'' +
                '}';
    }
}
