package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.UserTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserType and its DTO UserTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserTypeMapper extends EntityMapper<UserTypeDTO, UserType> {



    default UserType fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserType userType = new UserType();
        userType.setId(id);
        return userType;
    }
}
