package com.eyun.user.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Authentication entity.
 */
public class AuthenticationDTO implements Serializable {

    private Long id;

    private String realName;

    private String idnuber;

    private String frontImg;

    private String reverseImg;

    private Integer status;

    private String statusString;

    private Instant createdTime;

    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdnuber() {
        return idnuber;
    }

    public void setIdnuber(String idnuber) {
        this.idnuber = idnuber;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getReverseImg() {
        return reverseImg;
    }

    public void setReverseImg(String reverseImg) {
        this.reverseImg = reverseImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthenticationDTO authenticationDTO = (AuthenticationDTO) o;
        if(authenticationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authenticationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuthenticationDTO{" +
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
