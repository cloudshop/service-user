package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.OwnerTypeService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.OwnerTypeDTO;
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
 * REST controller for managing OwnerType.
 */
@RestController
@RequestMapping("/api")
public class OwnerTypeResource {

    private final Logger log = LoggerFactory.getLogger(OwnerTypeResource.class);

    private static final String ENTITY_NAME = "ownerType";

    private final OwnerTypeService ownerTypeService;

    public OwnerTypeResource(OwnerTypeService ownerTypeService) {
        this.ownerTypeService = ownerTypeService;
    }

    /**
     * POST  /owner-types : Create a new ownerType.
     *
     * @param ownerTypeDTO the ownerTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ownerTypeDTO, or with status 400 (Bad Request) if the ownerType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-types")
    @Timed
    public ResponseEntity<OwnerTypeDTO> createOwnerType(@RequestBody OwnerTypeDTO ownerTypeDTO) throws URISyntaxException {
        log.debug("REST request to save OwnerType : {}", ownerTypeDTO);
        if (ownerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ownerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerTypeDTO result = ownerTypeService.save(ownerTypeDTO);
        return ResponseEntity.created(new URI("/api/owner-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-types : Updates an existing ownerType.
     *
     * @param ownerTypeDTO the ownerTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ownerTypeDTO,
     * or with status 400 (Bad Request) if the ownerTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the ownerTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-types")
    @Timed
    public ResponseEntity<OwnerTypeDTO> updateOwnerType(@RequestBody OwnerTypeDTO ownerTypeDTO) throws URISyntaxException {
        log.debug("REST request to update OwnerType : {}", ownerTypeDTO);
        if (ownerTypeDTO.getId() == null) {
            return createOwnerType(ownerTypeDTO);
        }
        OwnerTypeDTO result = ownerTypeService.save(ownerTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ownerTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-types : get all the ownerTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ownerTypes in body
     */
    @GetMapping("/owner-types")
    @Timed
    public List<OwnerTypeDTO> getAllOwnerTypes() {
        log.debug("REST request to get all OwnerTypes");
        return ownerTypeService.findAll();
        }

    /**
     * GET  /owner-types/:id : get the "id" ownerType.
     *
     * @param id the id of the ownerTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ownerTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-types/{id}")
    @Timed
    public ResponseEntity<OwnerTypeDTO> getOwnerType(@PathVariable Long id) {
        log.debug("REST request to get OwnerType : {}", id);
        OwnerTypeDTO ownerTypeDTO = ownerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ownerTypeDTO));
    }

    /**
     * DELETE  /owner-types/:id : delete the "id" ownerType.
     *
     * @param id the id of the ownerTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwnerType(@PathVariable Long id) {
        log.debug("REST request to delete OwnerType : {}", id);
        ownerTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
