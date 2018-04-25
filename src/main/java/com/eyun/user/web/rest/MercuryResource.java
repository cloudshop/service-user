package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.MercuryService;
import com.eyun.user.service.UaaService;
import com.eyun.user.service.dto.UserDTO;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.dto.MercuryCriteria;
import com.eyun.user.service.MercuryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * REST controller for managing Mercury.
 */
@RestController
@RequestMapping("/api")
public class MercuryResource {

    private final Logger log = LoggerFactory.getLogger(MercuryResource.class);

    private static final String ENTITY_NAME = "mercury";

    private final MercuryService mercuryService;

    private final MercuryQueryService mercuryQueryService;

    @Autowired private  UaaService uaaService;


    public MercuryResource(MercuryService mercuryService, MercuryQueryService mercuryQueryService) {
        this.mercuryService = mercuryService;
        this.mercuryQueryService = mercuryQueryService;
    }

    /**
     * POST  /mercuries : Create a new mercury.
     *
     * @param mercuryDTO the mercuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mercuryDTO, or with status 400 (Bad Request) if the mercury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mercuries")
    @Timed
    public ResponseEntity<MercuryDTO> createMercury(@RequestBody MercuryDTO mercuryDTO) throws URISyntaxException {
        log.debug("REST request to save Mercury : {}", mercuryDTO);
        if (mercuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new mercury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MercuryDTO result = mercuryService.save(mercuryDTO);
        return ResponseEntity.created(new URI("/api/mercuries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mercuries : Updates an existing mercury.
     *
     * @param mercuryDTO the mercuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mercuryDTO,
     * or with status 400 (Bad Request) if the mercuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the mercuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mercuries")
    @Timed
    public ResponseEntity<MercuryDTO> updateMercury(@RequestBody MercuryDTO mercuryDTO) throws URISyntaxException {
        log.debug("REST request to update Mercury : {}", mercuryDTO);
        if (mercuryDTO.getId() == null) {
            return createMercury(mercuryDTO);
        }
        MercuryDTO result = mercuryService.save(mercuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mercuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mercuries : get all the mercuries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mercuries in body
     */
    @GetMapping("/mercuries")
    @Timed
    public ResponseEntity<List<MercuryDTO>> getAllMercuries(MercuryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Mercuries by criteria: {}", criteria);
        Page<MercuryDTO> page = mercuryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mercuries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mercuries/:id : get the "id" mercury.
     *
     * @param id the id of the mercuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mercuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mercuries/{id}")
    @Timed
    public ResponseEntity<MercuryDTO> getMercury(@PathVariable Long id) {
        log.debug("REST request to get Mercury : {}", id);
        MercuryDTO mercuryDTO = mercuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mercuryDTO));
    }

    /**
     * DELETE  /mercuries/:id : delete the "id" mercury.
     *
     * @param id the id of the mercuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mercuries/{id}")
    @Timed
    public ResponseEntity<Void> deleteMercury(@PathVariable Long id) {
        log.debug("REST request to delete Mercury : {}", id);
        mercuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * 穿着西装敲代码 文亮
     * 根据经纬度查询附近的商家

     *
     * @return
     */
    @PostMapping("/mercuries/info-list/index")
    @Timed
    public ResponseEntity<List<MercuryDTO>> getMercuryInfoList(@RequestBody MercuryDTO mercuryDTO) {
        List<MercuryDTO> MercuryInfoList = mercuryService.findNearMerchants(mercuryDTO);
        return ResponseEntity.ok()
            .body(MercuryInfoList);
    }


    /**
     *
     * 查询用户申请状态
     * @param
     * @return
     */
    @ApiOperation("查询用户申请状态")
    @GetMapping("/mercuries/checkMercuryStatus")
    @Timed
    public ResponseEntity checkMercuryStatus(){
        UserDTO account = uaaService.getAccount();
        log.info("{}",account.getId());
        Map map = mercuryService.checkMercuryStatus(account.getId());
        return ResponseEntity.ok().body(map);
    }


    /**
     * 添加商户申请
     * @param mercuryDTO
     * @return
     */
    @ApiOperation("添加商户申请 ")
    @PostMapping("/mercuries/addMercury")
    @Timed
    public ResponseEntity addMercury(@RequestBody MercuryDTO mercuryDTO){
        log.info("{}",mercuryDTO);
        mercuryService.addMercury(mercuryDTO);
        return ResponseEntity.ok().body(null);
    }




    /**
     * 穿着西装敲代码 文亮
     * 根据经纬度查询附近的商家
     *
     * @return
     */
    @ApiOperation("根据经纬度查询附近的商家 ")
    @PostMapping("/mercuries/info-list/MercuryInfo")
    @Timed
    public ResponseEntity getMercuryInfo(@RequestBody MercuryDTO mercuryDTO) {
        List<Map> MercuryInfoList = mercuryService.findNearList(mercuryDTO);
        return ResponseEntity.ok()
            .body(MercuryInfoList);
    }


    @ApiOperation("商品列表 ")
    @PostMapping("/mercuries/getMercuryInfoProductList/{id}")
    @Timed
    public ResponseEntity getMercuryInfoProductList(@PathVariable Long id){
        List<Map> relult = mercuryService.getMercuryInfoProductList(id);
        return ResponseEntity.ok().body(relult);

    }



    @ApiOperation("商户申请图片上传 ")
    @PostMapping("/mercuries/uploadMercuryImages")
    @Timed
    public ResponseEntity uploadMercuryImages(@RequestBody MercuryDTO mercuryDTO){
            mercuryService.uploadMercuryImages(mercuryDTO);
        return ResponseEntity.ok().body(null);

    }

    @ApiOperation("商户等级变更")
    @GetMapping("/mercuries/mercuryChangeStatus")
    @Timed
    public ResponseEntity mercuryChangeStatus(){
        UserDTO account = uaaService.getAccount();
        mercuryService.mercuryChangeStatus(account.getId());
        return ResponseEntity.ok().body(null);

    }









}

