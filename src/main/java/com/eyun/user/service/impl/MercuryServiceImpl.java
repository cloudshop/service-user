package com.eyun.user.service.impl;

import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.MercuryService;
import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.ProductService;
import com.eyun.user.service.UaaService;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.dto.UserDTO;
import com.eyun.user.service.mapper.MercuryMapper;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.*;
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

    @Autowired
    private  OwnerRelationRepository relationRepository;

    @Autowired
    private UserAnnexRepository userAnnexRepository;

    @Autowired
    private ProductService productService;


    @Autowired private UaaService uaaService;

    @Autowired private MercuryService mercuryService;





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






    /**
     * 添加商户的申请
     * @param mercuryDTO
     */
    @Override
    public void addMercury(MercuryDTO mercuryDTO) {
        Mercury mercury = new Mercury();
        BeanUtils.copyProperties(mercuryDTO,mercury);
        mercuryRepository.save(mercury);
    }

    /**
     * 检查商户申请状态 默认0 未申请
     * @param
     * @return
     */
    @Override
    public Map checkMercuryStatus(Long id) {
       return mercuryRepository.checkMercuryStatus(id);
    }


    /**
     * 商户申请
     * @param
     * @param
     */
    @Override
    public String uploadMercuryImages( MercuryDTO mercuryDTO) {

        UserAnnex annex = userAnnexRepository.findByid(mercuryDTO.getId());
        if (annex.getType()==1) {
            log.info("商户提交资料开始************************************************************************************");
            Mercury mercury = new Mercury();
            mercury.setId(mercury.getId());
            mercury.setName(mercuryDTO.getName());
            mercury.setImgIdcardBack(mercuryDTO.getImgIdcardBack());
            mercury.setImgIdcardFront(mercuryDTO.getImgIdcardFront());
            mercury.setImgFacade(mercuryDTO.getImgFacade());
            mercury.setImgIdcardHold(mercuryDTO.getImgIdcardHold());
            mercury.setImgIntroduces(mercuryDTO.getImgIntroduces());
            mercury.setImgLicense(mercuryDTO.getImgLicense());
            mercury.setUpdatedTime(Instant.now());
            mercury.setCreatedTime(Instant.now());
            mercury.setLangitude(116.66847);
            mercury.setLantitude(39.88372);
            mercury.setCity("新疆乌鲁木齐天山区红旗路30");
            //1，表示店铺未激活，2.表示店铺已激活
            mercury.setStatus(2);
            Mercury save = mercuryRepository.save(mercury);
            log.info("商户提交申请结束**********************************************************************************");


            log.info("创建店铺开始*************************************************************************************");
            OwnerRelation ownerRelation = new OwnerRelation();
            ownerRelation.setMercury(save);
            UserAnnex userAnnex = new UserAnnex();
            userAnnex.setId(mercuryDTO.getId());
            ownerRelation.setUserAnnex(userAnnex);
            ownerRelation.setCreatedTime(Instant.now());
            ownerRelation.setUpdatedTime(Instant.now());
            relationRepository.save(ownerRelation);
            log.info("创建店铺开始结束*********************************************************************************");

            //变更用户的type普通商家
            annex.setType(3);
            //店铺申请成功2，不成功设为1
            annex.setStatus(2);
            annex.setUpdatedTime(Instant.now());
            userAnnexRepository.saveAndFlush(annex);

            return "ok";
        }

        return "fal";








    }



    @Override
    public void setBackground(Map background) {
        UserDTO account = uaaService.getAccount();
        Map MercuryId = mercuryService.getUserIdMercuryId(account.getId());
        Mercury one = mercuryRepository.findOne(Long.valueOf(MercuryId.get("id").toString()));
        one.setImgIntroduces(background.get("imgIntroduces").toString());
        mercuryRepository.saveAndFlush(one);
    }

    @Override
    public MercuryDTO getUserShop() {
        UserDTO account = uaaService.getAccount();
        Map MercuryId = mercuryService.getUserIdMercuryId(account.getId());
        Mercury one = mercuryRepository.findOne(Long.valueOf(MercuryId.get("id").toString()));
        return mercuryMapper.toDto(one);
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

    @Override
    public List<Map> findNearList(MercuryDTO mercuryDTO) {
      return   mercuryRepository.findNearMerchants(mercuryDTO.getLangitude(),mercuryDTO.getLantitude());

    }


    /**
     * 根据商户ID查询商户列表
     * @param id
     * @return
     */
    @Override
    public Map getMercuryInfoProductList(Long id,Integer pageNum,Integer pageSize) {
        List<Map> productList = productService.ProductList(id,pageNum,pageSize);
        Mercury mercury = mercuryRepository.findOne(id);
        Map<String,Object> map = new HashMap();
        map.put("mercury", mercury);
        map.put("productList", productList);
        return map;
    }


    /**
     * 变更商户状态
     * @param
     */
    @Override
    public void mercuryChangeStatus(Long id) {
        Mercury one = mercuryRepository.findOne(id);
        //增值商户
        one.setStatus(4);
        mercuryRepository.saveAndFlush(one);

    }


    /**
     * 根据用户ID拿到对应的商户的ID
     * @param id
     * @return
     */
    @Override
    public Map getUserIdMercuryId(Long id) {
       return mercuryRepository.getUserIdMercuryId(id);
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
        if (nearMerchantsList.size()>0){
            return nearMerchantsList;
        }
        return null;

    }

	@Override
	public List<Mercury> getFavMercuries(List<Long> ids) {

        List<Mercury> lists = new ArrayList<>();
        ids.forEach( id -> {
            Mercury one = mercuryRepository.findOne(id);
            lists.add(one);
        });

		return lists;
	}


}
