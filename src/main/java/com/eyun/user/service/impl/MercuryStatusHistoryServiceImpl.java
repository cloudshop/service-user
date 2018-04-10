package com.eyun.user.service.impl;

import com.eyun.user.service.MercuryStatusHistoryService;
import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.repository.MercuryStatusHistoryRepository;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import com.eyun.user.service.mapper.MercuryStatusHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MercuryStatusHistory.
 */
@Service
@Transactional
public class MercuryStatusHistoryServiceImpl implements MercuryStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusHistoryServiceImpl.class);

    private final MercuryStatusHistoryRepository mercuryStatusHistoryRepository;

    private final MercuryStatusHistoryMapper mercuryStatusHistoryMapper;

    public MercuryStatusHistoryServiceImpl(MercuryStatusHistoryRepository mercuryStatusHistoryRepository, MercuryStatusHistoryMapper mercuryStatusHistoryMapper) {
        this.mercuryStatusHistoryRepository = mercuryStatusHistoryRepository;
        this.mercuryStatusHistoryMapper = mercuryStatusHistoryMapper;
    }

    /**
     * Save a mercuryStatusHistory.
     *
     * @param mercuryStatusHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MercuryStatusHistoryDTO save(MercuryStatusHistoryDTO mercuryStatusHistoryDTO) {
        log.debug("Request to save MercuryStatusHistory : {}", mercuryStatusHistoryDTO);
        MercuryStatusHistory mercuryStatusHistory = mercuryStatusHistoryMapper.toEntity(mercuryStatusHistoryDTO);
        mercuryStatusHistory = mercuryStatusHistoryRepository.save(mercuryStatusHistory);
        return mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);
    }

    /**
     * Get all the mercuryStatusHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MercuryStatusHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MercuryStatusHistories");
        return mercuryStatusHistoryRepository.findAll(pageable)
            .map(mercuryStatusHistoryMapper::toDto);
    }

    /**
     * Get one mercuryStatusHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MercuryStatusHistory : {}", id);
        mercuryStatusHistoryRepository.delete(id);
    }
}
