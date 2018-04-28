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

import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.UserStatusHistoryRepository;
import com.eyun.user.service.dto.UserStatusHistoryCriteria;

import com.eyun.user.service.dto.UserStatusHistoryDTO;
import com.eyun.user.service.mapper.UserStatusHistoryMapper;

/**
 * Service for executing complex queries for UserStatusHistory entities in the database.
 * The main input is a {@link UserStatusHistoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserStatusHistoryDTO} or a {@link Page} of {@link UserStatusHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserStatusHistoryQueryService extends QueryService<UserStatusHistory> {

    private final Logger log = LoggerFactory.getLogger(UserStatusHistoryQueryService.class);


    private final UserStatusHistoryRepository userStatusHistoryRepository;

    private final UserStatusHistoryMapper userStatusHistoryMapper;

    public UserStatusHistoryQueryService(UserStatusHistoryRepository userStatusHistoryRepository, UserStatusHistoryMapper userStatusHistoryMapper) {
        this.userStatusHistoryRepository = userStatusHistoryRepository;
        this.userStatusHistoryMapper = userStatusHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link UserStatusHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserStatusHistoryDTO> findByCriteria(UserStatusHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserStatusHistory> specification = createSpecification(criteria);
        return userStatusHistoryMapper.toDto(userStatusHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserStatusHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserStatusHistoryDTO> findByCriteria(UserStatusHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserStatusHistory> specification = createSpecification(criteria);
        final Page<UserStatusHistory> result = userStatusHistoryRepository.findAll(specification, page);
        return result.map(userStatusHistoryMapper::toDto);
    }

    /**
     * Function to convert UserStatusHistoryCriteria to a {@link Specifications}
     */
    private Specifications<UserStatusHistory> createSpecification(UserStatusHistoryCriteria criteria) {
        Specifications<UserStatusHistory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserStatusHistory_.id));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedBy(), UserStatusHistory_.modifiedBy));
            }
            if (criteria.getModifiedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedTime(), UserStatusHistory_.modifiedTime));
            }
            if (criteria.getUserid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserid(), UserStatusHistory_.userid));
            }
            if (criteria.getWithStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWithStatus(), UserStatusHistory_.withStatus));
            }
            if (criteria.getToStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToStatus(), UserStatusHistory_.toStatus));
            }
            if (criteria.getUserAnnexId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserAnnexId(), UserStatusHistory_.userAnnex, UserAnnex_.id));
            }
        }
        return specification;
    }

}
