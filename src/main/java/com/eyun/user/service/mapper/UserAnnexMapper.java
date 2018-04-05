package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserAnnexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAnnex and its DTO UserAnnexDTO.
 */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class, UserTypeMapper.class, UserStatusHistoryMapper.class})
public interface UserAnnexMapper extends EntityMapper<UserAnnexDTO, UserAnnex> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "invitee.id", target = "inviteeId")
    @Mapping(source = "userStatusHistory.id", target = "userStatusHistoryId")
    UserAnnexDTO toDto(UserAnnex userAnnex);

    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "deliveryAddresses", ignore = true)
    @Mapping(target = "inviters", ignore = true)
    @Mapping(source = "inviteeId", target = "invitee")
    @Mapping(source = "userStatusHistoryId", target = "userStatusHistory")
    UserAnnex toEntity(UserAnnexDTO userAnnexDTO);

    default UserAnnex fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAnnex userAnnex = new UserAnnex();
        userAnnex.setId(id);
        return userAnnex;
    }
}
