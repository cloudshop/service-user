package com.eyun.user.service.impl;

import com.eyun.user.service.UserStatusHistoryService;
import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.repository.UserStatusHistoryRepository;
import com.eyun.user.service.dto.UserStatusHistoryDTO;
import com.eyun.user.service.mapper.UserStatusHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserStatusHistory.
 */
@Service
@Transactional
public class UserStatusHistoryServiceImpl implements UserStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(UserStatusHistoryServiceImpl.class);

    private final UserStatusHistoryRepository userStatusHistoryRepository;

    private final UserStatusHistoryMapper userStatusHistoryMapper;

    public UserStatusHistoryServiceImpl(UserStatusHistoryRepository userStatusHistoryRepository, UserStatusHistoryMapper userStatusHistoryMapper) {
        this.userStatusHistoryRepository = userStatusHistoryRepository;
        this.userStatusHistoryMapper = userStatusHistoryMapper;
    }

    /**
     * Save a userStatusHistory.
     *
     * @param userStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserStatusHistoryDTO save(UserStatusHistoryDTO userStatusHistoryDTO) {
        log.debug("Request to save UserStatusHistory : {}", userStatusHistoryDTO);
        UserStatusHistory userStatusHistory = userStatusHistoryMapper.toEntity(userStatusHistoryDTO);
        userStatusHistory = userStatusHistoryRepository.save(userStatusHistory);
        return userStatusHistoryMapper.toDto(userStatusHistory);
    }

    /**
     * Get all the userStatusHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserStatusHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserStatusHistories");
        return userStatusHistoryRepository.findAll(pageable)
            .map(userStatusHistoryMapper::toDto);
    }

    /**
     * Get one userStatusHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserStatusHistory : {}", id);
        userStatusHistoryRepository.delete(id);
    }
}
