package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserAnnexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAnnex and its DTO UserAnnexDTO.
 */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class, UserTypeMapper.class})
public interface UserAnnexMapper extends EntityMapper<UserAnnexDTO, UserAnnex> {

    @Mapping(source = "statusname.id", target = "statusnameId")
    @Mapping(source = "inviteenickname.id", target = "inviteenicknameId")
    UserAnnexDTO toDto(UserAnnex userAnnex);

    @Mapping(source = "statusnameId", target = "statusname")
    @Mapping(target = "deliveryAddressaliases", ignore = true)
    @Mapping(target = "inviternicknames", ignore = true)
    @Mapping(source = "inviteenicknameId", target = "inviteenickname")
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
