package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.ShippingAddressService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.ShippingAddressDTO;
import com.coder.ecommerce.service.dto.ShippingAddressCriteria;
import com.coder.ecommerce.service.ShippingAddressQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.coder.ecommerce.domain.ShippingAddress}.
 */
@RestController
@RequestMapping("/api")
public class ShippingAddressResource {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressResource.class);

    private static final String ENTITY_NAME = "shippingAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingAddressService shippingAddressService;

    private final ShippingAddressQueryService shippingAddressQueryService;

    public ShippingAddressResource(ShippingAddressService shippingAddressService, ShippingAddressQueryService shippingAddressQueryService) {
        this.shippingAddressService = shippingAddressService;
        this.shippingAddressQueryService = shippingAddressQueryService;
    }

    /**
     * {@code POST  /shipping-addresses} : Create a new shippingAddress.
     *
     * @param shippingAddressDTO the shippingAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippingAddressDTO, or with status {@code 400 (Bad Request)} if the shippingAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipping-addresses")
    public ResponseEntity<ShippingAddressDTO> createShippingAddress(@Valid @RequestBody ShippingAddressDTO shippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to save ShippingAddress : {}", shippingAddressDTO);
        if (shippingAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new shippingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingAddressDTO result = shippingAddressService.save(shippingAddressDTO);
        return ResponseEntity.created(new URI("/api/shipping-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipping-addresses} : Updates an existing shippingAddress.
     *
     * @param shippingAddressDTO the shippingAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingAddressDTO,
     * or with status {@code 400 (Bad Request)} if the shippingAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippingAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipping-addresses")
    public ResponseEntity<ShippingAddressDTO> updateShippingAddress(@Valid @RequestBody ShippingAddressDTO shippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to update ShippingAddress : {}", shippingAddressDTO);
        if (shippingAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShippingAddressDTO result = shippingAddressService.save(shippingAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shippingAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shipping-addresses} : get all the shippingAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippingAddresses in body.
     */
    @GetMapping("/shipping-addresses")
    public ResponseEntity<List<ShippingAddressDTO>> getAllShippingAddresses(ShippingAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ShippingAddresses by criteria: {}", criteria);
        Page<ShippingAddressDTO> page = shippingAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipping-addresses/count} : count all the shippingAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/shipping-addresses/count")
    public ResponseEntity<Long> countShippingAddresses(ShippingAddressCriteria criteria) {
        log.debug("REST request to count ShippingAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(shippingAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shipping-addresses/:id} : get the "id" shippingAddress.
     *
     * @param id the id of the shippingAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippingAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipping-addresses/{id}")
    public ResponseEntity<ShippingAddressDTO> getShippingAddress(@PathVariable Long id) {
        log.debug("REST request to get ShippingAddress : {}", id);
        Optional<ShippingAddressDTO> shippingAddressDTO = shippingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shippingAddressDTO);
    }

    /**
     * {@code DELETE  /shipping-addresses/:id} : delete the "id" shippingAddress.
     *
     * @param id the id of the shippingAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipping-addresses/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShippingAddress : {}", id);
        shippingAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
