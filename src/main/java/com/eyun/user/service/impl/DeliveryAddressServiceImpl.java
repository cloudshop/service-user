package com.eyun.user.service.impl;

import com.eyun.user.service.DeliveryAddressService;
import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DeliveryAddress.
 */
@Service
@Transactional
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final Logger log = LoggerFactory.getLogger(DeliveryAddressServiceImpl.class);

    private final DeliveryAddressRepository deliveryAddressRepository;

    private final DeliveryAddressMapper deliveryAddressMapper;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository, DeliveryAddressMapper deliveryAddressMapper) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.deliveryAddressMapper = deliveryAddressMapper;
    }

    /**
     * Save a deliveryAddress.
     *
     * @param deliveryAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeliveryAddressDTO save(DeliveryAddressDTO deliveryAddressDTO) {
        log.debug("Request to save DeliveryAddress : {}", deliveryAddressDTO);
        DeliveryAddress deliveryAddress = deliveryAddressMapper.toEntity(deliveryAddressDTO);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
        return deliveryAddressMapper.toDto(deliveryAddress);
    }

    /**
     * Get all the deliveryAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryAddresses");
        return deliveryAddressRepository.findAll(pageable)
            .map(deliveryAddressMapper::toDto);
    }

    /**
     * Get one deliveryAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryAddressDTO findOne(Long id) {
        log.debug("Request to get DeliveryAddress : {}", id);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findOne(id);
        return deliveryAddressMapper.toDto(deliveryAddress);
    }

    /**
     * Delete the deliveryAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryAddress : {}", id);
        deliveryAddressRepository.delete(id);
    }
}