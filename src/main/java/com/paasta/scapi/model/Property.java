package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Property {

    @JsonSerialize
    @JsonProperty("key")
    private String key;

    @JsonSerialize
    @JsonProperty("value")
    private String value;

    public Property(String key, String value){
        this.key = key;
        this.value = value;
    }
}
