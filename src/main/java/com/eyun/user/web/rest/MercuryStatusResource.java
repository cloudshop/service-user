package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.MercuryStatusService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.MercuryStatusDTO;
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
 * REST controller for managing MercuryStatus.
 */
@RestController
@RequestMapping("/api")
public class MercuryStatusResource {

    private final Logger log = LoggerFactory.getLogger(MercuryStatusResource.class);

    private static final String ENTITY_NAME = "mercuryStatus";

    private final MercuryStatusService mercuryStatusService;

    public MercuryStatusResource(MercuryStatusService mercuryStatusService) {
        this.mercuryStatusService = mercuryStatusService;
    }

    /**
     * POST  /mercury-statuses : Create a new mercuryStatus.
     *
     * @param mercuryStatusDTO the mercuryStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mercuryStatusDTO, or with status 400 (Bad Request) if the mercuryStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mercury-statuses")
    @Timed
    public ResponseEntity<MercuryStatusDTO> createMercuryStatus(@RequestBody MercuryStatusDTO mercuryStatusDTO) throws URISyntaxException {
        log.debug("REST request to save MercuryStatus : {}", mercuryStatusDTO);
        if (mercuryStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new mercuryStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MercuryStatusDTO result = mercuryStatusService.save(mercuryStatusDTO);
        return ResponseEntity.created(new URI("/api/mercury-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mercury-statuses : Updates an existing mercuryStatus.
     *
     * @param mercuryStatusDTO the mercuryStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mercuryStatusDTO,
     * or with status 400 (Bad Request) if the mercuryStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the mercuryStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mercury-statuses")
    @Timed
    public ResponseEntity<MercuryStatusDTO> updateMercuryStatus(@RequestBody MercuryStatusDTO mercuryStatusDTO) throws URISyntaxException {
        log.debug("REST request to update MercuryStatus : {}", mercuryStatusDTO);
        if (mercuryStatusDTO.getId() == null) {
            return createMercuryStatus(mercuryStatusDTO);
        }
        MercuryStatusDTO result = mercuryStatusService.save(mercuryStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mercuryStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mercury-statuses : get all the mercuryStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mercuryStatuses in body
     */
    @GetMapping("/mercury-statuses")
    @Timed
    public List<MercuryStatusDTO> getAllMercuryStatuses() {
        log.debug("REST request to get all MercuryStatuses");
        return mercuryStatusService.findAll();
        }

    /**
     * GET  /mercury-statuses/:id : get the "id" mercuryStatus.
     *
     * @param id the id of the mercuryStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mercuryStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mercury-statuses/{id}")
    @Timed
    public ResponseEntity<MercuryStatusDTO> getMercuryStatus(@PathVariable Long id) {
        log.debug("REST request to get MercuryStatus : {}", id);
        MercuryStatusDTO mercuryStatusDTO = mercuryStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mercuryStatusDTO));
    }

    /**
     * DELETE  /mercury-statuses/:id : delete the "id" mercuryStatus.
     *
     * @param id the id of the mercuryStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mercury-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteMercuryStatus(@PathVariable Long id) {
        log.debug("REST request to delete MercuryStatus : {}", id);
        mercuryStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
