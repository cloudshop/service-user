package com.eyun.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MercuryStatusHistory.
 */
@Entity
@Table(name = "mercury_status_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MercuryStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_time")
    private Instant modifiedTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Mercury mercuryname;

    @OneToOne
    @JoinColumn(unique = true)
    private MercuryStatus oldStatusname;

    @OneToOne
    @JoinColumn(unique = true)
    private MercuryStatus newtatusname;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public MercuryStatusHistory modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public MercuryStatusHistory modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Mercury getMercuryname() {
        return mercuryname;
    }

    public MercuryStatusHistory mercuryname(Mercury mercury) {
        this.mercuryname = mercury;
        return this;
    }

    public void setMercuryname(Mercury mercury) {
        this.mercuryname = mercury;
    }

    public MercuryStatus getOldStatusname() {
        return oldStatusname;
    }

    public MercuryStatusHistory oldStatusname(MercuryStatus mercuryStatus) {
        this.oldStatusname = mercuryStatus;
        return this;
    }

    public void setOldStatusname(MercuryStatus mercuryStatus) {
        this.oldStatusname = mercuryStatus;
    }

    public MercuryStatus getNewtatusname() {
        return newtatusname;
    }

    public MercuryStatusHistory newtatusname(MercuryStatus mercuryStatus) {
        this.newtatusname = mercuryStatus;
        return this;
    }

    public void setNewtatusname(MercuryStatus mercuryStatus) {
        this.newtatusname = mercuryStatus;
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
        MercuryStatusHistory mercuryStatusHistory = (MercuryStatusHistory) o;
        if (mercuryStatusHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercuryStatusHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MercuryStatusHistory{" +
            "id=" + getId() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
