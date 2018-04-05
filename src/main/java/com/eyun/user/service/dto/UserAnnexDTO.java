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

    private Long statusnameId;

    private Set<UserTypeDTO> userTypenames = new HashSet<>();

    private Long inviteenicknameId;

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

    public Long getStatusnameId() {
        return statusnameId;
    }

    public void setStatusnameId(Long userStatusId) {
        this.statusnameId = userStatusId;
    }

    public Set<UserTypeDTO> getUserTypenames() {
        return userTypenames;
    }

    public void setUserTypenames(Set<UserTypeDTO> userTypes) {
        this.userTypenames = userTypes;
    }

    public Long getInviteenicknameId() {
        return inviteenicknameId;
    }

    public void setInviteenicknameId(Long userAnnexId) {
        this.inviteenicknameId = userAnnexId;
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
