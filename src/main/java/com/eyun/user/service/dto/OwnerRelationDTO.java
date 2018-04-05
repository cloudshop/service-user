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

    private Long ownerTypenameId;

    private Long ownernicknameId;

    private Long mercurynameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerTypenameId() {
        return ownerTypenameId;
    }

    public void setOwnerTypenameId(Long ownerTypeId) {
        this.ownerTypenameId = ownerTypeId;
    }

    public Long getOwnernicknameId() {
        return ownernicknameId;
    }

    public void setOwnernicknameId(Long userAnnexId) {
        this.ownernicknameId = userAnnexId;
    }

    public Long getMercurynameId() {
        return mercurynameId;
    }

    public void setMercurynameId(Long mercuryId) {
        this.mercurynameId = mercuryId;
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
