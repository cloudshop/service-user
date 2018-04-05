package com.eyun.user.service;

import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.mapper.MercuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mercury.
 */
@Service
@Transactional
public class MercuryService {

    private final Logger log = LoggerFactory.getLogger(MercuryService.class);

    private final MercuryRepository mercuryRepository;

    private final MercuryMapper mercuryMapper;

    public MercuryService(MercuryRepository mercuryRepository, MercuryMapper mercuryMapper) {
        this.mercuryRepository = mercuryRepository;
        this.mercuryMapper = mercuryMapper;
    }

    /**
     * Save a mercury.
     *
     * @param mercuryDTO the entity to save
     * @return the persisted entity
     */
    public MercuryDTO save(MercuryDTO mercuryDTO) {
        log.debug("Request to save Mercury : {}", mercuryDTO);
        Mercury mercury = mercuryMapper.toEntity(mercuryDTO);
        mercury = mercuryRepository.save(mercury);
        return mercuryMapper.toDto(mercury);
    }

    /**
     * Get all the mercuries.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MercuryDTO> findAll() {
        log.debug("Request to get all Mercuries");
        return mercuryRepository.findAll().stream()
            .map(mercuryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mercury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Mercury : {}", id);
        mercuryRepository.delete(id);
    }
}
