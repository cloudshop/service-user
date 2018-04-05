package com.eyun.user.service;

import com.eyun.user.domain.MercuryStatus;
import com.eyun.user.repository.MercuryStatusRepository;
import com.eyun.user.service.dto.MercuryStatusDTO;
import com.eyun.user.service.mapper.MercuryStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MercuryStatus.
 */
@Service
@Transactional
public class MercuryStatusService {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusService.class);

    private final MercuryStatusRepository mercuryStatusRepository;

    private final MercuryStatusMapper mercuryStatusMapper;

    public MercuryStatusService(MercuryStatusRepository mercuryStatusRepository, MercuryStatusMapper mercuryStatusMapper) {
        this.mercuryStatusRepository = mercuryStatusRepository;
        this.mercuryStatusMapper = mercuryStatusMapper;
    }

    /**
     * Save a mercuryStatus.
     *
     * @param mercuryStatusDTO the entity to save
     * @return the persisted entity
     */
    public MercuryStatusDTO save(MercuryStatusDTO mercuryStatusDTO) {
        log.debug("Request to save MercuryStatus : {}", mercuryStatusDTO);
        MercuryStatus mercuryStatus = mercuryStatusMapper.toEntity(mercuryStatusDTO);
        mercuryStatus = mercuryStatusRepository.save(mercuryStatus);
        return mercuryStatusMapper.toDto(mercuryStatus);
    }

    /**
     * Get all the mercuryStatuses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MercuryStatusDTO> findAll() {
        log.debug("Request to get all MercuryStatuses");
        return mercuryStatusRepository.findAll().stream()
            .map(mercuryStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mercuryStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MercuryStatusDTO findOne(Long id) {
        log.debug("Request to get MercuryStatus : {}", id);
        MercuryStatus mercuryStatus = mercuryStatusRepository.findOne(id);
        return mercuryStatusMapper.toDto(mercuryStatus);
    }

    /**
     * Delete the mercuryStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MercuryStatus : {}", id);
        mercuryStatusRepository.delete(id);
    }
}
