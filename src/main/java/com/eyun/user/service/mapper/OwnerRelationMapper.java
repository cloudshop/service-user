package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.OwnerRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OwnerRelation and its DTO OwnerRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {OwnerTypeMapper.class, UserAnnexMapper.class, MercuryMapper.class})
public interface OwnerRelationMapper extends EntityMapper<OwnerRelationDTO, OwnerRelation> {

    @Mapping(source = "ownerTypename.id", target = "ownerTypenameId")
    @Mapping(source = "ownernickname.id", target = "ownernicknameId")
    @Mapping(source = "mercuryname.id", target = "mercurynameId")
    OwnerRelationDTO toDto(OwnerRelation ownerRelation);

    @Mapping(source = "ownerTypenameId", target = "ownerTypename")
    @Mapping(source = "ownernicknameId", target = "ownernickname")
    @Mapping(source = "mercurynameId", target = "mercuryname")
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
