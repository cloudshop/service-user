package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MercuryStatus and its DTO MercuryStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {MercuryStatusHistoryMapper.class})
public interface MercuryStatusMapper extends EntityMapper<MercuryStatusDTO, MercuryStatus> {

    @Mapping(source = "mercuryStatusHistory.id", target = "mercuryStatusHistoryId")
    @Mapping(source = "mercuryStatusHistory.id", target = "mercuryStatusHistoryId")
    MercuryStatusDTO toDto(MercuryStatus mercuryStatus);

    @Mapping(source = "mercuryStatusHistoryId", target = "mercuryStatusHistory")
    @Mapping(source = "mercuryStatusHistoryId", target = "mercuryStatusHistory")
    MercuryStatus toEntity(MercuryStatusDTO mercuryStatusDTO);

    default MercuryStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MercuryStatus mercuryStatus = new MercuryStatus();
        mercuryStatus.setId(id);
        return mercuryStatus;
    }
}
