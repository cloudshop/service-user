package com.eyun.user.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyun.user.service.DeliveryAddressService;
import com.eyun.user.web.rest.errors.BadRequestAlertException;
import com.eyun.user.web.rest.util.HeaderUtil;
import com.eyun.user.service.dto.DeliveryAddressDTO;
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
 * REST controller for managing DeliveryAddress.
 */
@RestController
@RequestMapping("/api")
public class DeliveryAddressResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryAddressResource.class);

    private static final String ENTITY_NAME = "deliveryAddress";

    private final DeliveryAddressService deliveryAddressService;

    public DeliveryAddressResource(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    /**
     * POST  /delivery-addresses : Create a new deliveryAddress.
     *
     * @param deliveryAddressDTO the deliveryAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryAddressDTO, or with status 400 (Bad Request) if the deliveryAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-addresses")
    @Timed
    public ResponseEntity<DeliveryAddressDTO> createDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryAddress : {}", deliveryAddressDTO);
        if (deliveryAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryAddressDTO result = deliveryAddressService.save(deliveryAddressDTO);
        return ResponseEntity.created(new URI("/api/delivery-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-addresses : Updates an existing deliveryAddress.
     *
     * @param deliveryAddressDTO the deliveryAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryAddressDTO,
     * or with status 400 (Bad Request) if the deliveryAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the deliveryAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-addresses")
    @Timed
    public ResponseEntity<DeliveryAddressDTO> updateDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryAddress : {}", deliveryAddressDTO);
        if (deliveryAddressDTO.getId() == null) {
            return createDeliveryAddress(deliveryAddressDTO);
        }
        DeliveryAddressDTO result = deliveryAddressService.save(deliveryAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-addresses : get all the deliveryAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryAddresses in body
     */
    @GetMapping("/delivery-addresses")
    @Timed
    public List<DeliveryAddressDTO> getAllDeliveryAddresses() {
        log.debug("REST request to get all DeliveryAddresses");
        return deliveryAddressService.findAll();
        }

    /**
     * GET  /delivery-addresses/:id : get the "id" deliveryAddress.
     *
     * @param id the id of the deliveryAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-addresses/{id}")
    @Timed
    public ResponseEntity<DeliveryAddressDTO> getDeliveryAddress(@PathVariable Long id) {
        log.debug("REST request to get DeliveryAddress : {}", id);
        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deliveryAddressDTO));
    }

    /**
     * DELETE  /delivery-addresses/:id : delete the "id" deliveryAddress.
     *
     * @param id the id of the deliveryAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliveryAddress(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryAddress : {}", id);
        deliveryAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
