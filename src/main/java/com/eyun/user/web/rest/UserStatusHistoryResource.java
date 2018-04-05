package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.UserStatusHistoryService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.UserStatusHistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserStatusHistory.
 */
@RestController
@RequestMapping("/api")
public class UserStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(UserStatusHistoryResource.class);

    private static final String ENTITY_NAME = "userStatusHistory";

    private final UserStatusHistoryService userStatusHistoryService;

    public UserStatusHistoryResource(UserStatusHistoryService userStatusHistoryService) {
        this.userStatusHistoryService = userStatusHistoryService;
    }

    /**
     * POST  /user-status-histories : Create a new userStatusHistory.
     *
     * @param userStatusHistoryDTO the userStatusHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStatusHistoryDTO, or with status 400 (Bad Request) if the userStatusHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-status-histories")
    @Timed
    public ResponseEntity<UserStatusHistoryDTO> createUserStatusHistory(@RequestBody UserStatusHistoryDTO userStatusHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save UserStatusHistory : {}", userStatusHistoryDTO);
        if (userStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new userStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserStatusHistoryDTO result = userStatusHistoryService.save(userStatusHistoryDTO);
        return ResponseEntity.created(new URI("/api/user-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-status-histories : Updates an existing userStatusHistory.
     *
     * @param userStatusHistoryDTO the userStatusHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStatusHistoryDTO,
     * or with status 400 (Bad Request) if the userStatusHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the userStatusHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-status-histories")
    @Timed
    public ResponseEntity<UserStatusHistoryDTO> updateUserStatusHistory(@RequestBody UserStatusHistoryDTO userStatusHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update UserStatusHistory : {}", userStatusHistoryDTO);
        if (userStatusHistoryDTO.getId() == null) {
            return createUserStatusHistory(userStatusHistoryDTO);
        }
        UserStatusHistoryDTO result = userStatusHistoryService.save(userStatusHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userStatusHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-status-histories : get all the userStatusHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStatusHistories in body
     */
    @GetMapping("/user-status-histories")
    @Timed
    public List<UserStatusHistoryDTO> getAllUserStatusHistories() {
        log.debug("REST request to get all UserStatusHistories");
        return userStatusHistoryService.findAll();
        }

    /**
     * GET  /user-status-histories/:id : get the "id" userStatusHistory.
     *
     * @param id the id of the userStatusHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStatusHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-status-histories/{id}")
    @Timed
    public ResponseEntity<UserStatusHistoryDTO> getUserStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get UserStatusHistory : {}", id);
        UserStatusHistoryDTO userStatusHistoryDTO = userStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userStatusHistoryDTO));
    }

    /**
     * DELETE  /user-status-histories/:id : delete the "id" userStatusHistory.
     *
     * @param id the id of the userStatusHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-status-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete UserStatusHistory : {}", id);
        userStatusHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
