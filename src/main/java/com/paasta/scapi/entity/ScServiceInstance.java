package com.paasta.scapi.entity;


import com.paasta.scapi.common.util.DateUtil;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SERVICE_INSTANCE")
public class ScServiceInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INSTANCE_ID")
    private String instanceId;
    @Column(name = "ORGANIZATION_GUID")
    private String organizationGuid;
    @Column(name = "ORGANIZATION_NAME")
    private String organizationName;
    @Column(name = "PLAN_ID")
    private String planId;
    @Column(name = "SERVICE_ID")
    private String seryiceId;
    @Column(name = "SPACE_GUID")
    private String spaceGuid;
    @Column(name = "CREATE_USER_ID")
    private String createUserId;
    @Column(name = "CREATED_TIME")
    private String createdTime;

    public ScServiceInstance(){}

    public ScServiceInstance(String instanceId, String organizationGuid, String organizationName, String planId,
                             String seryiceId, String spaceGuid, String createUserId) {
        this.instanceId = instanceId;
        this.organizationGuid = organizationGuid;
        this.organizationName = organizationName;
        this.planId = planId;
        this.seryiceId = seryiceId;
        this.spaceGuid =spaceGuid;
        this.createUserId = createUserId;
        createdTime = DateUtil.currentDateTime();
    }
}





