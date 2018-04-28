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

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.dto.UserAnnexCriteria;

import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;

/**
 * Service for executing complex queries for UserAnnex entities in the database.
 * The main input is a {@link UserAnnexCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAnnexDTO} or a {@link Page} of {@link UserAnnexDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAnnexQueryService extends QueryService<UserAnnex> {

    private final Logger log = LoggerFactory.getLogger(UserAnnexQueryService.class);


    private final UserAnnexRepository userAnnexRepository;

    private final UserAnnexMapper userAnnexMapper;

    public UserAnnexQueryService(UserAnnexRepository userAnnexRepository, UserAnnexMapper userAnnexMapper) {
        this.userAnnexRepository = userAnnexRepository;
        this.userAnnexMapper = userAnnexMapper;
    }

    /**
     * Return a {@link List} of {@link UserAnnexDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAnnexDTO> findByCriteria(UserAnnexCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserAnnex> specification = createSpecification(criteria);
        return userAnnexMapper.toDto(userAnnexRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserAnnexDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAnnexDTO> findByCriteria(UserAnnexCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserAnnex> specification = createSpecification(criteria);
        final Page<UserAnnex> result = userAnnexRepository.findAll(specification, page);
        return result.map(userAnnexMapper::toDto);
    }

    /**
     * Function to convert UserAnnexCriteria to a {@link Specifications}
     */
    private Specifications<UserAnnex> createSpecification(UserAnnexCriteria criteria) {
        Specifications<UserAnnex> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserAnnex_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UserAnnex_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), UserAnnex_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), UserAnnex_.phone));
            }
            if (criteria.getNickname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNickname(), UserAnnex_.nickname));
            }
            if (criteria.getAvatar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAvatar(), UserAnnex_.avatar));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), UserAnnex_.status));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), UserAnnex_.createdTime));
            }
            if (criteria.getUpdatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedTime(), UserAnnex_.updatedTime));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), UserAnnex_.type));
            }
            if (criteria.getInviterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInviterId(), UserAnnex_.inviterId));
            }
            if (criteria.getOwnerRelationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerRelationId(), UserAnnex_.ownerRelation, OwnerRelation_.id));
            }
            if (criteria.getUserStatusHistoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserStatusHistoryId(), UserAnnex_.userStatusHistories, UserStatusHistory_.id));
            }
        }
        return specification;
    }

}
