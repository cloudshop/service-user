package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MercuryStatus.
 */
@Entity
@Table(name = "mercury_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MercuryStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "jhi_desc")
    private byte[] desc;

    @Column(name = "jhi_desc_content_type")
    private String descContentType;

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

    public MercuryStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDesc() {
        return desc;
    }

    public MercuryStatus desc(byte[] desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(byte[] desc) {
        this.desc = desc;
    }

    public String getDescContentType() {
        return descContentType;
    }

    public MercuryStatus descContentType(String descContentType) {
        this.descContentType = descContentType;
        return this;
    }

    public void setDescContentType(String descContentType) {
        this.descContentType = descContentType;
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
        MercuryStatus mercuryStatus = (MercuryStatus) o;
        if (mercuryStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercuryStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MercuryStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", descContentType='" + getDescContentType() + "'" +
            "}";
    }
}
