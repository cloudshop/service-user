package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.DeliveryAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeliveryAddress and its DTO DeliveryAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class})
public interface DeliveryAddressMapper extends EntityMapper<DeliveryAddressDTO, DeliveryAddress> {

    @Mapping(source = "userAnnex.id", target = "userAnnexId")
    DeliveryAddressDTO toDto(DeliveryAddress deliveryAddress);

    @Mapping(source = "userAnnexId", target = "userAnnex")
    DeliveryAddress toEntity(DeliveryAddressDTO deliveryAddressDTO);

    default DeliveryAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setId(id);
        return deliveryAddress;
    }
}
