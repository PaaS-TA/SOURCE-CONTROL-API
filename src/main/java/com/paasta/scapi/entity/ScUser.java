package com.paasta.scapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "SC_USER")
public class ScUser {

    @Id
    @Column(name="USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_MAIL")
    private String userMail;
    @Column(name = "USER_DESC")
    private String userDesc;

    public ScUser() { }

    public ScUser(String userId, String userName, String userMail) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
    }
    public ScUser(String userId, String userName, String userMail, String userDesc) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.userDesc = userDesc;
    }

    @Override
    public String toString() {
        return "ScUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userDesc='" + userDesc + '\'' +
                '}';
    }
}