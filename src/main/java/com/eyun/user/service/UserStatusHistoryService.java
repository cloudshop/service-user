package com.eyun.user.service;

import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.repository.UserStatusHistoryRepository;
import com.eyun.user.service.dto.UserStatusHistoryDTO;
import com.eyun.user.service.mapper.UserStatusHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserStatusHistory.
 */
@Service
@Transactional
public class UserStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(UserStatusHistoryService.class);

    private final UserStatusHistoryRepository userStatusHistoryRepository;

    private final UserStatusHistoryMapper userStatusHistoryMapper;

    public UserStatusHistoryService(UserStatusHistoryRepository userStatusHistoryRepository, UserStatusHistoryMapper userStatusHistoryMapper) {
        this.userStatusHistoryRepository = userStatusHistoryRepository;
        this.userStatusHistoryMapper = userStatusHistoryMapper;
    }

    /**
     * Save a userStatusHistory.
     *
     * @param userStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    public UserStatusHistoryDTO save(UserStatusHistoryDTO userStatusHistoryDTO) {
        log.debug("Request to save UserStatusHistory : {}", userStatusHistoryDTO);
        UserStatusHistory userStatusHistory = userStatusHistoryMapper.toEntity(userStatusHistoryDTO);
        userStatusHistory = userStatusHistoryRepository.save(userStatusHistory);
        return userStatusHistoryMapper.toDto(userStatusHistory);
    }

    /**
     * Get all the userStatusHistories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserStatusHistoryDTO> findAll() {
        log.debug("Request to get all UserStatusHistories");
        return userStatusHistoryRepository.findAll().stream()
            .map(userStatusHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userStatusHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserStatusHistoryDTO findOne(Long id) {
        log.debug("Request to get UserStatusHistory : {}", id);
        UserStatusHistory userStatusHistory = userStatusHistoryRepository.findOne(id);
        return userStatusHistoryMapper.toDto(userStatusHistory);
    }

    /**
     * Delete the userStatusHistory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserStatusHistory : {}", id);
        userStatusHistoryRepository.delete(id);
    }
}
