package com.eyun.user.service;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserAnnex.
 */
@Service
@Transactional
public class UserAnnexService {

    private final Logger log = LoggerFactory.getLogger(UserAnnexService.class);

    private final UserAnnexRepository userAnnexRepository;

    private final UserAnnexMapper userAnnexMapper;

    public UserAnnexService(UserAnnexRepository userAnnexRepository, UserAnnexMapper userAnnexMapper) {
        this.userAnnexRepository = userAnnexRepository;
        this.userAnnexMapper = userAnnexMapper;
    }

    /**
     * Save a userAnnex.
     *
     * @param userAnnexDTO the entity to save
     * @return the persisted entity
     */
    public UserAnnexDTO save(UserAnnexDTO userAnnexDTO) {
        log.debug("Request to save UserAnnex : {}", userAnnexDTO);
        UserAnnex userAnnex = userAnnexMapper.toEntity(userAnnexDTO);
        userAnnex = userAnnexRepository.save(userAnnex);
        return userAnnexMapper.toDto(userAnnex);
    }

    /**
     * Get all the userAnnexes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserAnnexDTO> findAll() {
        log.debug("Request to get all UserAnnexes");
        return userAnnexRepository.findAllWithEagerRelationships().stream()
            .map(userAnnexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userAnnex by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserAnnexDTO findOne(Long id) {
        log.debug("Request to get UserAnnex : {}", id);
        UserAnnex userAnnex = userAnnexRepository.findOneWithEagerRelationships(id);
        return userAnnexMapper.toDto(userAnnex);
    }

    /**
     * Delete the userAnnex by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAnnex : {}", id);
        userAnnexRepository.delete(id);
    }
}