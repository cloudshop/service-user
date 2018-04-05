package com.eyun.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "user_annex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAnnex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne
    @JoinColumn(unique = true)
    private UserStatus status;

    @OneToMany(mappedBy = "userAnnex")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryAddress> deliveryAddresses = new HashSet<>();

    @OneToMany(mappedBy = "invitee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAnnex> inviters = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_annex_user_type",
               joinColumns = @JoinColumn(name="user_annexes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_types_id", referencedColumnName="id"))
    private Set<UserType> userTypes = new HashSet<>();

    @ManyToOne
    private UserAnnex invitee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAnnex firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAnnex lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserAnnex email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserAnnex phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public UserAnnex nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserAnnex avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserAnnex status(UserStatus userStatus) {
        this.status = userStatus;
        return this;
    }

    public void setStatus(UserStatus userStatus) {
        this.status = userStatus;
    }

    public Set<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public UserAnnex deliveryAddresses(Set<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
        return this;
    }

    public UserAnnex addDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddresses.add(deliveryAddress);
        deliveryAddress.setUserAnnex(this);
        return this;
    }

    public UserAnnex removeDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddresses.remove(deliveryAddress);
        deliveryAddress.setUserAnnex(null);
        return this;
    }

    public void setDeliveryAddresses(Set<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public Set<UserAnnex> getInviters() {
        return inviters;
    }

    public UserAnnex inviters(Set<UserAnnex> userAnnexes) {
        this.inviters = userAnnexes;
        return this;
    }

    public UserAnnex addInviter(UserAnnex userAnnex) {
        this.inviters.add(userAnnex);
        userAnnex.setInvitee(this);
        return this;
    }

    public UserAnnex removeInviter(UserAnnex userAnnex) {
        this.inviters.remove(userAnnex);
        userAnnex.setInvitee(null);
        return this;
    }

    public void setInviters(Set<UserAnnex> userAnnexes) {
        this.inviters = userAnnexes;
    }

    public Set<UserType> getUserTypes() {
        return userTypes;
    }

    public UserAnnex userTypes(Set<UserType> userTypes) {
        this.userTypes = userTypes;
        return this;
    }

    public UserAnnex addUserType(UserType userType) {
        this.userTypes.add(userType);
        return this;
    }

    public UserAnnex removeUserType(UserType userType) {
        this.userTypes.remove(userType);
        return this;
    }

    public void setUserTypes(Set<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public UserAnnex getInvitee() {
        return invitee;
    }

    public UserAnnex invitee(UserAnnex userAnnex) {
        this.invitee = userAnnex;
        return this;
    }

    public void setInvitee(UserAnnex userAnnex) {
        this.invitee = userAnnex;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAnnex userAnnex = (UserAnnex) o;
        if (userAnnex.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAnnex.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAnnex{" +
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
