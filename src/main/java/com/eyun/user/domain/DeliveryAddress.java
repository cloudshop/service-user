package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DeliveryAddress.
 */
@Entity
@Table(name = "delivery_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aliases")
    private String aliases;

    @Column(name = "contact")
    private String contact;

    @Column(name = "phone")
    private String phone;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "city")
    private String city;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    private UserAnnex userAnnex;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliases() {
        return aliases;
    }

    public DeliveryAddress aliases(String aliases) {
        this.aliases = aliases;
        return this;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getContact() {
        return contact;
    }

    public DeliveryAddress contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public DeliveryAddress phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public DeliveryAddress stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCity() {
        return city;
    }

    public DeliveryAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public DeliveryAddress streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public DeliveryAddress postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public UserAnnex getUserAnnex() {
        return userAnnex;
    }

    public DeliveryAddress userAnnex(UserAnnex userAnnex) {
        this.userAnnex = userAnnex;
        return this;
    }

    public void setUserAnnex(UserAnnex userAnnex) {
        this.userAnnex = userAnnex;
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
        DeliveryAddress deliveryAddress = (DeliveryAddress) o;
        if (deliveryAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
            "id=" + getId() +
            ", aliases='" + getAliases() + "'" +
            ", contact='" + getContact() + "'" +
            ", phone='" + getPhone() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            "}";
    }
}
