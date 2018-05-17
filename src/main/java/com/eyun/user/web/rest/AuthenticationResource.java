package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.AuthenticationService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.AuthenticationDTO;
import com.eyun.user.service.dto.AuthenticationCriteria;
import com.eyun.user.service.AuthenticationQueryService;
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
 * REST controller for managing Authentication.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationResource {

    private final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

    private static final String ENTITY_NAME = "authentication";

    private final AuthenticationService authenticationService;

    private final AuthenticationQueryService authenticationQueryService;

    public AuthenticationResource(AuthenticationService authenticationService, AuthenticationQueryService authenticationQueryService) {
        this.authenticationService = authenticationService;
        this.authenticationQueryService = authenticationQueryService;
    }

    /**
     * POST  /authentications : Create a new authentication.
     *
     * @param authenticationDTO the authenticationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authenticationDTO, or with status 400 (Bad Request) if the authentication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authentications")
    @Timed
    public ResponseEntity<AuthenticationDTO> createAuthentication(@RequestBody AuthenticationDTO authenticationDTO) throws URISyntaxException {
        log.debug("REST request to save Authentication : {}", authenticationDTO);
        if (authenticationDTO.getId() != null) {
            throw new BadRequestAlertException("A new authentication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthenticationDTO result = authenticationService.save(authenticationDTO);
        return ResponseEntity.created(new URI("/api/authentications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authentications : Updates an existing authentication.
     *
     * @param authenticationDTO the authenticationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authenticationDTO,
     * or with status 400 (Bad Request) if the authenticationDTO is not valid,
     * or with status 500 (Internal Server Error) if the authenticationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authentications")
    @Timed
    public ResponseEntity<AuthenticationDTO> updateAuthentication(@RequestBody AuthenticationDTO authenticationDTO) throws URISyntaxException {
        log.debug("REST request to update Authentication : {}", authenticationDTO);
        if (authenticationDTO.getId() == null) {
            return createAuthentication(authenticationDTO);
        }
        AuthenticationDTO result = authenticationService.save(authenticationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authenticationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authentications : get all the authentications.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of authentications in body
     */
    @GetMapping("/authentications")
    @Timed
    public ResponseEntity<List<AuthenticationDTO>> getAllAuthentications(AuthenticationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Authentications by criteria: {}", criteria);
        Page<AuthenticationDTO> page = authenticationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authentications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authentications/:id : get the "id" authentication.
     *
     * @param id the id of the authenticationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authenticationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/authentications/{id}")
    @Timed
    public ResponseEntity<AuthenticationDTO> getAuthentication(@PathVariable Long id) {
        log.debug("REST request to get Authentication : {}", id);
        AuthenticationDTO authenticationDTO = authenticationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authenticationDTO));
    }

    /**
     * DELETE  /authentications/:id : delete the "id" authentication.
     *
     * @param id the id of the authenticationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authentications/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthentication(@PathVariable Long id) {
        log.debug("REST request to delete Authentication : {}", id);
        authenticationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
