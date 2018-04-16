package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.UaaService;
import com.eyun.user.service.UserAnnexService;
import com.eyun.user.service.dto.UserDTO;
import com.eyun.user.service.dto.UserParamDTO;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.dto.UserAnnexCriteria;
import com.eyun.user.service.UserAnnexQueryService;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.Optional;
import java.util.stream.StreamSupport;

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
        if (userAnnexDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAnnex cannot already have an ID", ENTITY_NAME, "idexists");
        }
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






    @PostMapping("/user-annexes-useregis/updaUserInfo/")
    @Timed
    public ResponseEntity updaUserInfo(@RequestBody  UserParamDTO userParamDTO){
        UserDTO account = uaaService.getAccount();
        userParamDTO.setUserid(account.getId());
        userAnnexService.updataUserInfo(userParamDTO);
        return ResponseEntity.ok().body(null);

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
}
