package com.eyun.user.service.impl;

import com.eyun.user.service.UaaService;
import com.eyun.user.service.UserAnnexService;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.dto.UserDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    private UaaService uaaService;

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


    @Override
    public UserAnnex userInfo(Long id) {
        UserDTO account = uaaService.getAccount();
        UserAnnex userAnnex = new UserAnnex();
        userAnnex.setNickname("gr"+System.currentTimeMillis());
        userAnnex.setUserid(id);
        userAnnex.setId(id);
        userAnnexRepository.save(userAnnex);
        UserAnnex one = userAnnexRepository.findOne(id);
        one.setPhone(account.getLogin());
        return one;


    }


    /**
     * 用户分享邀请列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Map> shareUserList(Long id) {
        List<Map> resultUserList = userAnnexRepository.shareUserList(id);
        if (resultUserList.size() > 0) {
            return resultUserList;
        }
        throw new BadRequestAlertException(" 用户分享邀请列表不存在", "resultUserList", "resultUserListexists");


    }

    /**
     * 用户关系绑定
     *
     * @param
     */
    @Override
    public void userBinding(UserAnnexDTO userParamDTO) {
        UserAnnex userAnnex = new UserAnnex();
        BeanUtils.copyProperties(userParamDTO, userAnnex);
        userAnnexRepository.save(userAnnex);

    }

    /**
     * 修改用户相关的信息
     *
     * @param userParamDTO
     */
    @Override
    public void updataUserInfo(UserAnnexDTO userParamDTO) {

        //修改用户的头像
        if (userParamDTO.getType() == 0) {
            UserAnnex userAnnex = new UserAnnex();
            userAnnex.setAvatar(userParamDTO.getAvatar());
            userAnnex.setId(userParamDTO.getId());
            userAnnex.setUserid(userParamDTO.getUserid());
            userAnnexRepository.saveAndFlush(userAnnex);

            //修改用户电话
        } else if (userParamDTO.getType() == 1) {
            UserAnnex userAnnex = new UserAnnex();
            userAnnex.setPhone(userParamDTO.getPhone());
            userAnnex.setId(userParamDTO.getId());
            userAnnex.setUserid(userParamDTO.getUserid());
            userAnnexRepository.saveAndFlush(userAnnex);

            //修改用户昵称
        } else if (userParamDTO.getType() == 2) {
            UserAnnex userAnnex = new UserAnnex();
            userAnnex.setNickname(userParamDTO.getNickname());
            userAnnex.setId(userParamDTO.getId());
            userAnnex.setUserid(userParamDTO.getUserid());
            userAnnexRepository.saveAndFlush(userAnnex);


        }

    }


}



