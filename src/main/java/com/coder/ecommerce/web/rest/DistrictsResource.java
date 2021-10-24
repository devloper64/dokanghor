package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.DistrictsService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.DistrictsDTO;
import com.coder.ecommerce.service.dto.DistrictsCriteria;
import com.coder.ecommerce.service.DistrictsQueryService;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.Districts}.
 */
@RestController
@RequestMapping("/api")
public class DistrictsResource {

    private final Logger log = LoggerFactory.getLogger(DistrictsResource.class);

    private static final String ENTITY_NAME = "districts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictsService districtsService;

    private final DistrictsQueryService districtsQueryService;

    public DistrictsResource(DistrictsService districtsService, DistrictsQueryService districtsQueryService) {
        this.districtsService = districtsService;
        this.districtsQueryService = districtsQueryService;
    }

    /**
     * {@code POST  /districts} : Create a new districts.
     *
     * @param districtsDTO the districtsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtsDTO, or with status {@code 400 (Bad Request)} if the districts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<DistrictsDTO> createDistricts(@Valid @RequestBody DistrictsDTO districtsDTO) throws URISyntaxException {
        log.debug("REST request to save Districts : {}", districtsDTO);
        if (districtsDTO.getId() != null) {
            throw new BadRequestAlertException("A new districts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictsDTO result = districtsService.save(districtsDTO);
        return ResponseEntity.created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /districts} : Updates an existing districts.
     *
     * @param districtsDTO the districtsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtsDTO,
     * or with status {@code 400 (Bad Request)} if the districtsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts")
    public ResponseEntity<DistrictsDTO> updateDistricts(@Valid @RequestBody DistrictsDTO districtsDTO) throws URISyntaxException {
        log.debug("REST request to update Districts : {}", districtsDTO);
        if (districtsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DistrictsDTO result = districtsService.save(districtsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, districtsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictsDTO>> getAllDistricts(DistrictsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Districts by criteria: {}", criteria);
        Page<DistrictsDTO> page = districtsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /districts/count} : count all the districts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/districts/count")
    public ResponseEntity<Long> countDistricts(DistrictsCriteria criteria) {
        log.debug("REST request to count Districts by criteria: {}", criteria);
        return ResponseEntity.ok().body(districtsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /districts/:id} : get the "id" districts.
     *
     * @param id the id of the districtsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<DistrictsDTO> getDistricts(@PathVariable Long id) {
        log.debug("REST request to get Districts : {}", id);
        Optional<DistrictsDTO> districtsDTO = districtsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtsDTO);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" districts.
     *
     * @param id the id of the districtsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistricts(@PathVariable Long id) {
        log.debug("REST request to delete Districts : {}", id);
        districtsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
