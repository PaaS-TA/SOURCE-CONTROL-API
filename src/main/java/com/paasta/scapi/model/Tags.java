package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

 @Setter
 @Getter
public class Tags {
    @NotEmpty
    @JsonSerialize
    @JsonProperty("tag")
    private List<Tag> tag;

    @Override
    public String toString() {
        return "Tags{" +
                "tag=" + this.tag +
                '}';
    }
}

