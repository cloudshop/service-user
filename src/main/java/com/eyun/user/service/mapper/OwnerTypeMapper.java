package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.OwnerTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OwnerType and its DTO OwnerTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnerTypeMapper extends EntityMapper<OwnerTypeDTO, OwnerType> {



    default OwnerType fromId(Long id) {
        if (id == null) {
            return null;
        }
        OwnerType ownerType = new OwnerType();
        ownerType.setId(id);
        return ownerType;
    }
}
