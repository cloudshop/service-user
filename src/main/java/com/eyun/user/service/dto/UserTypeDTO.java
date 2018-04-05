package com.eyun.user.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the UserType entity.
 */
public class UserTypeDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] desc;
    private String descContentType;

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

    public byte[] getDesc() {
        return desc;
    }

    public void setDesc(byte[] desc) {
        this.desc = desc;
    }

    public String getDescContentType() {
        return descContentType;
    }

    public void setDescContentType(String descContentType) {
        this.descContentType = descContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserTypeDTO userTypeDTO = (UserTypeDTO) o;
        if(userTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
