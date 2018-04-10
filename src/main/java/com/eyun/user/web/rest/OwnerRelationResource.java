package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.OwnerRelationService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.dto.OwnerRelationCriteria;
import com.eyun.user.service.OwnerRelationQueryService;
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
 * REST controller for managing OwnerRelation.
 */
@RestController
@RequestMapping("/api")
public class OwnerRelationResource {

    private final Logger log = LoggerFactory.getLogger(OwnerRelationResource.class);

    private static final String ENTITY_NAME = "ownerRelation";

    private final OwnerRelationService ownerRelationService;

    private final OwnerRelationQueryService ownerRelationQueryService;

    public OwnerRelationResource(OwnerRelationService ownerRelationService, OwnerRelationQueryService ownerRelationQueryService) {
        this.ownerRelationService = ownerRelationService;
        this.ownerRelationQueryService = ownerRelationQueryService;
    }

    /**
     * POST  /owner-relations : Create a new ownerRelation.
     *
     * @param ownerRelationDTO the ownerRelationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ownerRelationDTO, or with status 400 (Bad Request) if the ownerRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-relations")
    @Timed
    public ResponseEntity<OwnerRelationDTO> createOwnerRelation(@RequestBody OwnerRelationDTO ownerRelationDTO) throws URISyntaxException {
        log.debug("REST request to save OwnerRelation : {}", ownerRelationDTO);
        if (ownerRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new ownerRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerRelationDTO result = ownerRelationService.save(ownerRelationDTO);
        return ResponseEntity.created(new URI("/api/owner-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-relations : Updates an existing ownerRelation.
     *
     * @param ownerRelationDTO the ownerRelationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ownerRelationDTO,
     * or with status 400 (Bad Request) if the ownerRelationDTO is not valid,
     * or with status 500 (Internal Server Error) if the ownerRelationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-relations")
    @Timed
    public ResponseEntity<OwnerRelationDTO> updateOwnerRelation(@RequestBody OwnerRelationDTO ownerRelationDTO) throws URISyntaxException {
        log.debug("REST request to update OwnerRelation : {}", ownerRelationDTO);
        if (ownerRelationDTO.getId() == null) {
            return createOwnerRelation(ownerRelationDTO);
        }
        OwnerRelationDTO result = ownerRelationService.save(ownerRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ownerRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-relations : get all the ownerRelations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ownerRelations in body
     */
    @GetMapping("/owner-relations")
    @Timed
    public ResponseEntity<List<OwnerRelationDTO>> getAllOwnerRelations(OwnerRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OwnerRelations by criteria: {}", criteria);
        Page<OwnerRelationDTO> page = ownerRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-relations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-relations/:id : get the "id" ownerRelation.
     *
     * @param id the id of the ownerRelationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ownerRelationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-relations/{id}")
    @Timed
    public ResponseEntity<OwnerRelationDTO> getOwnerRelation(@PathVariable Long id) {
        log.debug("REST request to get OwnerRelation : {}", id);
        OwnerRelationDTO ownerRelationDTO = ownerRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ownerRelationDTO));
    }

    /**
     * DELETE  /owner-relations/:id : delete the "id" ownerRelation.
     *
     * @param id the id of the ownerRelationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwnerRelation(@PathVariable Long id) {
        log.debug("REST request to delete OwnerRelation : {}", id);
        ownerRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
