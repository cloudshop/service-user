package com.eyun.user.service;

import com.eyun.user.service.dto.UserStatusHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserStatusHistory.
 */
public interface UserStatusHistoryService {

    /**
     * Save a userStatusHistory.
     *
     * @param userStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    UserStatusHistoryDTO save(UserStatusHistoryDTO userStatusHistoryDTO);

    /**
     * Get all the userStatusHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserStatusHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userStatusHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserStatusHistoryDTO findOne(Long id);

    /**
     * Delete the "id" userStatusHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
