package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aliases")
    private String aliases;

    @Column(name = "contact")
    private String contact;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "postal")
    private String postal;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

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

    public Delivery aliases(String aliases) {
        this.aliases = aliases;
        return this;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getContact() {
        return contact;
    }

    public Delivery contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public Delivery mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public Delivery address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public Delivery postal(String postal) {
        this.postal = postal;
        return this;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public Delivery city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public Delivery province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public UserAnnex getUserAnnex() {
        return userAnnex;
    }

    public Delivery userAnnex(UserAnnex userAnnex) {
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
        Delivery delivery = (Delivery) o;
        if (delivery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delivery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", aliases='" + getAliases() + "'" +
            ", contact='" + getContact() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", address='" + getAddress() + "'" +
            ", postal='" + getPostal() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            "}";
    }
}
