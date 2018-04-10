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

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.domain.*; // for static metamodels
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.dto.DeliveryAddressCriteria;

import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;

/**
 * Service for executing complex queries for DeliveryAddress entities in the database.
 * The main input is a {@link DeliveryAddressCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryAddressDTO} or a {@link Page} of {@link DeliveryAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryAddressQueryService extends QueryService<DeliveryAddress> {

    private final Logger log = LoggerFactory.getLogger(DeliveryAddressQueryService.class);


    private final DeliveryAddressRepository deliveryAddressRepository;

    private final DeliveryAddressMapper deliveryAddressMapper;

    public DeliveryAddressQueryService(DeliveryAddressRepository deliveryAddressRepository, DeliveryAddressMapper deliveryAddressMapper) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.deliveryAddressMapper = deliveryAddressMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryAddressDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryAddressDTO> findByCriteria(DeliveryAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<DeliveryAddress> specification = createSpecification(criteria);
        return deliveryAddressMapper.toDto(deliveryAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryAddressDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryAddressDTO> findByCriteria(DeliveryAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<DeliveryAddress> specification = createSpecification(criteria);
        final Page<DeliveryAddress> result = deliveryAddressRepository.findAll(specification, page);
        return result.map(deliveryAddressMapper::toDto);
    }

    /**
     * Function to convert DeliveryAddressCriteria to a {@link Specifications}
     */
    private Specifications<DeliveryAddress> createSpecification(DeliveryAddressCriteria criteria) {
        Specifications<DeliveryAddress> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DeliveryAddress_.id));
            }
            if (criteria.getAliases() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAliases(), DeliveryAddress_.aliases));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), DeliveryAddress_.contact));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), DeliveryAddress_.phone));
            }
            if (criteria.getStateProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateProvince(), DeliveryAddress_.stateProvince));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), DeliveryAddress_.city));
            }
            if (criteria.getStreetAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetAddress(), DeliveryAddress_.streetAddress));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), DeliveryAddress_.postalCode));
            }
            if (criteria.getDefaultAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultAddress(), DeliveryAddress_.defaultAddress));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), DeliveryAddress_.createdTime));
            }
            if (criteria.getUpdatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedTime(), DeliveryAddress_.updatedTime));
            }
            if (criteria.getUserAnnexId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserAnnexId(), DeliveryAddress_.userAnnex, UserAnnex_.id));
            }
        }
        return specification;
    }

}
