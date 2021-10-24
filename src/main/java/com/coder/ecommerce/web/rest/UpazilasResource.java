package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.UpazilasService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.UpazilasDTO;
import com.coder.ecommerce.service.dto.UpazilasCriteria;
import com.coder.ecommerce.service.UpazilasQueryService;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.Upazilas}.
 */
@RestController
@RequestMapping("/api")
public class UpazilasResource {

    private final Logger log = LoggerFactory.getLogger(UpazilasResource.class);

    private static final String ENTITY_NAME = "upazilas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UpazilasService upazilasService;

    private final UpazilasQueryService upazilasQueryService;

    public UpazilasResource(UpazilasService upazilasService, UpazilasQueryService upazilasQueryService) {
        this.upazilasService = upazilasService;
        this.upazilasQueryService = upazilasQueryService;
    }

    /**
     * {@code POST  /upazilas} : Create a new upazilas.
     *
     * @param upazilasDTO the upazilasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new upazilasDTO, or with status {@code 400 (Bad Request)} if the upazilas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upazilas")
    public ResponseEntity<UpazilasDTO> createUpazilas(@Valid @RequestBody UpazilasDTO upazilasDTO) throws URISyntaxException {
        log.debug("REST request to save Upazilas : {}", upazilasDTO);
        if (upazilasDTO.getId() != null) {
            throw new BadRequestAlertException("A new upazilas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpazilasDTO result = upazilasService.save(upazilasDTO);
        return ResponseEntity.created(new URI("/api/upazilas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upazilas} : Updates an existing upazilas.
     *
     * @param upazilasDTO the upazilasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated upazilasDTO,
     * or with status {@code 400 (Bad Request)} if the upazilasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the upazilasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upazilas")
    public ResponseEntity<UpazilasDTO> updateUpazilas(@Valid @RequestBody UpazilasDTO upazilasDTO) throws URISyntaxException {
        log.debug("REST request to update Upazilas : {}", upazilasDTO);
        if (upazilasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UpazilasDTO result = upazilasService.save(upazilasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, upazilasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /upazilas} : get all the upazilas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of upazilas in body.
     */
    @GetMapping("/upazilas")
    public ResponseEntity<List<UpazilasDTO>> getAllUpazilas(UpazilasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Upazilas by criteria: {}", criteria);
        Page<UpazilasDTO> page = upazilasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /upazilas/count} : count all the upazilas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/upazilas/count")
    public ResponseEntity<Long> countUpazilas(UpazilasCriteria criteria) {
        log.debug("REST request to count Upazilas by criteria: {}", criteria);
        return ResponseEntity.ok().body(upazilasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /upazilas/:id} : get the "id" upazilas.
     *
     * @param id the id of the upazilasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the upazilasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upazilas/{id}")
    public ResponseEntity<UpazilasDTO> getUpazilas(@PathVariable Long id) {
        log.debug("REST request to get Upazilas : {}", id);
        Optional<UpazilasDTO> upazilasDTO = upazilasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(upazilasDTO);
    }

    /**
     * {@code DELETE  /upazilas/:id} : delete the "id" upazilas.
     *
     * @param id the id of the upazilasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upazilas/{id}")
    public ResponseEntity<Void> deleteUpazilas(@PathVariable Long id) {
        log.debug("REST request to delete Upazilas : {}", id);
        upazilasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
