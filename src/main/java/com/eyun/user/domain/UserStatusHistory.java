package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
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

    @OneToOne
    @JoinColumn(unique = true)
    private UserAnnex user;

    @OneToOne
    @JoinColumn(unique = true)
    private UserStatus oldStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private UserStatus newtatus;

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

    public UserAnnex getUser() {
        return user;
    }

    public UserStatusHistory user(UserAnnex userAnnex) {
        this.user = userAnnex;
        return this;
    }

    public void setUser(UserAnnex userAnnex) {
        this.user = userAnnex;
    }

    public UserStatus getOldStatus() {
        return oldStatus;
    }

    public UserStatusHistory oldStatus(UserStatus userStatus) {
        this.oldStatus = userStatus;
        return this;
    }

    public void setOldStatus(UserStatus userStatus) {
        this.oldStatus = userStatus;
    }

    public UserStatus getNewtatus() {
        return newtatus;
    }

    public UserStatusHistory newtatus(UserStatus userStatus) {
        this.newtatus = userStatus;
        return this;
    }

    public void setNewtatus(UserStatus userStatus) {
        this.newtatus = userStatus;
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
