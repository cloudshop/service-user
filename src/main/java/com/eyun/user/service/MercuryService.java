package com.eyun.user.service;

import com.eyun.user.domain.Mercury;
import com.eyun.user.service.dto.MercuryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Mercury.
 */
public interface MercuryService {

    /**
     * Save a mercury.
     *
     * @param mercuryDTO the entity to save
     * @return the persisted entity
     */
    MercuryDTO save(MercuryDTO mercuryDTO);

    /**
     * Get all the mercuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MercuryDTO> findAll(Pageable pageable);
    /**
     * Get all the MercuryDTO where OwnerRelation is null.
     *
     * @return the list of entities
     */
    List<MercuryDTO> findAllWhereOwnerRelationIsNull();

    /**
     * Get the "id" mercury.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MercuryDTO findOne(Long id);

    /**
     * Delete the "id" mercury.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * 附近商户查询
     * @param mercuryDTO
     * @return
     */
    List<MercuryDTO> findNearMerchants(MercuryDTO mercuryDTO);





    /**
     * 检查商户申请状态
     * @param
     * @return
     */
    Map checkMercuryStatus(Long id);


    /**
     * 添加商户的申请
     * @param mercuryDTO
     */
    void addMercury(MercuryDTO mercuryDTO);


    List<Map> findNearList(MercuryDTO mercuryDTO);


    /**
     * 根据商户ID查询商品列表
     * @param id
     * @return
     */
    Map getMercuryInfoProductList(Long id,Integer pageNum,Integer pageSize);



    String uploadMercuryImages( MercuryDTO mercuryDTO);

    void mercuryChangeStatus(Long id);

    Map getUserIdMercuryId(Long id);

    void setBackground(Map background);
    public MercuryDTO getUserShop();

	List<Mercury> getFavMercuries(List<Long> ids);
}
