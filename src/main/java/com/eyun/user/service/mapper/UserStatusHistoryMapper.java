package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserStatusHistory and its DTO UserStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class, UserStatusMapper.class})
public interface UserStatusHistoryMapper extends EntityMapper<UserStatusHistoryDTO, UserStatusHistory> {

    @Mapping(source = "usernickname.id", target = "usernicknameId")
    @Mapping(source = "oldStatusname.id", target = "oldStatusnameId")
    @Mapping(source = "newtatusname.id", target = "newtatusnameId")
    UserStatusHistoryDTO toDto(UserStatusHistory userStatusHistory);

    @Mapping(source = "usernicknameId", target = "usernickname")
    @Mapping(source = "oldStatusnameId", target = "oldStatusname")
    @Mapping(source = "newtatusnameId", target = "newtatusname")
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
