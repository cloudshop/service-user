package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Delivery entity.
 */
public class DeliveryDTO implements Serializable {

    private Long id;

    private String aliases;

    private String contact;

    private String mobile;

    private String address;

    private String postal;

    private String city;

    private String province;

    private Long userAnnexId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getUserAnnexId() {
        return userAnnexId;
    }

    public void setUserAnnexId(Long userAnnexId) {
        this.userAnnexId = userAnnexId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryDTO deliveryDTO = (DeliveryDTO) o;
        if(deliveryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
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
