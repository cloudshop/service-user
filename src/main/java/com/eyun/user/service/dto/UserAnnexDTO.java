package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserAnnex entity.
 */
public class UserAnnexDTO implements Serializable {

    private Long id;

    private Long userId;

    private String name;

    private String email;

    private String phone;

    private String nickname;

    private String avatar;

    private Long statusId;

    private String statusName;

    private Set<UserTypeDTO> userTypes = new HashSet<>();

    private Long inviteeId;

    private String inviteeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long userStatusId) {
        this.statusId = userStatusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String userStatusName) {
        this.statusName = userStatusName;
    }

    public Set<UserTypeDTO> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(Set<UserTypeDTO> userTypes) {
        this.userTypes = userTypes;
    }

    public Long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(Long userAnnexId) {
        this.inviteeId = userAnnexId;
    }

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String userAnnexName) {
        this.inviteeName = userAnnexName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAnnexDTO userAnnexDTO = (UserAnnexDTO) o;
        if(userAnnexDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAnnexDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAnnexDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
