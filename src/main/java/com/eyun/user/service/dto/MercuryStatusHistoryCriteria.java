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
 * Criteria class for the MercuryStatusHistory entity. This class is used in MercuryStatusHistoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mercury-status-histories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MercuryStatusHistoryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter modifiedBy;

    private InstantFilter modifiedTime;

    private IntegerFilter withStatus;

    private IntegerFilter toStatus;

    private LongFilter mercuryId;

    public MercuryStatusHistoryCriteria() {
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

    public LongFilter getMercuryId() {
        return mercuryId;
    }

    public void setMercuryId(LongFilter mercuryId) {
        this.mercuryId = mercuryId;
    }

    @Override
    public String toString() {
        return "MercuryStatusHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedTime != null ? "modifiedTime=" + modifiedTime + ", " : "") +
                (withStatus != null ? "withStatus=" + withStatus + ", " : "") +
                (toStatus != null ? "toStatus=" + toStatus + ", " : "") +
                (mercuryId != null ? "mercuryId=" + mercuryId + ", " : "") +
            "}";
    }

}
