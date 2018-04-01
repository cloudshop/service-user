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

    private String firstName;

    private String lastName;

    private String nickname;

    private String email;

    private String mobile;

    private String avatar;

    private Long inviterId;

    private Set<UserTypeDTO> userTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long userAnnexId) {
        this.inviterId = userAnnexId;
    }

    public Set<UserTypeDTO> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(Set<UserTypeDTO> userTypes) {
        this.userTypes = userTypes;
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
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
