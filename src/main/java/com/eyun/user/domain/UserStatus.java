package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserStatus.
 */
@Entity
@Table(name = "user_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private UserStatusHistory userStatusHistory;

    @ManyToOne
    private UserStatusHistory userStatusHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatusHistory getUserStatusHistory() {
        return userStatusHistory;
    }

    public UserStatus userStatusHistory(UserStatusHistory userStatusHistory) {
        this.userStatusHistory = userStatusHistory;
        return this;
    }

    public void setUserStatusHistory(UserStatusHistory userStatusHistory) {
        this.userStatusHistory = userStatusHistory;
    }

    public UserStatusHistory getUserStatusHistory() {
        return userStatusHistory;
    }

    public UserStatus userStatusHistory(UserStatusHistory userStatusHistory) {
        this.userStatusHistory = userStatusHistory;
        return this;
    }

    public void setUserStatusHistory(UserStatusHistory userStatusHistory) {
        this.userStatusHistory = userStatusHistory;
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
        UserStatus userStatus = (UserStatus) o;
        if (userStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
