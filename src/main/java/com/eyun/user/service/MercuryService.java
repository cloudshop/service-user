package com.eyun.user.service;

import com.eyun.user.service.dto.MercuryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Mercury.
 */
public interface MercuryService {

    /**
     * Save a mercury.
     *
     * @param mercuryDTO the entity to save
     * @return the persisted entity
     */
    MercuryDTO save(MercuryDTO mercuryDTO);

    /**
     * Get all the mercuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MercuryDTO> findAll(Pageable pageable);
    /**
     * Get all the MercuryDTO where OwnerRelation is null.
     *
     * @return the list of entities
     */
    List<MercuryDTO> findAllWhereOwnerRelationIsNull();

    /**
     * Get the "id" mercury.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MercuryDTO findOne(Long id);

    /**
     * Delete the "id" mercury.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * 附近商户查询
     * @param mercuryDTO
     * @return
     */
    List<MercuryDTO> findNearMerchants(MercuryDTO mercuryDTO);
}
