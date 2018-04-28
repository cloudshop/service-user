package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserStatusHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserStatusHistory and its DTO UserStatusHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAnnexMapper.class})
public interface UserStatusHistoryMapper extends EntityMapper<UserStatusHistoryDTO, UserStatusHistory> {

    @Mapping(source = "userAnnex.id", target = "userAnnexId")
    UserStatusHistoryDTO toDto(UserStatusHistory userStatusHistory);

    @Mapping(source = "userAnnexId", target = "userAnnex")
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
