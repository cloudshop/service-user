package com.eyun.user.service.impl;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.service.DeliveryAddressService;
import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * 删除单个地址
     * @param
     */
    @Override
    public void deleteAddress(DeliveryAddressDTO deliveryAddressDTO) {

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        BeanUtils.copyProperties(deliveryAddressDTO,deliveryAddress);
        deliveryAddressRepository.delete(deliveryAddress);
    }

    /**
     * 创建地址
     * @param deliveryAddressDTO
     */
    @Override
    @Transactional
    public void createAddress(DeliveryAddressDTO deliveryAddressDTO) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        BeanUtils.copyProperties(deliveryAddressDTO,deliveryAddress);
        deliveryAddressRepository.save(deliveryAddress);



    }


   @Override
   @Transactional
    public void updateAddress(DeliveryAddressDTO deliveryAddressDTO) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        BeanUtils.copyProperties(deliveryAddressDTO, deliveryAddress);
        deliveryAddressRepository.saveAndFlush(deliveryAddress);
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
     *
     * @param id
     * @return
     */
    @Override
    public List<Map> findByIdList(Long id) {
        Map result=new HashMap();
        List<Map> deliveryAddressList = deliveryAddressRepository.findDeliveryAddressList(id);
            return deliveryAddressList;






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
