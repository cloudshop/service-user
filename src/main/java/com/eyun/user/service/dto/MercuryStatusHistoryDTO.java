package com.eyun.user.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MercuryStatusHistory entity.
 */
public class MercuryStatusHistoryDTO implements Serializable {

    private Long id;

    private Long modifiedBy;

    private Instant modifiedTime;

    private Long mercuryId;

    private String mercuryName;

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

    public Long getMercuryId() {
        return mercuryId;
    }

    public void setMercuryId(Long mercuryId) {
        this.mercuryId = mercuryId;
    }

    public String getMercuryName() {
        return mercuryName;
    }

    public void setMercuryName(String mercuryName) {
        this.mercuryName = mercuryName;
    }

    public Long getOldStatusId() {
        return oldStatusId;
    }

    public void setOldStatusId(Long mercuryStatusId) {
        this.oldStatusId = mercuryStatusId;
    }

    public String getOldStatusName() {
        return oldStatusName;
    }

    public void setOldStatusName(String mercuryStatusName) {
        this.oldStatusName = mercuryStatusName;
    }

    public Long getNewtatusId() {
        return newtatusId;
    }

    public void setNewtatusId(Long mercuryStatusId) {
        this.newtatusId = mercuryStatusId;
    }

    public String getNewtatusName() {
        return newtatusName;
    }

    public void setNewtatusName(String mercuryStatusName) {
        this.newtatusName = mercuryStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = (MercuryStatusHistoryDTO) o;
        if(mercuryStatusHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercuryStatusHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MercuryStatusHistoryDTO{" +
            "id=" + getId() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
