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
 * Criteria class for the Authentication entity. This class is used in AuthenticationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /authentications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuthenticationCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter realName;

    private StringFilter idnuber;

    private StringFilter frontImg;

    private StringFilter reverseImg;

    private IntegerFilter status;

    private StringFilter statusString;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    public AuthenticationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRealName() {
        return realName;
    }

    public void setRealName(StringFilter realName) {
        this.realName = realName;
    }

    public StringFilter getIdnuber() {
        return idnuber;
    }

    public void setIdnuber(StringFilter idnuber) {
        this.idnuber = idnuber;
    }

    public StringFilter getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(StringFilter frontImg) {
        this.frontImg = frontImg;
    }

    public StringFilter getReverseImg() {
        return reverseImg;
    }

    public void setReverseImg(StringFilter reverseImg) {
        this.reverseImg = reverseImg;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getStatusString() {
        return statusString;
    }

    public void setStatusString(StringFilter statusString) {
        this.statusString = statusString;
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

    @Override
    public String toString() {
        return "AuthenticationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (realName != null ? "realName=" + realName + ", " : "") +
                (idnuber != null ? "idnuber=" + idnuber + ", " : "") +
                (frontImg != null ? "frontImg=" + frontImg + ", " : "") +
                (reverseImg != null ? "reverseImg=" + reverseImg + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (statusString != null ? "statusString=" + statusString + ", " : "") +
                (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
                (updatedTime != null ? "updatedTime=" + updatedTime + ", " : "") +
            "}";
    }

}
