package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.DeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Delivery and its DTO DeliveryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class})
public interface DeliveryMapper extends EntityMapper<DeliveryDTO, Delivery> {

    @Mapping(source = "userAnnex.id", target = "userAnnexId")
    DeliveryDTO toDto(Delivery delivery);

    @Mapping(source = "userAnnexId", target = "userAnnex")
    Delivery toEntity(DeliveryDTO deliveryDTO);

    default Delivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delivery delivery = new Delivery();
        delivery.setId(id);
        return delivery;
    }
}
