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

import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.service.dto.OwnerRelationCriteria;

import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.mapper.OwnerRelationMapper;

/**
 * Service for executing complex queries for OwnerRelation entities in the database.
 * The main input is a {@link OwnerRelationCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OwnerRelationDTO} or a {@link Page} of {@link OwnerRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OwnerRelationQueryService extends QueryService<OwnerRelation> {

    private final Logger log = LoggerFactory.getLogger(OwnerRelationQueryService.class);


    private final OwnerRelationRepository ownerRelationRepository;

    private final OwnerRelationMapper ownerRelationMapper;

    public OwnerRelationQueryService(OwnerRelationRepository ownerRelationRepository, OwnerRelationMapper ownerRelationMapper) {
        this.ownerRelationRepository = ownerRelationRepository;
        this.ownerRelationMapper = ownerRelationMapper;
    }

    /**
     * Return a {@link List} of {@link OwnerRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OwnerRelationDTO> findByCriteria(OwnerRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<OwnerRelation> specification = createSpecification(criteria);
        return ownerRelationMapper.toDto(ownerRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OwnerRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OwnerRelationDTO> findByCriteria(OwnerRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<OwnerRelation> specification = createSpecification(criteria);
        final Page<OwnerRelation> result = ownerRelationRepository.findAll(specification, page);
        return result.map(ownerRelationMapper::toDto);
    }

    /**
     * Function to convert OwnerRelationCriteria to a {@link Specifications}
     */
    private Specifications<OwnerRelation> createSpecification(OwnerRelationCriteria criteria) {
        Specifications<OwnerRelation> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OwnerRelation_.id));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), OwnerRelation_.roleName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OwnerRelation_.description));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), OwnerRelation_.createdTime));
            }
            if (criteria.getUpdatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedTime(), OwnerRelation_.updatedTime));
            }
            if (criteria.getUserAnnexId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserAnnexId(), OwnerRelation_.userAnnex, UserAnnex_.id));
            }
            if (criteria.getMercuryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMercuryId(), OwnerRelation_.mercury, Mercury_.id));
            }
        }
        return specification;
    }

}
