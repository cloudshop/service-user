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

    private String email;

    private String phoneNumber;

    private String nickname;

    private String avatar;

    private Long statusId;

    private Set<UserTypeDTO> userTypes = new HashSet<>();

    private Long inviteeId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
