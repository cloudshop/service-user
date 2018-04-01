package com.eyun.user.service;

import com.eyun.user.domain.Delivery;
import com.eyun.user.repository.DeliveryRepository;
import com.eyun.user.service.dto.DeliveryDTO;
import com.eyun.user.service.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Delivery.
 */
@Service
@Transactional
public class DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save
     * @return the persisted entity
     */
    public DeliveryDTO save(DeliveryDTO deliveryDTO) {
        log.debug("Request to save Delivery : {}", deliveryDTO);
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        delivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(delivery);
    }

    /**
     * Get all the deliveries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DeliveryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll(pageable)
            .map(deliveryMapper::toDto);
    }

    /**
     * Get one delivery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DeliveryDTO findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        Delivery delivery = deliveryRepository.findOne(id);
        return deliveryMapper.toDto(delivery);
    }

    /**
     * Delete the delivery by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.delete(id);
    }
}
