package com.paasta.scapi.entity;

import com.paasta.scapi.common.util.DateUtil;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lena on 2017-06-15.
 */
@Data
@Entity
@Table(name = "SC_REPOSITORY")
public class ScRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPO_NO")
    private int repoNo;
    @NotNull
    @Column(name = "REPO_SCM_ID")
    private String repoScmId;

    @Column(name = "REPO_NAME")
    private String repoName;
    @Column(name = "REPO_DESC")
    private String repoDesc;
    @Column(name = "INSTANCE_ID")
    private String instanceId;
    @Column(name = "OWNER_USER_ID")
    private String ownerUserId;
    @Column(name = "CREATE_USER_ID")
    private String createUserId;
    @Column(name = "CREATED_TIME")
    private String createTime;

    public ScRepository(){}
    public ScRepository(String id, String reponame, String mail, String desc) { }

    public ScRepository(String repoScmId, String repoName, String repoDesc, String instanceId,String createUserId, String ownerUserId) {
        this.repoScmId = repoScmId;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.instanceId = instanceId;
        this.createUserId = createUserId;
        this.ownerUserId = ownerUserId;
        createTime = DateUtil.currentDateTime();
    }

}
