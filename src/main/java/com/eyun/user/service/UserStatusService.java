package com.eyun.user.service;

import com.eyun.user.domain.UserStatus;
import com.eyun.user.repository.UserStatusRepository;
import com.eyun.user.service.dto.UserStatusDTO;
import com.eyun.user.service.mapper.UserStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserStatus.
 */
@Service
@Transactional
public class UserStatusService {

    private final Logger log = LoggerFactory.getLogger(UserStatusService.class);

    private final UserStatusRepository userStatusRepository;

    private final UserStatusMapper userStatusMapper;

    public UserStatusService(UserStatusRepository userStatusRepository, UserStatusMapper userStatusMapper) {
        this.userStatusRepository = userStatusRepository;
        this.userStatusMapper = userStatusMapper;
    }

    /**
     * Save a userStatus.
     *
     * @param userStatusDTO the entity to save
     * @return the persisted entity
     */
    public UserStatusDTO save(UserStatusDTO userStatusDTO) {
        log.debug("Request to save UserStatus : {}", userStatusDTO);
        UserStatus userStatus = userStatusMapper.toEntity(userStatusDTO);
        userStatus = userStatusRepository.save(userStatus);
        return userStatusMapper.toDto(userStatus);
    }

    /**
     * Get all the userStatuses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserStatusDTO> findAll() {
        log.debug("Request to get all UserStatuses");
        return userStatusRepository.findAll().stream()
            .map(userStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserStatusDTO findOne(Long id) {
        log.debug("Request to get UserStatus : {}", id);
        UserStatus userStatus = userStatusRepository.findOne(id);
        return userStatusMapper.toDto(userStatus);
    }

    /**
     * Delete the userStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserStatus : {}", id);
        userStatusRepository.delete(id);
    }
}
