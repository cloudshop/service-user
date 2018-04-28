package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mercury and its DTO MercuryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MercuryMapper extends EntityMapper<MercuryDTO, Mercury> {


    @Mapping(target = "mercuryStatusHistories", ignore = true)
    @Mapping(target = "ownerRelation", ignore = true)
    Mercury toEntity(MercuryDTO mercuryDTO);

    default Mercury fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mercury mercury = new Mercury();
        mercury.setId(id);
        return mercury;
    }
}
