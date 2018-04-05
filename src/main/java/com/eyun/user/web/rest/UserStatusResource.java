package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.UserStatusService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.UserStatusDTO;
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
 * REST controller for managing UserStatus.
 */
@RestController
@RequestMapping("/api")
public class UserStatusResource {

    private final Logger log = LoggerFactory.getLogger(UserStatusResource.class);

    private static final String ENTITY_NAME = "userStatus";

    private final UserStatusService userStatusService;

    public UserStatusResource(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    /**
     * POST  /user-statuses : Create a new userStatus.
     *
     * @param userStatusDTO the userStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStatusDTO, or with status 400 (Bad Request) if the userStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-statuses")
    @Timed
    public ResponseEntity<UserStatusDTO> createUserStatus(@RequestBody UserStatusDTO userStatusDTO) throws URISyntaxException {
        log.debug("REST request to save UserStatus : {}", userStatusDTO);
        if (userStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new userStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserStatusDTO result = userStatusService.save(userStatusDTO);
        return ResponseEntity.created(new URI("/api/user-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-statuses : Updates an existing userStatus.
     *
     * @param userStatusDTO the userStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStatusDTO,
     * or with status 400 (Bad Request) if the userStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the userStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-statuses")
    @Timed
    public ResponseEntity<UserStatusDTO> updateUserStatus(@RequestBody UserStatusDTO userStatusDTO) throws URISyntaxException {
        log.debug("REST request to update UserStatus : {}", userStatusDTO);
        if (userStatusDTO.getId() == null) {
            return createUserStatus(userStatusDTO);
        }
        UserStatusDTO result = userStatusService.save(userStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-statuses : get all the userStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStatuses in body
     */
    @GetMapping("/user-statuses")
    @Timed
    public List<UserStatusDTO> getAllUserStatuses() {
        log.debug("REST request to get all UserStatuses");
        return userStatusService.findAll();
        }

    /**
     * GET  /user-statuses/:id : get the "id" userStatus.
     *
     * @param id the id of the userStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-statuses/{id}")
    @Timed
    public ResponseEntity<UserStatusDTO> getUserStatus(@PathVariable Long id) {
        log.debug("REST request to get UserStatus : {}", id);
        UserStatusDTO userStatusDTO = userStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userStatusDTO));
    }

    /**
     * DELETE  /user-statuses/:id : delete the "id" userStatus.
     *
     * @param id the id of the userStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserStatus(@PathVariable Long id) {
        log.debug("REST request to delete UserStatus : {}", id);
        userStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
