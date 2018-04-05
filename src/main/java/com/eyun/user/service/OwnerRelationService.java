package com.eyun.user.service;

import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.mapper.OwnerRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OwnerRelation.
 */
@Service
@Transactional
public class OwnerRelationService {

    private final Logger log = LoggerFactory.getLogger(OwnerRelationService.class);

    private final OwnerRelationRepository ownerRelationRepository;

    private final OwnerRelationMapper ownerRelationMapper;

    public OwnerRelationService(OwnerRelationRepository ownerRelationRepository, OwnerRelationMapper ownerRelationMapper) {
        this.ownerRelationRepository = ownerRelationRepository;
        this.ownerRelationMapper = ownerRelationMapper;
    }

    /**
     * Save a ownerRelation.
     *
     * @param ownerRelationDTO the entity to save
     * @return the persisted entity
     */
    public OwnerRelationDTO save(OwnerRelationDTO ownerRelationDTO) {
        log.debug("Request to save OwnerRelation : {}", ownerRelationDTO);
        OwnerRelation ownerRelation = ownerRelationMapper.toEntity(ownerRelationDTO);
        ownerRelation = ownerRelationRepository.save(ownerRelation);
        return ownerRelationMapper.toDto(ownerRelation);
    }

    /**
     * Get all the ownerRelations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OwnerRelationDTO> findAll() {
        log.debug("Request to get all OwnerRelations");
        return ownerRelationRepository.findAll().stream()
            .map(ownerRelationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ownerRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
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
    public void delete(Long id) {
        log.debug("Request to delete OwnerRelation : {}", id);
        ownerRelationRepository.delete(id);
    }
}
