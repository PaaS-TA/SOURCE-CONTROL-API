package com.paasta.scapi.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SC_INSTANCE_USER")
public class ScInstanceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NO")
    private int no;

    @Column(name = "INSTANCE_ID")
    private String instanceId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "REPO_ROLE")
    private String repoRole;

    @Column(name = "CREATER_YN")
    private String createrYn;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    public ScInstanceUser(){}

    public ScInstanceUser(String instanceId, String userId, String repoRole, String createrYn) {
        this.instanceId = instanceId;
        this.userId = userId;
        this.repoRole = repoRole;
        this.createrYn = createrYn;
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }
}





