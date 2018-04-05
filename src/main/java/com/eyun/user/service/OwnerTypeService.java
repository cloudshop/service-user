package com.eyun.user.service;

import com.eyun.user.domain.OwnerType;
import com.eyun.user.repository.OwnerTypeRepository;
import com.eyun.user.service.dto.OwnerTypeDTO;
import com.eyun.user.service.mapper.OwnerTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OwnerType.
 */
@Service
@Transactional
public class OwnerTypeService {

    private final Logger log = LoggerFactory.getLogger(OwnerTypeService.class);

    private final OwnerTypeRepository ownerTypeRepository;

    private final OwnerTypeMapper ownerTypeMapper;

    public OwnerTypeService(OwnerTypeRepository ownerTypeRepository, OwnerTypeMapper ownerTypeMapper) {
        this.ownerTypeRepository = ownerTypeRepository;
        this.ownerTypeMapper = ownerTypeMapper;
    }

    /**
     * Save a ownerType.
     *
     * @param ownerTypeDTO the entity to save
     * @return the persisted entity
     */
    public OwnerTypeDTO save(OwnerTypeDTO ownerTypeDTO) {
        log.debug("Request to save OwnerType : {}", ownerTypeDTO);
        OwnerType ownerType = ownerTypeMapper.toEntity(ownerTypeDTO);
        ownerType = ownerTypeRepository.save(ownerType);
        return ownerTypeMapper.toDto(ownerType);
    }

    /**
     * Get all the ownerTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OwnerTypeDTO> findAll() {
        log.debug("Request to get all OwnerTypes");
        return ownerTypeRepository.findAll().stream()
            .map(ownerTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ownerType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OwnerTypeDTO findOne(Long id) {
        log.debug("Request to get OwnerType : {}", id);
        OwnerType ownerType = ownerTypeRepository.findOne(id);
        return ownerTypeMapper.toDto(ownerType);
    }

    /**
     * Delete the ownerType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OwnerType : {}", id);
        ownerTypeRepository.delete(id);
    }
}
