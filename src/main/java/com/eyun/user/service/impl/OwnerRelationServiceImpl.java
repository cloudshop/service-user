package com.eyun.user.service.impl;

import com.eyun.user.service.OwnerRelationService;
import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.mapper.OwnerRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OwnerRelation.
 */
@Service
@Transactional
public class OwnerRelationServiceImpl implements OwnerRelationService {

    private final Logger log = LoggerFactory.getLogger(OwnerRelationServiceImpl.class);

    private final OwnerRelationRepository ownerRelationRepository;

    private final OwnerRelationMapper ownerRelationMapper;

    public OwnerRelationServiceImpl(OwnerRelationRepository ownerRelationRepository, OwnerRelationMapper ownerRelationMapper) {
        this.ownerRelationRepository = ownerRelationRepository;
        this.ownerRelationMapper = ownerRelationMapper;
    }

    /**
     * Save a ownerRelation.
     *
     * @param ownerRelationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OwnerRelationDTO save(OwnerRelationDTO ownerRelationDTO) {
        log.debug("Request to save OwnerRelation : {}", ownerRelationDTO);
        OwnerRelation ownerRelation = ownerRelationMapper.toEntity(ownerRelationDTO);
        ownerRelation = ownerRelationRepository.save(ownerRelation);
        return ownerRelationMapper.toDto(ownerRelation);
    }

    /**
     * Get all the ownerRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OwnerRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OwnerRelations");
        return ownerRelationRepository.findAll(pageable)
            .map(ownerRelationMapper::toDto);
    }

    /**
     * Get one ownerRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OwnerRelationDTO findOne(Long id) {
        log.debug("Request to get OwnerRelation : {}", id);
        OwnerRelation ownerRelation = ownerRelationRepository.findOne(id);
        return ownerRelationMapper.toDto(ownerRelation);
    }

    /**
     * Delete the ownerRelation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OwnerRelation : {}", id);
        ownerRelationRepository.delete(id);
    }
}
