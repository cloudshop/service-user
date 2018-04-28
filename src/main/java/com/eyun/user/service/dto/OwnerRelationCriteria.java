package com.eyun.user.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the OwnerRelation entity. This class is used in OwnerRelationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /owner-relations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OwnerRelationCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter roleName;

    private StringFilter description;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    private LongFilter userAnnexId;

    private LongFilter mercuryId;

    public OwnerRelationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRoleName() {
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public InstantFilter getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(InstantFilter updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LongFilter getUserAnnexId() {
        return userAnnexId;
    }

    public void setUserAnnexId(LongFilter userAnnexId) {
        this.userAnnexId = userAnnexId;
    }

    public LongFilter getMercuryId() {
        return mercuryId;
    }

    public void setMercuryId(LongFilter mercuryId) {
        this.mercuryId = mercuryId;
    }

    @Override
    public String toString() {
        return "OwnerRelationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (roleName != null ? "roleName=" + roleName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
                (updatedTime != null ? "updatedTime=" + updatedTime + ", " : "") +
                (userAnnexId != null ? "userAnnexId=" + userAnnexId + ", " : "") +
                (mercuryId != null ? "mercuryId=" + mercuryId + ", " : "") +
            "}";
    }

}
