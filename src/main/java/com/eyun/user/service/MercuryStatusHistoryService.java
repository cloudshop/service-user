package com.eyun.user.service;

import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.repository.MercuryStatusHistoryRepository;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import com.eyun.user.service.mapper.MercuryStatusHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MercuryStatusHistory.
 */
@Service
@Transactional
public class MercuryStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusHistoryService.class);

    private final MercuryStatusHistoryRepository mercuryStatusHistoryRepository;

    private final MercuryStatusHistoryMapper mercuryStatusHistoryMapper;

    public MercuryStatusHistoryService(MercuryStatusHistoryRepository mercuryStatusHistoryRepository, MercuryStatusHistoryMapper mercuryStatusHistoryMapper) {
        this.mercuryStatusHistoryRepository = mercuryStatusHistoryRepository;
        this.mercuryStatusHistoryMapper = mercuryStatusHistoryMapper;
    }

    /**
     * Save a mercuryStatusHistory.
     *
     * @param mercuryStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    public MercuryStatusHistoryDTO save(MercuryStatusHistoryDTO mercuryStatusHistoryDTO) {
        log.debug("Request to save MercuryStatusHistory : {}", mercuryStatusHistoryDTO);
        MercuryStatusHistory mercuryStatusHistory = mercuryStatusHistoryMapper.toEntity(mercuryStatusHistoryDTO);
        mercuryStatusHistory = mercuryStatusHistoryRepository.save(mercuryStatusHistory);
        return mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);
    }

    /**
     * Get all the mercuryStatusHistories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MercuryStatusHistoryDTO> findAll() {
        log.debug("Request to get all MercuryStatusHistories");
        return mercuryStatusHistoryRepository.findAll().stream()
            .map(mercuryStatusHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mercuryStatusHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MercuryStatusHistoryDTO findOne(Long id) {
        log.debug("Request to get MercuryStatusHistory : {}", id);
        MercuryStatusHistory mercuryStatusHistory = mercuryStatusHistoryRepository.findOne(id);
        return mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);
    }

    /**
     * Delete the mercuryStatusHistory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MercuryStatusHistory : {}", id);
        mercuryStatusHistoryRepository.delete(id);
    }
}
