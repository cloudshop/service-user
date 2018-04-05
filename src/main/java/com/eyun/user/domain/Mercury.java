package com.eyun.user.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Mercury.
 */
@Entity
@Table(name = "mercury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mercury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "name")
    private String name;

    @Column(name = "img_license")
    private String imgLicense;

    @Column(name = "img_idcard_front")
    private String imgIdcardFront;

    @Column(name = "img_idcard_back")
    private String imgIdcardBack;

    @Column(name = "img_idcard_hold")
    private String imgIdcardHold;

    @Column(name = "langitude")
    private Float langitude;

    @Column(name = "lantitude")
    private Float lantitude;

    @Column(name = "provice")
    private String provice;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "img_facade")
    private String imgFacade;

    @Column(name = "img_introduces")
    private String imgIntroduces;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToOne
    @JoinColumn(unique = true)
    private MercuryStatus status;

    @ManyToOne
    private MercuryStatusHistory mercuryStatusHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Mercury name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLicense() {
        return imgLicense;
    }

    public Mercury imgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
        return this;
    }

    public void setImgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
    }

    public String getImgIdcardFront() {
        return imgIdcardFront;
    }

    public Mercury imgIdcardFront(String imgIdcardFront) {
        this.imgIdcardFront = imgIdcardFront;
        return this;
    }

    public void setImgIdcardFront(String imgIdcardFront) {
        this.imgIdcardFront = imgIdcardFront;
    }

    public String getImgIdcardBack() {
        return imgIdcardBack;
    }

    public Mercury imgIdcardBack(String imgIdcardBack) {
        this.imgIdcardBack = imgIdcardBack;
        return this;
    }

    public void setImgIdcardBack(String imgIdcardBack) {
        this.imgIdcardBack = imgIdcardBack;
    }

    public String getImgIdcardHold() {
        return imgIdcardHold;
    }

    public Mercury imgIdcardHold(String imgIdcardHold) {
        this.imgIdcardHold = imgIdcardHold;
        return this;
    }

    public void setImgIdcardHold(String imgIdcardHold) {
        this.imgIdcardHold = imgIdcardHold;
    }

    public Float getLangitude() {
        return langitude;
    }

    public Mercury langitude(Float langitude) {
        this.langitude = langitude;
        return this;
    }

    public void setLangitude(Float langitude) {
        this.langitude = langitude;
    }

    public Float getLantitude() {
        return lantitude;
    }

    public Mercury lantitude(Float lantitude) {
        this.lantitude = lantitude;
        return this;
    }

    public void setLantitude(Float lantitude) {
        this.lantitude = lantitude;
    }

    public String getProvice() {
        return provice;
    }

    public Mercury provice(String provice) {
        this.provice = provice;
        return this;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public Mercury city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Mercury street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getImgFacade() {
        return imgFacade;
    }

    public Mercury imgFacade(String imgFacade) {
        this.imgFacade = imgFacade;
        return this;
    }

    public void setImgFacade(String imgFacade) {
        this.imgFacade = imgFacade;
    }

    public String getImgIntroduces() {
        return imgIntroduces;
    }

    public Mercury imgIntroduces(String imgIntroduces) {
        this.imgIntroduces = imgIntroduces;
        return this;
    }

    public void setImgIntroduces(String imgIntroduces) {
        this.imgIntroduces = imgIntroduces;
    }

    public String getDesc() {
        return desc;
    }

    public Mercury desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MercuryStatus getStatus() {
        return status;
    }

    public Mercury status(MercuryStatus mercuryStatus) {
        this.status = mercuryStatus;
        return this;
    }

    public void setStatus(MercuryStatus mercuryStatus) {
        this.status = mercuryStatus;
    }

    public MercuryStatusHistory getMercuryStatusHistory() {
        return mercuryStatusHistory;
    }

    public Mercury mercuryStatusHistory(MercuryStatusHistory mercuryStatusHistory) {
        this.mercuryStatusHistory = mercuryStatusHistory;
        return this;
    }

    public void setMercuryStatusHistory(MercuryStatusHistory mercuryStatusHistory) {
        this.mercuryStatusHistory = mercuryStatusHistory;
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
        Mercury mercury = (Mercury) o;
        if (mercury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mercury{" +
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
