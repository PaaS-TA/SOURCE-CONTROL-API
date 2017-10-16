package com.paasta.scapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by User on 2017-06-23.
 */
@Setter
@Getter
public class Branches {
    @NotEmpty
    @JsonSerialize
    @JsonProperty("branch")
    private List<Branch> branch;

//    public List<Branch> getBranch() {
//        return branch;
//    }
//
//    public void setBranch(List<Branch> branch) {
//        this.branch = branch;
//    }

    @Override
    public String toString() {
        return "Branches{" +
                "branch=" + this.branch +
                '}';
    }
}
