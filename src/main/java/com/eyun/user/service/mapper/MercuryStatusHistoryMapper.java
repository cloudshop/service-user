package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MercuryStatusHistory and its DTO MercuryStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MercuryStatusHistoryMapper extends EntityMapper<MercuryStatusHistoryDTO, MercuryStatusHistory> {


    @Mapping(target = "mercuries", ignore = true)
    @Mapping(target = "oldStatuses", ignore = true)
    @Mapping(target = "newtatuses", ignore = true)
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
