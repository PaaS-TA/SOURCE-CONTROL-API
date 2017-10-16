package com.paasta.scapi.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
@Getter
@Setter
public class User {
    public User() {

    }

    /**
     * Constructs ...
     *
     * @param name
     */
    public User(String name) {
        this.name = name;
        displayName = name;
    }

    /**
     * Constructs ...
     *
     * @param name
     * @param displayName
     * @param mail
     */
    public User(String name, String displayName, String mail) {
        this.name = name;
        this.displayName = displayName;
        this.mail = mail;
    }

    /**
     * {@inheritDoc}
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;

        return Objects.equal(this.name, other.name)
                && Objects.equal(this.displayName, other.displayName)
                && Objects.equal(this.mail, other.mail)
                && Objects.equal(this.type, other.type)
                && Objects.equal(this.admin, other.admin)
                && Objects.equal(this.active, other.active)
                && Objects.equal(this.password, other.password)
                && Objects.equal(this.creationDate, other.creationDate)
                && Objects.equal(this.lastModified, other.lastModified);
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    //~--- fields ---------------------------------------------------------------

    /**
     * Field description
     */
    private boolean active = true;

    /**
     * Field description
     */
    private boolean admin;

    /**
     * Field description
     */
    private Long creationDate;

    /**
     * Field description
     */
    private String displayName;

    /**
     * Field description
     */
    private Long lastModified;

    /**
     * Field description
     */
    private String mail;

    /**
     * Field description
     */
    private String name;

    /**
     * Field description
     */
    private String password;

    /**
     * Field description
     */
    private String type;
}