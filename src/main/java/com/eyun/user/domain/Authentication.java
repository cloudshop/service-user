package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Authentication.
 */
@Entity
@Table(name = "authentication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authentication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "idnuber")
    private String idnuber;

    @Column(name = "front_img")
    private String frontImg;

    @Column(name = "reverse_img")
    private String reverseImg;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_string")
    private String statusString;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "updated_time")
    private Instant updatedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public Authentication realName(String realName) {
        this.realName = realName;
        return this;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdnuber() {
        return idnuber;
    }

    public Authentication idnuber(String idnuber) {
        this.idnuber = idnuber;
        return this;
    }

    public void setIdnuber(String idnuber) {
        this.idnuber = idnuber;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public Authentication frontImg(String frontImg) {
        this.frontImg = frontImg;
        return this;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getReverseImg() {
        return reverseImg;
    }

    public Authentication reverseImg(String reverseImg) {
        this.reverseImg = reverseImg;
        return this;
    }

    public void setReverseImg(String reverseImg) {
        this.reverseImg = reverseImg;
    }

    public Integer getStatus() {
        return status;
    }

    public Authentication status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public Authentication statusString(String statusString) {
        this.statusString = statusString;
        return this;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Authentication createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public Authentication updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
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
        Authentication authentication = (Authentication) o;
        if (authentication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authentication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Authentication{" +
            "id=" + getId() +
            ", realName='" + getRealName() + "'" +
            ", idnuber='" + getIdnuber() + "'" +
            ", frontImg='" + getFrontImg() + "'" +
            ", reverseImg='" + getReverseImg() + "'" +
            ", status=" + getStatus() +
            ", statusString='" + getStatusString() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
