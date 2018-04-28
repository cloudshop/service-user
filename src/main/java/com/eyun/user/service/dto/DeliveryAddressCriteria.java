package com.eyun.user.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the DeliveryAddress entity. This class is used in DeliveryAddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /delivery-addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeliveryAddressCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter aliases;

    private StringFilter contact;

    private StringFilter phone;

    private StringFilter stateProvince;

    private StringFilter city;

    private StringFilter streetAddress;

    private StringFilter postalCode;

    private BooleanFilter defaultAddress;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    private LongFilter userAnnexId;

    public DeliveryAddressCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAliases() {
        return aliases;
    }

    public void setAliases(StringFilter aliases) {
        this.aliases = aliases;
    }

    public StringFilter getContact() {
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(StringFilter stateProvince) {
        this.stateProvince = stateProvince;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StringFilter streetAddress) {
        this.streetAddress = streetAddress;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public BooleanFilter getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(BooleanFilter defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public InstantFilter getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(InstantFilter updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LongFilter getUserAnnexId() {
        return userAnnexId;
    }

    public void setUserAnnexId(LongFilter userAnnexId) {
        this.userAnnexId = userAnnexId;
    }

    @Override
    public String toString() {
        return "DeliveryAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (aliases != null ? "aliases=" + aliases + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (stateProvince != null ? "stateProvince=" + stateProvince + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (defaultAddress != null ? "defaultAddress=" + defaultAddress + ", " : "") +
                (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
                (updatedTime != null ? "updatedTime=" + updatedTime + ", " : "") +
                (userAnnexId != null ? "userAnnexId=" + userAnnexId + ", " : "") +
            "}";
    }

}
