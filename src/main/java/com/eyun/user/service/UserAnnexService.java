package com.eyun.user.service;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.service.dto.UserAnnexDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing UserAnnex.
 */
public interface UserAnnexService {

    /**
     * Save a userAnnex.
     *
     * @param userAnnexDTO the entity to save
     * @return the persisted entity
     */
    UserAnnexDTO save(UserAnnexDTO userAnnexDTO);

    /**
     * Get all the userAnnexes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserAnnexDTO> findAll(Pageable pageable);
    /**
     * Get all the UserAnnexDTO where OwnerRelation is null.
     *
     * @return the list of entities
     */
    List<UserAnnexDTO> findAllWhereOwnerRelationIsNull();



    /**
     * Get the "id" userAnnex.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserAnnexDTO findOne(Long id);

    /**
     * Delete the "id" userAnnex.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    void updataUserInfo(UserAnnexDTO userAnnexDTO);

    UserAnnex userInfo(Long id);
}
