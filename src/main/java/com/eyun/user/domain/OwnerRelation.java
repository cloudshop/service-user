package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OwnerRelation.
 */
@Entity
@Table(name = "owner_relation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OwnerRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private OwnerType ownerType;

    @OneToOne
    @JoinColumn(unique = true)
    private UserAnnex owner;

    @OneToOne
    @JoinColumn(unique = true)
    private Mercury mercury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public OwnerRelation ownerType(OwnerType ownerType) {
        this.ownerType = ownerType;
        return this;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public UserAnnex getOwner() {
        return owner;
    }

    public OwnerRelation owner(UserAnnex userAnnex) {
        this.owner = userAnnex;
        return this;
    }

    public void setOwner(UserAnnex userAnnex) {
        this.owner = userAnnex;
    }

    public Mercury getMercury() {
        return mercury;
    }

    public OwnerRelation mercury(Mercury mercury) {
        this.mercury = mercury;
        return this;
    }

    public void setMercury(Mercury mercury) {
        this.mercury = mercury;
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
        OwnerRelation ownerRelation = (OwnerRelation) o;
        if (ownerRelation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ownerRelation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OwnerRelation{" +
            "id=" + getId() +
            "}";
    }
}
