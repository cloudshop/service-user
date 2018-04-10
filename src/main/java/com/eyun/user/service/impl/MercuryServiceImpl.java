package com.eyun.user.service.impl;

import com.eyun.user.service.MercuryService;
import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.mapper.MercuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Mercury.
 */
@Service
@Transactional
public class MercuryServiceImpl implements MercuryService {

    private final Logger log = LoggerFactory.getLogger(MercuryServiceImpl.class);

    private final MercuryRepository mercuryRepository;

    private final MercuryMapper mercuryMapper;

    public MercuryServiceImpl(MercuryRepository mercuryRepository, MercuryMapper mercuryMapper) {
        this.mercuryRepository = mercuryRepository;
        this.mercuryMapper = mercuryMapper;
    }

    /**
     * Save a mercury.
     *
     * @param mercuryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MercuryDTO save(MercuryDTO mercuryDTO) {
        log.debug("Request to save Mercury : {}", mercuryDTO);
        Mercury mercury = mercuryMapper.toEntity(mercuryDTO);
        mercury = mercuryRepository.save(mercury);
        return mercuryMapper.toDto(mercury);
    }

    /**
     * Get all the mercuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MercuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mercuries");
        return mercuryRepository.findAll(pageable)
            .map(mercuryMapper::toDto);
    }


    /**
     *  get all the mercuries where OwnerRelation is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MercuryDTO> findAllWhereOwnerRelationIsNull() {
        log.debug("Request to get all mercuries where OwnerRelation is null");
        return StreamSupport
            .stream(mercuryRepository.findAll().spliterator(), false)
            .filter(mercury -> mercury.getOwnerRelation() == null)
            .map(mercuryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mercury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MercuryDTO findOne(Long id) {
        log.debug("Request to get Mercury : {}", id);
        Mercury mercury = mercuryRepository.findOne(id);
        return mercuryMapper.toDto(mercury);
    }

    /**
     * Delete the mercury by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mercury : {}", id);
        mercuryRepository.delete(id);
    }
}
