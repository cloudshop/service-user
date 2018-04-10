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
 * Criteria class for the Mercury entity. This class is used in MercuryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mercuries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MercuryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter imgLicense;

    private StringFilter imgIdcardFront;

    private StringFilter imgIdcardBack;

    private StringFilter imgIdcardHold;

    private DoubleFilter langitude;

    private DoubleFilter lantitude;

    private StringFilter provice;

    private StringFilter city;

    private StringFilter street;

    private StringFilter imgFacade;

    private StringFilter imgIntroduces;

    private IntegerFilter status;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    private LongFilter mercuryStatusHistoryId;

    private LongFilter ownerRelationId;

    public MercuryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getImgLicense() {
        return imgLicense;
    }

    public void setImgLicense(StringFilter imgLicense) {
        this.imgLicense = imgLicense;
    }

    public StringFilter getImgIdcardFront() {
        return imgIdcardFront;
    }

    public void setImgIdcardFront(StringFilter imgIdcardFront) {
        this.imgIdcardFront = imgIdcardFront;
    }

    public StringFilter getImgIdcardBack() {
        return imgIdcardBack;
    }

    public void setImgIdcardBack(StringFilter imgIdcardBack) {
        this.imgIdcardBack = imgIdcardBack;
    }

    public StringFilter getImgIdcardHold() {
        return imgIdcardHold;
    }

    public void setImgIdcardHold(StringFilter imgIdcardHold) {
        this.imgIdcardHold = imgIdcardHold;
    }

    public DoubleFilter getLangitude() {
        return langitude;
    }

    public void setLangitude(DoubleFilter langitude) {
        this.langitude = langitude;
    }

    public DoubleFilter getLantitude() {
        return lantitude;
    }

    public void setLantitude(DoubleFilter lantitude) {
        this.lantitude = lantitude;
    }

    public StringFilter getProvice() {
        return provice;
    }

    public void setProvice(StringFilter provice) {
        this.provice = provice;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getImgFacade() {
        return imgFacade;
    }

    public void setImgFacade(StringFilter imgFacade) {
        this.imgFacade = imgFacade;
    }

    public StringFilter getImgIntroduces() {
        return imgIntroduces;
    }

    public void setImgIntroduces(StringFilter imgIntroduces) {
        this.imgIntroduces = imgIntroduces;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
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

    public LongFilter getMercuryStatusHistoryId() {
        return mercuryStatusHistoryId;
    }

    public void setMercuryStatusHistoryId(LongFilter mercuryStatusHistoryId) {
        this.mercuryStatusHistoryId = mercuryStatusHistoryId;
    }

    public LongFilter getOwnerRelationId() {
        return ownerRelationId;
    }

    public void setOwnerRelationId(LongFilter ownerRelationId) {
        this.ownerRelationId = ownerRelationId;
    }

    @Override
    public String toString() {
        return "MercuryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (imgLicense != null ? "imgLicense=" + imgLicense + ", " : "") +
                (imgIdcardFront != null ? "imgIdcardFront=" + imgIdcardFront + ", " : "") +
                (imgIdcardBack != null ? "imgIdcardBack=" + imgIdcardBack + ", " : "") +
                (imgIdcardHold != null ? "imgIdcardHold=" + imgIdcardHold + ", " : "") +
                (langitude != null ? "langitude=" + langitude + ", " : "") +
                (lantitude != null ? "lantitude=" + lantitude + ", " : "") +
                (provice != null ? "provice=" + provice + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (imgFacade != null ? "imgFacade=" + imgFacade + ", " : "") +
                (imgIntroduces != null ? "imgIntroduces=" + imgIntroduces + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
                (updatedTime != null ? "updatedTime=" + updatedTime + ", " : "") +
                (mercuryStatusHistoryId != null ? "mercuryStatusHistoryId=" + mercuryStatusHistoryId + ", " : "") +
                (ownerRelationId != null ? "ownerRelationId=" + ownerRelationId + ", " : "") +
            "}";
    }

}
