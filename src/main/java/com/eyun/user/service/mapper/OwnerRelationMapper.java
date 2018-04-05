package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.OwnerRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OwnerRelation and its DTO OwnerRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {OwnerTypeMapper.class, UserAnnexMapper.class, MercuryMapper.class})
public interface OwnerRelationMapper extends EntityMapper<OwnerRelationDTO, OwnerRelation> {

    @Mapping(source = "ownerType.id", target = "ownerTypeId")
    @Mapping(source = "ownerType.name", target = "ownerTypeName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    @Mapping(source = "mercury.id", target = "mercuryId")
    @Mapping(source = "mercury.name", target = "mercuryName")
    OwnerRelationDTO toDto(OwnerRelation ownerRelation);

    @Mapping(source = "ownerTypeId", target = "ownerType")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "mercuryId", target = "mercury")
    OwnerRelation toEntity(OwnerRelationDTO ownerRelationDTO);

    default OwnerRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        OwnerRelation ownerRelation = new OwnerRelation();
        ownerRelation.setId(id);
        return ownerRelation;
    }
}
