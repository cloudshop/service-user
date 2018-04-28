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

import com.eyun.user.domain.Mercury;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.dto.MercuryCriteria;

import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.mapper.MercuryMapper;

/**
 * Service for executing complex queries for Mercury entities in the database.
 * The main input is a {@link MercuryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MercuryDTO} or a {@link Page} of {@link MercuryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MercuryQueryService extends QueryService<Mercury> {

    private final Logger log = LoggerFactory.getLogger(MercuryQueryService.class);


    private final MercuryRepository mercuryRepository;

    private final MercuryMapper mercuryMapper;

    public MercuryQueryService(MercuryRepository mercuryRepository, MercuryMapper mercuryMapper) {
        this.mercuryRepository = mercuryRepository;
        this.mercuryMapper = mercuryMapper;
    }

    /**
     * Return a {@link List} of {@link MercuryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MercuryDTO> findByCriteria(MercuryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Mercury> specification = createSpecification(criteria);
        return mercuryMapper.toDto(mercuryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MercuryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MercuryDTO> findByCriteria(MercuryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Mercury> specification = createSpecification(criteria);
        final Page<Mercury> result = mercuryRepository.findAll(specification, page);
        return result.map(mercuryMapper::toDto);
    }

    /**
     * Function to convert MercuryCriteria to a {@link Specifications}
     */
    private Specifications<Mercury> createSpecification(MercuryCriteria criteria) {
        Specifications<Mercury> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Mercury_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Mercury_.name));
            }
            if (criteria.getImgLicense() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLicense(), Mercury_.imgLicense));
            }
            if (criteria.getImgIdcardFront() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIdcardFront(), Mercury_.imgIdcardFront));
            }
            if (criteria.getImgIdcardBack() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIdcardBack(), Mercury_.imgIdcardBack));
            }
            if (criteria.getImgIdcardHold() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIdcardHold(), Mercury_.imgIdcardHold));
            }
            if (criteria.getLangitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLangitude(), Mercury_.langitude));
            }
            if (criteria.getLantitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLantitude(), Mercury_.lantitude));
            }
            if (criteria.getProvice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvice(), Mercury_.provice));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Mercury_.city));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), Mercury_.street));
            }
            if (criteria.getImgFacade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgFacade(), Mercury_.imgFacade));
            }
            if (criteria.getImgIntroduces() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIntroduces(), Mercury_.imgIntroduces));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Mercury_.status));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), Mercury_.createdTime));
            }
            if (criteria.getUpdatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedTime(), Mercury_.updatedTime));
            }
            if (criteria.getMercuryStatusHistoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMercuryStatusHistoryId(), Mercury_.mercuryStatusHistories, MercuryStatusHistory_.id));
            }
            if (criteria.getOwnerRelationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerRelationId(), Mercury_.ownerRelation, OwnerRelation_.id));
            }
        }
        return specification;
    }

}
