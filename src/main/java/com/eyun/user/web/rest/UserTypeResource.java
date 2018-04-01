package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.UserTypeService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.UserTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST controller for managing UserType.
 */
@RestController
@RequestMapping("/api")
public class UserTypeResource {

    private final Logger log = LoggerFactory.getLogger(UserTypeResource.class);

    private static final String ENTITY_NAME = "userType";

    private final UserTypeService userTypeService;

    public UserTypeResource(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    /**
     * POST  /user-types : Create a new userType.
     *
     * @param userTypeDTO the userTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userTypeDTO, or with status 400 (Bad Request) if the userType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-types")
    @Timed
    public ResponseEntity<UserTypeDTO> createUserType(@RequestBody UserTypeDTO userTypeDTO) throws URISyntaxException {
        log.debug("REST request to save UserType : {}", userTypeDTO);
        if (userTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new userType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTypeDTO result = userTypeService.save(userTypeDTO);
        return ResponseEntity.created(new URI("/api/user-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-types : Updates an existing userType.
     *
     * @param userTypeDTO the userTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userTypeDTO,
     * or with status 400 (Bad Request) if the userTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the userTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-types")
    @Timed
    public ResponseEntity<UserTypeDTO> updateUserType(@RequestBody UserTypeDTO userTypeDTO) throws URISyntaxException {
        log.debug("REST request to update UserType : {}", userTypeDTO);
        if (userTypeDTO.getId() == null) {
            return createUserType(userTypeDTO);
        }
        UserTypeDTO result = userTypeService.save(userTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-types : get all the userTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userTypes in body
     */
    @GetMapping("/user-types")
    @Timed
    public ResponseEntity<List<UserTypeDTO>> getAllUserTypes(Pageable pageable) {
        log.debug("REST request to get a page of UserTypes");
        Page<UserTypeDTO> page = userTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-types/:id : get the "id" userType.
     *
     * @param id the id of the userTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-types/{id}")
    @Timed
    public ResponseEntity<UserTypeDTO> getUserType(@PathVariable Long id) {
        log.debug("REST request to get UserType : {}", id);
        UserTypeDTO userTypeDTO = userTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userTypeDTO));
    }

    /**
     * DELETE  /user-types/:id : delete the "id" userType.
     *
     * @param id the id of the userTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        log.debug("REST request to delete UserType : {}", id);
        userTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
