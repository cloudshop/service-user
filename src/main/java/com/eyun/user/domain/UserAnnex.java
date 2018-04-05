package com.eyun.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserAnnex.
 */
@Entity
@Table(name = "user_annex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAnnex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne
    @JoinColumn(unique = true)
    private UserStatus statusname;

    @OneToMany(mappedBy = "userAnnex")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryAddress> deliveryAddressaliases = new HashSet<>();

    @OneToMany(mappedBy = "invitee[nickname]")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAnnex> inviternicknames = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_annex_user_typename",
               joinColumns = @JoinColumn(name="user_annexes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_typenames_id", referencedColumnName="id"))
    private Set<UserType> userTypenames = new HashSet<>();

    @ManyToOne
    private UserAnnex inviteenickname;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public UserAnnex userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public UserAnnex name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public UserAnnex phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public UserStatus getStatusname() {
        return statusname;
    }

    public UserAnnex statusname(UserStatus userStatus) {
        this.statusname = userStatus;
        return this;
    }

    public void setStatusname(UserStatus userStatus) {
        this.statusname = userStatus;
    }

    public Set<DeliveryAddress> getDeliveryAddressaliases() {
        return deliveryAddressaliases;
    }

    public UserAnnex deliveryAddressaliases(Set<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddressaliases = deliveryAddresses;
        return this;
    }

    public UserAnnex addDeliveryAddressaliases(DeliveryAddress deliveryAddress) {
        this.deliveryAddressaliases.add(deliveryAddress);
        deliveryAddress.setUserAnnex(this);
        return this;
    }

    public UserAnnex removeDeliveryAddressaliases(DeliveryAddress deliveryAddress) {
        this.deliveryAddressaliases.remove(deliveryAddress);
        deliveryAddress.setUserAnnex(null);
        return this;
    }

    public void setDeliveryAddressaliases(Set<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddressaliases = deliveryAddresses;
    }

    public Set<UserAnnex> getInviternicknames() {
        return inviternicknames;
    }

    public UserAnnex inviternicknames(Set<UserAnnex> userAnnexes) {
        this.inviternicknames = userAnnexes;
        return this;
    }

    public UserAnnex addInviternickname(UserAnnex userAnnex) {
        this.inviternicknames.add(userAnnex);
        userAnnex.setInvitee[nickname](this);
        return this;
    }

    public UserAnnex removeInviternickname(UserAnnex userAnnex) {
        this.inviternicknames.remove(userAnnex);
        userAnnex.setInvitee[nickname](null);
        return this;
    }

    public void setInviternicknames(Set<UserAnnex> userAnnexes) {
        this.inviternicknames = userAnnexes;
    }

    public Set<UserType> getUserTypenames() {
        return userTypenames;
    }

    public UserAnnex userTypenames(Set<UserType> userTypes) {
        this.userTypenames = userTypes;
        return this;
    }

    public UserAnnex addUserTypename(UserType userType) {
        this.userTypenames.add(userType);
        return this;
    }

    public UserAnnex removeUserTypename(UserType userType) {
        this.userTypenames.remove(userType);
        return this;
    }

    public void setUserTypenames(Set<UserType> userTypes) {
        this.userTypenames = userTypes;
    }

    public UserAnnex getInviteenickname() {
        return inviteenickname;
    }

    public UserAnnex inviteenickname(UserAnnex userAnnex) {
        this.inviteenickname = userAnnex;
        return this;
    }

    public void setInviteenickname(UserAnnex userAnnex) {
        this.inviteenickname = userAnnex;
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
            ", userId=" + getUserId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
