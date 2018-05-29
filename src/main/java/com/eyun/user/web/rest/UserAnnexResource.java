package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.service.UaaService;
import com.eyun.user.service.UserAnnexService;
import com.eyun.user.service.dto.UserDTO;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.dto.UserAnnexCriteria;
import com.eyun.user.service.UserAnnexQueryService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing UserAnnex.
 */
@RestController
@RequestMapping("/api")
public class UserAnnexResource {

    private final Logger log = LoggerFactory.getLogger(UserAnnexResource.class);

    private static final String ENTITY_NAME = "userAnnex";

    private final UserAnnexService userAnnexService;

    private final UserAnnexQueryService userAnnexQueryService;



    @Autowired
    private UaaService uaaService;


    public UserAnnexResource(UserAnnexService userAnnexService, UserAnnexQueryService userAnnexQueryService) {
        this.userAnnexService = userAnnexService;
        this.userAnnexQueryService = userAnnexQueryService;
    }

    /**
     * POST  /user-annexes : Create a new userAnnex.
     *
     * @param userAnnexDTO the userAnnexDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAnnexDTO, or with status 400 (Bad Request) if the userAnnex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-annexes")
    @Timed
    public ResponseEntity<UserAnnexDTO> createUserAnnex(@RequestBody UserAnnexDTO userAnnexDTO) throws URISyntaxException {
        log.debug("REST request to save UserAnnex : {}", userAnnexDTO);
//        if (userAnnexDTO.getId() != null) {
//            throw new BadRequestAlertException("A new userAnnex cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        UserAnnexDTO result = userAnnexService.save(userAnnexDTO);
        return ResponseEntity.created(new URI("/api/user-annexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-annexes : Updates an existing userAnnex.
     *
     * @param userAnnexDTO the userAnnexDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAnnexDTO,
     * or with status 400 (Bad Request) if the userAnnexDTO is not valid,
     * or with status 500 (Internal Server Error) if the userAnnexDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-annexes")
    @Timed
    public ResponseEntity<UserAnnexDTO> updateUserAnnex(@RequestBody UserAnnexDTO userAnnexDTO) throws URISyntaxException {
        log.debug("REST request to update UserAnnex : {}", userAnnexDTO);
        if (userAnnexDTO.getId() == null) {
            return createUserAnnex(userAnnexDTO);
        }
        UserAnnexDTO result = userAnnexService.save(userAnnexDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAnnexDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-annexes : get all the userAnnexes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userAnnexes in body
     */
    @GetMapping("/user-annexes")
    @Timed
    public ResponseEntity<List<UserAnnexDTO>> getAllUserAnnexes(UserAnnexCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserAnnexes by criteria: {}", criteria);
        Page<UserAnnexDTO> page = userAnnexQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-annexes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-annexes/:id : get the "id" userAnnex.
     *
     * @param id the id of the userAnnexDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAnnexDTO, or with status 404 (Not Found)
     */

    @GetMapping("/user-annexes/{id}")
    @Timed
    public ResponseEntity<UserAnnexDTO> getUserAnnex(@PathVariable Long id) {
        log.debug("REST request to get UserAnnex : {}", id);
        UserAnnexDTO userAnnexDTO = userAnnexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAnnexDTO));
    }






    @ApiOperation("修改用户信息")
    @PostMapping("/user-annexes-useregis/updaUserInfo")
    @Timed
    public ResponseEntity updaUserInfo(@RequestBody  UserAnnexDTO userParamDTO){
        UserDTO account = uaaService.getAccount();
        userParamDTO.setId(account.getId());
        userAnnexService.updataUserInfo(userParamDTO);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user-annexes/userInfo")
    @Timed
    public ResponseEntity userInfo(){
        UserDTO account = uaaService.getAccount();
        log.info("用户ID是{}",account.getId());
        UserAnnexDTO userAnnexDTO = new UserAnnexDTO();
        //查询当前用户
        UserAnnex userAnnex = userAnnexService.userInfo(account.getId());
        BeanUtils.copyProperties(userAnnex,userAnnexDTO);
        //查看邀请人
        if(userAnnexDTO.getInviterId() == null){
//            UserAnnexDTO inviter = userAnnexService.findOne(userAnnexDTO.getInviterId());
//            userAnnexDTO.setInvNickName(null);
//            userAnnexDTO.setInvPhone(null);
        }else{
            UserAnnexDTO inviter = userAnnexService.findOne(userAnnexDTO.getInviterId());
        	userAnnexDTO.setInvNickName(inviter.getNickname());
            userAnnexDTO.setInvPhone(inviter.getPhone());
        }
        userAnnexDTO.setUpdatedTime(Instant.now());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAnnexDTO));
    }


    /**
     * DELETE  /user-annexes/:id : delete the "id" userAnnex.
     *
     * @param id the id of the userAnnexDTO to delete
     * @return the ResponseEntity with status 200 (OK)

     @DeleteMapping("/user-annexes/{id}")
     @Timed public ResponseEntity<Void> deleteUserAnnex(@PathVariable Long id) {
     log.debug("REST request to delete UserAnnex : {}", id);
     userAnnexService.delete(id);
     return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
     }*/



    /**
     * 用户分享关系绑定接口
     * @param
     * @return
     */
    @ApiOperation("用户关系绑定")
    @PostMapping("/user-annexes-userBinding")
    @Timed
    public ResponseEntity userBinding(@RequestBody  UserAnnexDTO userParamDTO){
        userAnnexService.userBinding(userParamDTO);
        return ResponseEntity.ok().body(null);
    }



    @ApiOperation("用户分享列表")
   @GetMapping("/user-annexes-shareUserList")
    @Timed
    public ResponseEntity shareUserList(){
        UserDTO account = uaaService.getAccount();
        List<UserAnnex> annexDTOS = userAnnexService.shareUserList(account.getId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annexDTOS));
    }


    @ApiOperation("变更用户的状态")
    @PostMapping("/user-annexes-UpdaeUserStatus")
    @Timed
    public ResponseEntity UpdaeUserStatus(@RequestBody  UserDTO userParamDTO){
        userAnnexService.UpdaeUserStatus(userParamDTO.getId());
        return ResponseEntity.ok().body(null);
    }




    @ApiOperation("普通用户升级为增值用户")
    @PostMapping("/user-annexes-valueUser")
    @Timed
    public ResponseEntity valueUser (){
        UserDTO account = uaaService.getAccount();
        userAnnexService.valueUser(account.getId());
        return ResponseEntity.ok().body("OK");
    }


    @ApiOperation("增值用户邀请增值商户")
    @PostMapping("/user-annexes-userAddMoney")
    @Timed
    public ResponseEntity userAddMoney (@RequestBody  UserAnnexDTO userParamDTO){
        userAnnexService.userAddMoney(userParamDTO.getId());
        return ResponseEntity.ok().body("OK");
    }


    @ApiOperation("升级为服务商")
    @GetMapping("/user-annexes-changeService/{id}")
    @Timed
    public ResponseEntity changeService(@PathVariable Long id){
        userAnnexService.changeService(id);
        return ResponseEntity.ok().body("OK");


    }


    @ApiOperation("根据商户的ID拿到当前用户ID")
    @GetMapping("/user-annexes-ShopIdFindByUserid/{shopid}")
    @Timed
    public ResponseEntity<Long> ShopIdFindByUserid(@PathVariable Long shopid){
        Long aLong = userAnnexService.ShopIdFindByUserid(shopid);
        return ResponseEntity.ok().body(aLong);

    }

    @ApiOperation("检查用户申请商户的的状态")
    @GetMapping("/user-annexes-getCheckUserStatus")
    @Timed
    public ResponseEntity<UserAnnex> getCheckUserStatus(){
        UserDTO account = uaaService.getAccount();
        UserAnnex userAnnex = userAnnexService.checkUserStatus(account.getId());
        return ResponseEntity.ok().body(userAnnex);


    }

    @ApiOperation("通过电话号码获取用户的信息")
    @GetMapping("/user-annexes-getUserInfosByPhone/{phone}")
    @Timed
   public ResponseEntity<UserAnnex> getUserInfosByPhone(@PathVariable String phone){

            UserAnnex userAnnexInfos = userAnnexService.getUserInfosByPhone(phone);
            return ResponseEntity.ok().body(userAnnexInfos);

    }

    @ApiOperation("获取当前用户的推荐人")
    @GetMapping("/user-annexes-getReferees")
    @Timed
    public ResponseEntity<UserAnnex> getReferees(){
        UserDTO account = uaaService.getAccount();
       UserAnnex userReferees = userAnnexService.getReferees(account.getId());

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userReferees));

    }


    @ApiOperation("获取二级邀请人")
    @GetMapping("/user-annexes-getSecondinviter/{id}")
    @Timed
    public ResponseEntity<List<UserAnnex>> getSecondinviter(@PathVariable Long id){

        List<UserAnnex> secondinviterLists =
               userAnnexService.getSecondinviter(id);

        return  ResponseUtil.wrapOrNotFound(Optional.ofNullable(secondinviterLists));

    }

    @ApiOperation("获取团队人数")
    @GetMapping("/user-annexes-getTeamSize")
    @Timed
    public ResponseEntity<Integer> getTeamSize() {

        UserDTO account = uaaService.getAccount();

        Integer team = userAnnexService.getTeam(account.getId());

        return  ResponseUtil.wrapOrNotFound(Optional.ofNullable(team));


    }









}
