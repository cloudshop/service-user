package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OwnerRelation entity.
 */
public class OwnerRelationDTO implements Serializable {

    private Long id;

    private Long ownerTypeId;

    private String ownerTypeName;

    private Long ownerId;

    private String ownerName;

    private Long mercuryId;

    private String mercuryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(Long ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public String getOwnerTypeName() {
        return ownerTypeName;
    }

    public void setOwnerTypeName(String ownerTypeName) {
        this.ownerTypeName = ownerTypeName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userAnnexId) {
        this.ownerId = userAnnexId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String userAnnexName) {
        this.ownerName = userAnnexName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OwnerRelationDTO ownerRelationDTO = (OwnerRelationDTO) o;
        if(ownerRelationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ownerRelationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OwnerRelationDTO{" +
            "id=" + getId() +
            "}";
    }
}
