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
 * Criteria class for the UserStatusHistory entity. This class is used in UserStatusHistoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-status-histories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserStatusHistoryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter modifiedBy;

    private InstantFilter modifiedTime;

    private LongFilter userid;

    private IntegerFilter withStatus;

    private IntegerFilter toStatus;

    private LongFilter userAnnexId;

    public UserStatusHistoryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(LongFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public InstantFilter getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(InstantFilter modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public LongFilter getUserid() {
        return userid;
    }

    public void setUserid(LongFilter userid) {
        this.userid = userid;
    }

    public IntegerFilter getWithStatus() {
        return withStatus;
    }

    public void setWithStatus(IntegerFilter withStatus) {
        this.withStatus = withStatus;
    }

    public IntegerFilter getToStatus() {
        return toStatus;
    }

    public void setToStatus(IntegerFilter toStatus) {
        this.toStatus = toStatus;
    }

    public LongFilter getUserAnnexId() {
        return userAnnexId;
    }

    public void setUserAnnexId(LongFilter userAnnexId) {
        this.userAnnexId = userAnnexId;
    }

    @Override
    public String toString() {
        return "UserStatusHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedTime != null ? "modifiedTime=" + modifiedTime + ", " : "") +
                (userid != null ? "userid=" + userid + ", " : "") +
                (withStatus != null ? "withStatus=" + withStatus + ", " : "") +
                (toStatus != null ? "toStatus=" + toStatus + ", " : "") +
                (userAnnexId != null ? "userAnnexId=" + userAnnexId + ", " : "") +
            "}";
    }

}
