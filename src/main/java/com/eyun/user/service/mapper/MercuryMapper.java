package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mercury and its DTO MercuryDTO.
 */
@Mapper(componentModel = "spring", uses = {MercuryStatusMapper.class, MercuryStatusHistoryMapper.class})
public interface MercuryMapper extends EntityMapper<MercuryDTO, Mercury> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "mercuryStatusHistory.id", target = "mercuryStatusHistoryId")
    MercuryDTO toDto(Mercury mercury);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "mercuryStatusHistoryId", target = "mercuryStatusHistory")
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
