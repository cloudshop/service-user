package com.eyun.user.service;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing DeliveryAddress.
 */
public interface DeliveryAddressService {

    /**
     * Save a deliveryAddress.
     *
     * @param deliveryAddressDTO the entity to save
     * @return the persisted entity
     */
    DeliveryAddressDTO save(DeliveryAddressDTO deliveryAddressDTO);

    /**
     * Get all the deliveryAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeliveryAddressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deliveryAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DeliveryAddressDTO findOne(Long id);

    /**
     * Delete the "id" deliveryAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     *
     * @param id
     * @return
     */
    List<Map> findByIdList(Long id);


    /**
     * 修改地址
     * @param deliveryAddressDTO
     */
    void updateAddress(DeliveryAddressDTO deliveryAddressDTO);


    void createAddress(DeliveryAddressDTO deliveryAddressDTO);



}
