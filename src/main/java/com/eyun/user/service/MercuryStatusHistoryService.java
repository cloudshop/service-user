package com.eyun.user.service;

import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MercuryStatusHistory.
 */
public interface MercuryStatusHistoryService {

    /**
     * Save a mercuryStatusHistory.
     *
     * @param mercuryStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    MercuryStatusHistoryDTO save(MercuryStatusHistoryDTO mercuryStatusHistoryDTO);

    /**
     * Get all the mercuryStatusHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MercuryStatusHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mercuryStatusHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MercuryStatusHistoryDTO findOne(Long id);

    /**
     * Delete the "id" mercuryStatusHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
