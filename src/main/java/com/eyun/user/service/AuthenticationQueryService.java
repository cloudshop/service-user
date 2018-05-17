package com.eyun.user.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.eyun.user.domain.Authentication;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.AuthenticationRepository;
import com.eyun.user.service.dto.AuthenticationCriteria;

import com.eyun.user.service.dto.AuthenticationDTO;
import com.eyun.user.service.mapper.AuthenticationMapper;

/**
 * Service for executing complex queries for Authentication entities in the database.
 * The main input is a {@link AuthenticationCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuthenticationDTO} or a {@link Page} of {@link AuthenticationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthenticationQueryService extends QueryService<Authentication> {

    private final Logger log = LoggerFactory.getLogger(AuthenticationQueryService.class);


    private final AuthenticationRepository authenticationRepository;

    private final AuthenticationMapper authenticationMapper;

    public AuthenticationQueryService(AuthenticationRepository authenticationRepository, AuthenticationMapper authenticationMapper) {
        this.authenticationRepository = authenticationRepository;
        this.authenticationMapper = authenticationMapper;
    }

    /**
     * Return a {@link List} of {@link AuthenticationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthenticationDTO> findByCriteria(AuthenticationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Authentication> specification = createSpecification(criteria);
        return authenticationMapper.toDto(authenticationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuthenticationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthenticationDTO> findByCriteria(AuthenticationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Authentication> specification = createSpecification(criteria);
        final Page<Authentication> result = authenticationRepository.findAll(specification, page);
        return result.map(authenticationMapper::toDto);
    }

    /**
     * Function to convert AuthenticationCriteria to a {@link Specifications}
     */
    private Specifications<Authentication> createSpecification(AuthenticationCriteria criteria) {
        Specifications<Authentication> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Authentication_.id));
            }
            if (criteria.getRealName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRealName(), Authentication_.realName));
            }
            if (criteria.getIdnuber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnuber(), Authentication_.idnuber));
            }
            if (criteria.getFrontImg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFrontImg(), Authentication_.frontImg));
            }
            if (criteria.getReverseImg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReverseImg(), Authentication_.reverseImg));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Authentication_.status));
            }
            if (criteria.getStatusString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusString(), Authentication_.statusString));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), Authentication_.createdTime));
            }
            if (criteria.getUpdatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedTime(), Authentication_.updatedTime));
            }
        }
        return specification;
    }

}
