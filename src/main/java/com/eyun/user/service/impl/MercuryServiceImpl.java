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
    @Override
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



    @Override
    public List<MercuryDTO> findNearMerchants(MercuryDTO mercuryDTO) {
        //经度
        Double langitude = mercuryDTO.getLangitude();
        //维度
        Double lantitude = mercuryDTO.getLantitude();
        //1.把经纬度转换成附近的位置
         List<MercuryDTO> MercuryInfoList= this.findNeighPosition(lantitude, langitude);
        return MercuryInfoList;

    }

    private List<MercuryDTO> findNeighPosition(Double lantitude, Double langitude) {
        //先计算查询点的经纬度范围

        //地球半径千米
        double r = 6371;

        //0.5千米距离
        double dis = 0.5;

        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(lantitude*Math.PI/180));
        //角度转为弧度
        dlng = dlng*180/Math.PI;

        double dlat = dis/r;

        dlat = dlat*180/Math.PI;
        //维度最小值
        double minlat =lantitude-dlat;
        System.out.println(minlat);
        //维度最大值
        double maxlat = lantitude+dlat;
        System.out.println(maxlat);
        //经度最小值
        double minlng = langitude -dlng;
        System.out.println(minlng );
        double maxlng = langitude + dlng;
        //经度最大值
        System.out.println(maxlng);
        List<MercuryDTO> nearMerchantsList = mercuryRepository.findNearMerchantsList(minlng,maxlng,minlat,maxlat);
        MercuryDTO mercuryDTO = new MercuryDTO();
        mercuryDTO.setCity("北京");
        mercuryDTO.setImgLicense("wjkjwkjwwj");
        nearMerchantsList.add(mercuryDTO);


        return nearMerchantsList;
    }


}
