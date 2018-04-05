package com.eyun.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "mercuryStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mercury> mercuries = new HashSet<>();

    @OneToMany(mappedBy = "mercuryStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MercuryStatus> oldStatuses = new HashSet<>();

    @OneToMany(mappedBy = "mercuryStatusHistory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MercuryStatus> newtatuses = new HashSet<>();

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

    public Set<Mercury> getMercuries() {
        return mercuries;
    }

    public MercuryStatusHistory mercuries(Set<Mercury> mercuries) {
        this.mercuries = mercuries;
        return this;
    }

    public MercuryStatusHistory addMercury(Mercury mercury) {
        this.mercuries.add(mercury);
        mercury.setMercuryStatusHistory(this);
        return this;
    }

    public MercuryStatusHistory removeMercury(Mercury mercury) {
        this.mercuries.remove(mercury);
        mercury.setMercuryStatusHistory(null);
        return this;
    }

    public void setMercuries(Set<Mercury> mercuries) {
        this.mercuries = mercuries;
    }

    public Set<MercuryStatus> getOldStatuses() {
        return oldStatuses;
    }

    public MercuryStatusHistory oldStatuses(Set<MercuryStatus> mercuryStatuses) {
        this.oldStatuses = mercuryStatuses;
        return this;
    }

    public MercuryStatusHistory addOldStatus(MercuryStatus mercuryStatus) {
        this.oldStatuses.add(mercuryStatus);
        mercuryStatus.setMercuryStatusHistory(this);
        return this;
    }

    public MercuryStatusHistory removeOldStatus(MercuryStatus mercuryStatus) {
        this.oldStatuses.remove(mercuryStatus);
        mercuryStatus.setMercuryStatusHistory(null);
        return this;
    }

    public void setOldStatuses(Set<MercuryStatus> mercuryStatuses) {
        this.oldStatuses = mercuryStatuses;
    }

    public Set<MercuryStatus> getNewtatuses() {
        return newtatuses;
    }

    public MercuryStatusHistory newtatuses(Set<MercuryStatus> mercuryStatuses) {
        this.newtatuses = mercuryStatuses;
        return this;
    }

    public MercuryStatusHistory addNewtatus(MercuryStatus mercuryStatus) {
        this.newtatuses.add(mercuryStatus);
        mercuryStatus.setMercuryStatusHistory(this);
        return this;
    }

    public MercuryStatusHistory removeNewtatus(MercuryStatus mercuryStatus) {
        this.newtatuses.remove(mercuryStatus);
        mercuryStatus.setMercuryStatusHistory(null);
        return this;
    }

    public void setNewtatuses(Set<MercuryStatus> mercuryStatuses) {
        this.newtatuses = mercuryStatuses;
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
