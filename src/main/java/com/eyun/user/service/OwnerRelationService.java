package com.eyun.user.service;

import com.eyun.user.service.dto.OwnerRelationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OwnerRelation.
 */
public interface OwnerRelationService {

    /**
     * Save a ownerRelation.
     *
     * @param ownerRelationDTO the entity to save
     * @return the persisted entity
     */
    OwnerRelationDTO save(OwnerRelationDTO ownerRelationDTO);

    /**
     * Get all the ownerRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OwnerRelationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ownerRelation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OwnerRelationDTO findOne(Long id);

    /**
     * Delete the "id" ownerRelation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
