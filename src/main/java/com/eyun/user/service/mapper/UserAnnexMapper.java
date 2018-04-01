package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserAnnexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAnnex and its DTO UserAnnexDTO.
 */
@Mapper(componentModel = "spring", uses = {UserTypeMapper.class})
public interface UserAnnexMapper extends EntityMapper<UserAnnexDTO, UserAnnex> {

    @Mapping(source = "inviter.id", target = "inviterId")
    UserAnnexDTO toDto(UserAnnex userAnnex);

    @Mapping(target = "deliveries", ignore = true)
    @Mapping(source = "inviterId", target = "inviter")
    @Mapping(target = "invitees", ignore = true)
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
