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
 * Criteria class for the UserAnnex entity. This class is used in UserAnnexResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-annexes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserAnnexCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter nickname;

    private StringFilter avatar;

    private IntegerFilter status;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    private IntegerFilter type;

    private LongFilter inviterId;

    private LongFilter ownerRelationId;

    private LongFilter userStatusHistoryId;

    public UserAnnexCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getNickname() {
        return nickname;
    }

    public void setNickname(StringFilter nickname) {
        this.nickname = nickname;
    }

    public StringFilter getAvatar() {
        return avatar;
    }

    public void setAvatar(StringFilter avatar) {
        this.avatar = avatar;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
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

    public IntegerFilter getType() {
        return type;
    }

    public void setType(IntegerFilter type) {
        this.type = type;
    }

    public LongFilter getInviterId() {
        return inviterId;
    }

    public void setInviterId(LongFilter inviterId) {
        this.inviterId = inviterId;
    }

    public LongFilter getOwnerRelationId() {
        return ownerRelationId;
    }

    public void setOwnerRelationId(LongFilter ownerRelationId) {
        this.ownerRelationId = ownerRelationId;
    }

    public LongFilter getUserStatusHistoryId() {
        return userStatusHistoryId;
    }

    public void setUserStatusHistoryId(LongFilter userStatusHistoryId) {
        this.userStatusHistoryId = userStatusHistoryId;
    }

    @Override
    public String toString() {
        return "UserAnnexCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (nickname != null ? "nickname=" + nickname + ", " : "") +
                (avatar != null ? "avatar=" + avatar + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
                (updatedTime != null ? "updatedTime=" + updatedTime + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (inviterId != null ? "inviterId=" + inviterId + ", " : "") +
                (ownerRelationId != null ? "ownerRelationId=" + ownerRelationId + ", " : "") +
                (userStatusHistoryId != null ? "userStatusHistoryId=" + userStatusHistoryId + ", " : "") +
            "}";
    }

}
