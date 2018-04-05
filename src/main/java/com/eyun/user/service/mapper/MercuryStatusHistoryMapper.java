package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MercuryStatusHistory and its DTO MercuryStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {MercuryMapper.class, MercuryStatusMapper.class})
public interface MercuryStatusHistoryMapper extends EntityMapper<MercuryStatusHistoryDTO, MercuryStatusHistory> {

    @Mapping(source = "mercuryname.id", target = "mercurynameId")
    @Mapping(source = "oldStatusname.id", target = "oldStatusnameId")
    @Mapping(source = "newtatusname.id", target = "newtatusnameId")
    MercuryStatusHistoryDTO toDto(MercuryStatusHistory mercuryStatusHistory);

    @Mapping(source = "mercurynameId", target = "mercuryname")
    @Mapping(source = "oldStatusnameId", target = "oldStatusname")
    @Mapping(source = "newtatusnameId", target = "newtatusname")
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
