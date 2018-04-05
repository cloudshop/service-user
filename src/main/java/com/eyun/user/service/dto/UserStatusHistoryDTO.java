package com.eyun.user.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserStatusHistory entity.
 */
public class UserStatusHistoryDTO implements Serializable {

    private Long id;

    private Long modifiedBy;

    private Instant modifiedTime;

    private Long userId;

    private String userName;

    private Long oldStatusId;

    private String oldStatusName;

    private Long newtatusId;

    private String newtatusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userAnnexId) {
        this.userId = userAnnexId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userAnnexName) {
        this.userName = userAnnexName;
    }

    public Long getOldStatusId() {
        return oldStatusId;
    }

    public void setOldStatusId(Long userStatusId) {
        this.oldStatusId = userStatusId;
    }

    public String getOldStatusName() {
        return oldStatusName;
    }

    public void setOldStatusName(String userStatusName) {
        this.oldStatusName = userStatusName;
    }

    public Long getNewtatusId() {
        return newtatusId;
    }

    public void setNewtatusId(Long userStatusId) {
        this.newtatusId = userStatusId;
    }

    public String getNewtatusName() {
        return newtatusName;
    }

    public void setNewtatusName(String userStatusName) {
        this.newtatusName = userStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserStatusHistoryDTO userStatusHistoryDTO = (UserStatusHistoryDTO) o;
        if(userStatusHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userStatusHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserStatusHistoryDTO{" +
            "id=" + getId() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
