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

import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.MercuryStatusHistoryRepository;
import com.eyun.user.service.dto.MercuryStatusHistoryCriteria;

import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import com.eyun.user.service.mapper.MercuryStatusHistoryMapper;

/**
 * Service for executing complex queries for MercuryStatusHistory entities in the database.
 * The main input is a {@link MercuryStatusHistoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MercuryStatusHistoryDTO} or a {@link Page} of {@link MercuryStatusHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MercuryStatusHistoryQueryService extends QueryService<MercuryStatusHistory> {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusHistoryQueryService.class);


    private final MercuryStatusHistoryRepository mercuryStatusHistoryRepository;

    private final MercuryStatusHistoryMapper mercuryStatusHistoryMapper;

    public MercuryStatusHistoryQueryService(MercuryStatusHistoryRepository mercuryStatusHistoryRepository, MercuryStatusHistoryMapper mercuryStatusHistoryMapper) {
        this.mercuryStatusHistoryRepository = mercuryStatusHistoryRepository;
        this.mercuryStatusHistoryMapper = mercuryStatusHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link MercuryStatusHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MercuryStatusHistoryDTO> findByCriteria(MercuryStatusHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MercuryStatusHistory> specification = createSpecification(criteria);
        return mercuryStatusHistoryMapper.toDto(mercuryStatusHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MercuryStatusHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MercuryStatusHistoryDTO> findByCriteria(MercuryStatusHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MercuryStatusHistory> specification = createSpecification(criteria);
        final Page<MercuryStatusHistory> result = mercuryStatusHistoryRepository.findAll(specification, page);
        return result.map(mercuryStatusHistoryMapper::toDto);
    }

    /**
     * Function to convert MercuryStatusHistoryCriteria to a {@link Specifications}
     */
    private Specifications<MercuryStatusHistory> createSpecification(MercuryStatusHistoryCriteria criteria) {
        Specifications<MercuryStatusHistory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MercuryStatusHistory_.id));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedBy(), MercuryStatusHistory_.modifiedBy));
            }
            if (criteria.getModifiedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedTime(), MercuryStatusHistory_.modifiedTime));
            }
            if (criteria.getWithStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWithStatus(), MercuryStatusHistory_.withStatus));
            }
            if (criteria.getToStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToStatus(), MercuryStatusHistory_.toStatus));
            }
            if (criteria.getMercuryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMercuryId(), MercuryStatusHistory_.mercury, Mercury_.id));
            }
        }
        return specification;
    }

}
