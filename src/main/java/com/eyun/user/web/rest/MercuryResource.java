package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.MercuryService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.web.rest.util.PaginationUtil;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.dto.MercuryCriteria;
import com.eyun.user.service.MercuryQueryService;
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
import java.util.stream.StreamSupport;

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
}
