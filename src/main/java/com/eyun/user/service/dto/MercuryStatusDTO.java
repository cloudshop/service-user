package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MercuryStatus entity.
 */
public class MercuryStatusDTO implements Serializable {

    private Long id;

    private String name;

    private Long mercuryStatusHistoryId;

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

    public Long getMercuryStatusHistoryId() {
        return mercuryStatusHistoryId;
    }

    public void setMercuryStatusHistoryId(Long mercuryStatusHistoryId) {
        this.mercuryStatusHistoryId = mercuryStatusHistoryId;
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

        MercuryStatusDTO mercuryStatusDTO = (MercuryStatusDTO) o;
        if(mercuryStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mercuryStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MercuryStatusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}