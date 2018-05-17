package com.eyun.user.service.mapper;

import com.eyun.user.domain.*;
import com.eyun.user.service.dto.AuthenticationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Authentication and its DTO AuthenticationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthenticationMapper extends EntityMapper<AuthenticationDTO, Authentication> {



    default Authentication fromId(Long id) {
        if (id == null) {
            return null;
        }
        Authentication authentication = new Authentication();
        authentication.setId(id);
        return authentication;
    }
}
