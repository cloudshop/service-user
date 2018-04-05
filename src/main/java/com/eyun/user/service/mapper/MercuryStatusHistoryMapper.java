package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MercuryStatusHistory and its DTO MercuryStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {MercuryMapper.class, MercuryStatusMapper.class})
public interface MercuryStatusHistoryMapper extends EntityMapper<MercuryStatusHistoryDTO, MercuryStatusHistory> {

    @Mapping(source = "mercury.id", target = "mercuryId")
    @Mapping(source = "mercury.name", target = "mercuryName")
    @Mapping(source = "oldStatus.id", target = "oldStatusId")
    @Mapping(source = "oldStatus.name", target = "oldStatusName")
    @Mapping(source = "newtatus.id", target = "newtatusId")
    @Mapping(source = "newtatus.name", target = "newtatusName")
    MercuryStatusHistoryDTO toDto(MercuryStatusHistory mercuryStatusHistory);

    @Mapping(source = "mercuryId", target = "mercury")
    @Mapping(source = "oldStatusId", target = "oldStatus")
    @Mapping(source = "newtatusId", target = "newtatus")
    MercuryStatusHistory toEntity(MercuryStatusHistoryDTO mercuryStatusHistoryDTO);

    default MercuryStatusHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MercuryStatusHistory mercuryStatusHistory = new MercuryStatusHistory();
        mercuryStatusHistory.setId(id);
        return mercuryStatusHistory;
    }
}
