package com.eyun.user.service;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DeliveryAddress.
 */
@Service
@Transactional
public class DeliveryAddressService {

    private final Logger log = LoggerFactory.getLogger(DeliveryAddressService.class);

    private final DeliveryAddressRepository deliveryAddressRepository;

    private final DeliveryAddressMapper deliveryAddressMapper;

    public DeliveryAddressService(DeliveryAddressRepository deliveryAddressRepository, DeliveryAddressMapper deliveryAddressMapper) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.deliveryAddressMapper = deliveryAddressMapper;
    }

    /**
     * Save a deliveryAddress.
     *
     * @param deliveryAddressDTO the entity to save
     * @return the persisted entity
     */
    public DeliveryAddressDTO save(DeliveryAddressDTO deliveryAddressDTO) {
        log.debug("Request to save DeliveryAddress : {}", deliveryAddressDTO);
        DeliveryAddress deliveryAddress = deliveryAddressMapper.toEntity(deliveryAddressDTO);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
        return deliveryAddressMapper.toDto(deliveryAddress);
    }

    /**
     * Get all the deliveryAddresses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeliveryAddressDTO> findAll() {
        log.debug("Request to get all DeliveryAddresses");
        return deliveryAddressRepository.findAll().stream()
            .map(deliveryAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one deliveryAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
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
    public void delete(Long id) {
        log.debug("Request to delete DeliveryAddress : {}", id);
        deliveryAddressRepository.delete(id);
    }
}
