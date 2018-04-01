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
 * The UserAnnex entity.
 */
@ApiModel(description = "The UserAnnex entity.")
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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "avatar")
    private String avatar;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "userAnnex")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Delivery> deliveries = new HashSet<>();

    @ManyToOne
    private UserAnnex inviter;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_annex_user_types",
               joinColumns = @JoinColumn(name="user_annexes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_types_id", referencedColumnName="id"))
    private Set<UserType> userTypes = new HashSet<>();

    @OneToMany(mappedBy = "inviter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAnnex> invitees = new HashSet<>();

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

    public String getMobile() {
        return mobile;
    }

    public UserAnnex mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    public UserAnnex deliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
        return this;
    }

    public UserAnnex addDeliveries(Delivery delivery) {
        this.deliveries.add(delivery);
        delivery.setUserAnnex(this);
        return this;
    }

    public UserAnnex removeDeliveries(Delivery delivery) {
        this.deliveries.remove(delivery);
        delivery.setUserAnnex(null);
        return this;
    }

    public void setDeliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public UserAnnex getInviter() {
        return inviter;
    }

    public UserAnnex inviter(UserAnnex userAnnex) {
        this.inviter = userAnnex;
        return this;
    }

    public void setInviter(UserAnnex userAnnex) {
        this.inviter = userAnnex;
    }

    public Set<UserType> getUserTypes() {
        return userTypes;
    }

    public UserAnnex userTypes(Set<UserType> userTypes) {
        this.userTypes = userTypes;
        return this;
    }

    public UserAnnex addUserTypes(UserType userType) {
        this.userTypes.add(userType);
        return this;
    }

    public UserAnnex removeUserTypes(UserType userType) {
        this.userTypes.remove(userType);
        return this;
    }

    public void setUserTypes(Set<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public Set<UserAnnex> getInvitees() {
        return invitees;
    }

    public UserAnnex invitees(Set<UserAnnex> userAnnexes) {
        this.invitees = userAnnexes;
        return this;
    }

    public UserAnnex addInvitee(UserAnnex userAnnex) {
        this.invitees.add(userAnnex);
        userAnnex.setInviter(this);
        return this;
    }

    public UserAnnex removeInvitee(UserAnnex userAnnex) {
        this.invitees.remove(userAnnex);
        userAnnex.setInviter(null);
        return this;
    }

    public void setInvitees(Set<UserAnnex> userAnnexes) {
        this.invitees = userAnnexes;
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
            ", nickname='" + getNickname() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
