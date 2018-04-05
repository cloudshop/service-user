package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserStatusHistory and its DTO UserStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class, UserStatusMapper.class})
public interface UserStatusHistoryMapper extends EntityMapper<UserStatusHistoryDTO, UserStatusHistory> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "oldStatus.id", target = "oldStatusId")
    @Mapping(source = "oldStatus.name", target = "oldStatusName")
    @Mapping(source = "newtatus.id", target = "newtatusId")
    @Mapping(source = "newtatus.name", target = "newtatusName")
    UserStatusHistoryDTO toDto(UserStatusHistory userStatusHistory);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "oldStatusId", target = "oldStatus")
    @Mapping(source = "newtatusId", target = "newtatus")
    UserStatusHistory toEntity(UserStatusHistoryDTO userStatusHistoryDTO);

    default UserStatusHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserStatusHistory userStatusHistory = new UserStatusHistory();
        userStatusHistory.setId(id);
        return userStatusHistory;
    }
}
