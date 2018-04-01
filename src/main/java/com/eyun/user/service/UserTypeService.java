package com.eyun.user.service;

import com.eyun.user.domain.UserType;
import com.eyun.user.repository.UserTypeRepository;
import com.eyun.user.service.dto.UserTypeDTO;
import com.eyun.user.service.mapper.UserTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserType.
 */
@Service
@Transactional
public class UserTypeService {

    private final Logger log = LoggerFactory.getLogger(UserTypeService.class);

    private final UserTypeRepository userTypeRepository;

    private final UserTypeMapper userTypeMapper;

    public UserTypeService(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    /**
     * Save a userType.
     *
     * @param userTypeDTO the entity to save
     * @return the persisted entity
     */
    public UserTypeDTO save(UserTypeDTO userTypeDTO) {
        log.debug("Request to save UserType : {}", userTypeDTO);
        UserType userType = userTypeMapper.toEntity(userTypeDTO);
        userType = userTypeRepository.save(userType);
        return userTypeMapper.toDto(userType);
    }

    /**
     * Get all the userTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTypes");
        return userTypeRepository.findAll(pageable)
            .map(userTypeMapper::toDto);
    }

    /**
     * Get one userType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserTypeDTO findOne(Long id) {
        log.debug("Request to get UserType : {}", id);
        UserType userType = userTypeRepository.findOne(id);
        return userTypeMapper.toDto(userType);
    }

    /**
     * Delete the userType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserType : {}", id);
        userTypeRepository.delete(id);
    }
}
