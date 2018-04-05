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

    private Long usernicknameId;

    private Long oldStatusnameId;

    private Long newtatusnameId;

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

    public Long getUsernicknameId() {
        return usernicknameId;
    }

    public void setUsernicknameId(Long userAnnexId) {
        this.usernicknameId = userAnnexId;
    }

    public Long getOldStatusnameId() {
        return oldStatusnameId;
    }

    public void setOldStatusnameId(Long userStatusId) {
        this.oldStatusnameId = userStatusId;
    }

    public Long getNewtatusnameId() {
        return newtatusnameId;
    }

    public void setNewtatusnameId(Long userStatusId) {
        this.newtatusnameId = userStatusId;
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
