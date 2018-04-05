package com.eyun.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserStatusHistory.
 */
@Entity
@Table(name = "user_status_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_time")
    private Instant modifiedTime;

    @OneToMany(mappedBy = "userStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAnnex> users = new HashSet<>();

    @OneToMany(mappedBy = "userStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserStatus> oldStatuses = new HashSet<>();

    @OneToMany(mappedBy = "userStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserStatus> newtatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public UserStatusHistory modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public UserStatusHistory modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Set<UserAnnex> getUsers() {
        return users;
    }

    public UserStatusHistory users(Set<UserAnnex> userAnnexes) {
        this.users = userAnnexes;
        return this;
    }

    public UserStatusHistory addUser(UserAnnex userAnnex) {
        this.users.add(userAnnex);
        userAnnex.setUserStatusHistory(this);
        return this;
    }

    public UserStatusHistory removeUser(UserAnnex userAnnex) {
        this.users.remove(userAnnex);
        userAnnex.setUserStatusHistory(null);
        return this;
    }

    public void setUsers(Set<UserAnnex> userAnnexes) {
        this.users = userAnnexes;
    }

    public Set<UserStatus> getOldStatuses() {
        return oldStatuses;
    }

    public UserStatusHistory oldStatuses(Set<UserStatus> userStatuses) {
        this.oldStatuses = userStatuses;
        return this;
    }

    public UserStatusHistory addOldStatus(UserStatus userStatus) {
        this.oldStatuses.add(userStatus);
        userStatus.setUserStatusHistory(this);
        return this;
    }

    public UserStatusHistory removeOldStatus(UserStatus userStatus) {
        this.oldStatuses.remove(userStatus);
        userStatus.setUserStatusHistory(null);
        return this;
    }

    public void setOldStatuses(Set<UserStatus> userStatuses) {
        this.oldStatuses = userStatuses;
    }

    public Set<UserStatus> getNewtatuses() {
        return newtatuses;
    }

    public UserStatusHistory newtatuses(Set<UserStatus> userStatuses) {
        this.newtatuses = userStatuses;
        return this;
    }

    public UserStatusHistory addNewtatus(UserStatus userStatus) {
        this.newtatuses.add(userStatus);
        userStatus.setUserStatusHistory(this);
        return this;
    }

    public UserStatusHistory removeNewtatus(UserStatus userStatus) {
        this.newtatuses.remove(userStatus);
        userStatus.setUserStatusHistory(null);
        return this;
    }

    public void setNewtatuses(Set<UserStatus> userStatuses) {
        this.newtatuses = userStatuses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStatusHistory userStatusHistory = (UserStatusHistory) o;
        if (userStatusHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userStatusHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserStatusHistory{" +
            "id=" + getId() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
