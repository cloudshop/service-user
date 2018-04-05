package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserType.
 */
@Entity
@Table(name = "user_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserType implements Serializable {

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

    public UserType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDesc() {
        return desc;
    }

    public UserType desc(byte[] desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(byte[] desc) {
        this.desc = desc;
    }

    public String getDescContentType() {
        return descContentType;
    }

    public UserType descContentType(String descContentType) {
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
        UserType userType = (UserType) o;
        if (userType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", descContentType='" + getDescContentType() + "'" +
            "}";
    }
}
