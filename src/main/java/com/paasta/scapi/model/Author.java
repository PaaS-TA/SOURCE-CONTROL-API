package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
public class Author{

    @NotEmpty
    @JsonSerialize
    @JsonProperty("mail")
    private String mail;

    @NotEmpty
    @JsonSerialize
    @JsonProperty("name")
    private String name;

    @Override
    public String toString() {
        return "Author{" + "mail='" + this.mail + '\'' + ", name='" + this.name + '\'' + '}';
    }
}
