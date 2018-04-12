package com.eyun.user.service.impl;

import com.eyun.user.service.UserAnnexService;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserAnnex.
 */
@Service
@Transactional
public class UserAnnexServiceImpl implements UserAnnexService {

    private final Logger log = LoggerFactory.getLogger(UserAnnexServiceImpl.class);

    private final UserAnnexRepository userAnnexRepository;

    private final UserAnnexMapper userAnnexMapper;

    public UserAnnexServiceImpl(UserAnnexRepository userAnnexRepository, UserAnnexMapper userAnnexMapper) {
        this.userAnnexRepository = userAnnexRepository;
        this.userAnnexMapper = userAnnexMapper;
    }

    /**
     * Save a userAnnex.
     *
     * @param userAnnexDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAnnexDTO save(UserAnnexDTO userAnnexDTO) {
        log.debug("Request to save UserAnnex : {}", userAnnexDTO);
        UserAnnex userAnnex = userAnnexMapper.toEntity(userAnnexDTO);
        userAnnex = userAnnexRepository.save(userAnnex);
        return userAnnexMapper.toDto(userAnnex);
    }

    /**
     * Get all the userAnnexes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnnexDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAnnexes");
        return userAnnexRepository.findAll(pageable)
            .map(userAnnexMapper::toDto);
    }


    /**
     * get all the userAnnexes where OwnerRelation is null.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAnnexDTO> findAllWhereOwnerRelationIsNull() {
        log.debug("Request to get all userAnnexes where OwnerRelation is null");
        return StreamSupport
            .stream(userAnnexRepository.findAll().spliterator(), false)
            .filter(userAnnex -> userAnnex.getOwnerRelation() == null)
            .map(userAnnexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userAnnex by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserAnnexDTO findOne(Long id) {
        log.debug("Request to get UserAnnex : {}", id);
        UserAnnex userAnnex = userAnnexRepository.findOne(id);
        return userAnnexMapper.toDto(userAnnex);
    }

    /**
     * Delete the userAnnex by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAnnex : {}", id);
        userAnnexRepository.delete(id);
    }


    /**
     * @param userAnnexDTO
     */
    @Override
    @Transactional
    public void updataUserName(UserAnnexDTO userAnnexDTO) {
        UserAnnex userAnnex = new UserAnnex();
        BeanUtils.copyProperties(userAnnexDTO, userAnnex);
        userAnnexRepository.saveAndFlush(userAnnex);

    }

    /**
     * @param userAnnexDTO
     */
    @Override
    @Transactional
    public void updataUserNickname(UserAnnexDTO userAnnexDTO) {
        UserAnnex userAnnex = new UserAnnex();
        BeanUtils.copyProperties(userAnnexDTO, userAnnex);
        userAnnexRepository.saveAndFlush(userAnnex);

    }


    /**
     * @param userAnnexDTO
     */
    @Override
    @Transactional
    public void updataUserPhone(UserAnnexDTO userAnnexDTO) {
        UserAnnex userAnnex = new UserAnnex();
        BeanUtils.copyProperties(userAnnexDTO, userAnnex);
        userAnnexRepository.saveAndFlush(userAnnex);

    }


    /**
     * @param userAnnexDTO
     */
    @Override
    @Transactional
    public void updataUserAvatar(UserAnnexDTO userAnnexDTO) {
        UserAnnex userAnnex = new UserAnnex();
        BeanUtils.copyProperties(userAnnexDTO, userAnnex);
        userAnnexRepository.saveAndFlush(userAnnex);
    }


    /**
     * 用户注册
     * @param userAnnexDTO
     */
    @Override
    public void userRegis(UserAnnexDTO userAnnexDTO) {
        UserAnnex userAnnex = new UserAnnex();
        userAnnex.setStatus(1);
        //0，默认为普通会员 1,增值会员
        userAnnex.setType(0);
        BeanUtils.copyProperties(userAnnexDTO,userAnnex);
        this.userAnnexRepository.save(userAnnex);

    }
}
