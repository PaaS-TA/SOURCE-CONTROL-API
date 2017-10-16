package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by User on 2017-06-26.
 */
@Setter
@Getter
public class Tag{
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
        return "Tag{" +
                "name='" + this.name + '\'' +
                ", revision='" + this.revision + '\'' +
                '}';
        }
}
