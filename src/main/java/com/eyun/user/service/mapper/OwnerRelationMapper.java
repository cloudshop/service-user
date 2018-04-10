package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.OwnerRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OwnerRelation and its DTO OwnerRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class, MercuryMapper.class})
public interface OwnerRelationMapper extends EntityMapper<OwnerRelationDTO, OwnerRelation> {

    @Mapping(source = "userAnnex.id", target = "userAnnexId")
    @Mapping(source = "mercury.id", target = "mercuryId")
    OwnerRelationDTO toDto(OwnerRelation ownerRelation);

    @Mapping(source = "userAnnexId", target = "userAnnex")
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
