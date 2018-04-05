package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserStatus and its DTO UserStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {UserStatusHistoryMapper.class})
public interface UserStatusMapper extends EntityMapper<UserStatusDTO, UserStatus> {

    @Mapping(source = "userStatusHistory.id", target = "userStatusHistoryId")
    @Mapping(source = "userStatusHistory.id", target = "userStatusHistoryId")
    UserStatusDTO toDto(UserStatus userStatus);

    @Mapping(source = "userStatusHistoryId", target = "userStatusHistory")
    @Mapping(source = "userStatusHistoryId", target = "userStatusHistory")
    UserStatus toEntity(UserStatusDTO userStatusDTO);

    default UserStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserStatus userStatus = new UserStatus();
        userStatus.setId(id);
        return userStatus;
    }
}
