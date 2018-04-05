package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Mercury entity.
 */
public class MercuryDTO implements Serializable {

    private Long id;

    private String name;

    private String imgLicense;

    private String imgIdcardFront;

    private String imgIdcardBack;

    private String imgIdcardHold;

    private Float langitude;

    private Float lantitude;

    private String provice;

    private String city;

    private String street;

    private String imgFacade;

    private String imgIntroduces;

    private String desc;

    private Long statusId;

    private Long mercuryStatusHistoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLicense() {
        return imgLicense;
    }

    public void setImgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
    }

    public String getImgIdcardFront() {
        return imgIdcardFront;
    }

    public void setImgIdcardFront(String imgIdcardFront) {
        this.imgIdcardFront = imgIdcardFront;
    }

    public String getImgIdcardBack() {
        return imgIdcardBack;
    }

    public void setImgIdcardBack(String imgIdcardBack) {
        this.imgIdcardBack = imgIdcardBack;
    }

    public String getImgIdcardHold() {
        return imgIdcardHold;
    }

    public void setImgIdcardHold(String imgIdcardHold) {
        this.imgIdcardHold = imgIdcardHold;
    }

    public Float getLangitude() {
        return langitude;
    }

    public void setLangitude(Float langitude) {
        this.langitude = langitude;
    }

    public Float getLantitude() {
        return lantitude;
    }

    public void setLantitude(Float lantitude) {
        this.lantitude = lantitude;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getImgFacade() {
        return imgFacade;
    }

    public void setImgFacade(String imgFacade) {
        this.imgFacade = imgFacade;
    }

    public String getImgIntroduces() {
        return imgIntroduces;
    }

    public void setImgIntroduces(String imgIntroduces) {
        this.imgIntroduces = imgIntroduces;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long mercuryStatusId) {
        this.statusId = mercuryStatusId;
    }

    public Long getMercuryStatusHistoryId() {
        return mercuryStatusHistoryId;
    }

    public void setMercuryStatusHistoryId(Long mercuryStatusHistoryId) {
        this.mercuryStatusHistoryId = mercuryStatusHistoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MercuryDTO mercuryDTO = (MercuryDTO) o;
        if(mercuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MercuryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imgLicense='" + getImgLicense() + "'" +
            ", imgIdcardFront='" + getImgIdcardFront() + "'" +
            ", imgIdcardBack='" + getImgIdcardBack() + "'" +
            ", imgIdcardHold='" + getImgIdcardHold() + "'" +
            ", langitude=" + getLangitude() +
            ", lantitude=" + getLantitude() +
            ", provice='" + getProvice() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", imgFacade='" + getImgFacade() + "'" +
            ", imgIntroduces='" + getImgIntroduces() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}