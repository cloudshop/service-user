package com.eyun.user.service;

import com.eyun.user.domain.Authentication;
import com.eyun.user.service.dto.AuthenticationDTO;
import com.eyun.user.service.dto.SubTimeAuth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Authentication.
 */
public interface AuthenticationService {

    /**
     * Save a authentication.
     *
     * @param authenticationDTO the entity to save
     * @return the persisted entity
     */
    AuthenticationDTO save(AuthenticationDTO authenticationDTO);

    /**
     * Get all the authentications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AuthenticationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" authentication.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AuthenticationDTO findOne(Long id);

    /**
     * Delete the "id" authentication.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	List<Authentication> findSubAutherntication(SubTimeAuth subTimeAuth);
}
