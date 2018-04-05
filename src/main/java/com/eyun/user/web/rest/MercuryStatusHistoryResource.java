package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.MercuryStatusHistoryService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
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
 * REST controller for managing MercuryStatusHistory.
 */
@RestController
@RequestMapping("/api")
public class MercuryStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusHistoryResource.class);

    private static final String ENTITY_NAME = "mercuryStatusHistory";

    private final MercuryStatusHistoryService mercuryStatusHistoryService;

    public MercuryStatusHistoryResource(MercuryStatusHistoryService mercuryStatusHistoryService) {
        this.mercuryStatusHistoryService = mercuryStatusHistoryService;
    }

    /**
     * POST  /mercury-status-histories : Create a new mercuryStatusHistory.
     *
     * @param mercuryStatusHistoryDTO the mercuryStatusHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mercuryStatusHistoryDTO, or with status 400 (Bad Request) if the mercuryStatusHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mercury-status-histories")
    @Timed
    public ResponseEntity<MercuryStatusHistoryDTO> createMercuryStatusHistory(@RequestBody MercuryStatusHistoryDTO mercuryStatusHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save MercuryStatusHistory : {}", mercuryStatusHistoryDTO);
        if (mercuryStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new mercuryStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MercuryStatusHistoryDTO result = mercuryStatusHistoryService.save(mercuryStatusHistoryDTO);
        return ResponseEntity.created(new URI("/api/mercury-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mercury-status-histories : Updates an existing mercuryStatusHistory.
     *
     * @param mercuryStatusHistoryDTO the mercuryStatusHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mercuryStatusHistoryDTO,
     * or with status 400 (Bad Request) if the mercuryStatusHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the mercuryStatusHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mercury-status-histories")
    @Timed
    public ResponseEntity<MercuryStatusHistoryDTO> updateMercuryStatusHistory(@RequestBody MercuryStatusHistoryDTO mercuryStatusHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update MercuryStatusHistory : {}", mercuryStatusHistoryDTO);
        if (mercuryStatusHistoryDTO.getId() == null) {
            return createMercuryStatusHistory(mercuryStatusHistoryDTO);
        }
        MercuryStatusHistoryDTO result = mercuryStatusHistoryService.save(mercuryStatusHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mercuryStatusHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mercury-status-histories : get all the mercuryStatusHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mercuryStatusHistories in body
     */
    @GetMapping("/mercury-status-histories")
    @Timed
    public List<MercuryStatusHistoryDTO> getAllMercuryStatusHistories() {
        log.debug("REST request to get all MercuryStatusHistories");
        return mercuryStatusHistoryService.findAll();
        }

    /**
     * GET  /mercury-status-histories/:id : get the "id" mercuryStatusHistory.
     *
     * @param id the id of the mercuryStatusHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mercuryStatusHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mercury-status-histories/{id}")
    @Timed
    public ResponseEntity<MercuryStatusHistoryDTO> getMercuryStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get MercuryStatusHistory : {}", id);
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = mercuryStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mercuryStatusHistoryDTO));
    }

    /**
     * DELETE  /mercury-status-histories/:id : delete the "id" mercuryStatusHistory.
     *
     * @param id the id of the mercuryStatusHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mercury-status-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMercuryStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete MercuryStatusHistory : {}", id);
        mercuryStatusHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
