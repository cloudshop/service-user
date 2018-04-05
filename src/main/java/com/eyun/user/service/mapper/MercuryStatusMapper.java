package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.MercuryStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MercuryStatus and its DTO MercuryStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MercuryStatusMapper extends EntityMapper<MercuryStatusDTO, MercuryStatus> {



    default MercuryStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MercuryStatus mercuryStatus = new MercuryStatus();
        mercuryStatus.setId(id);
        return mercuryStatus;
    }
}
