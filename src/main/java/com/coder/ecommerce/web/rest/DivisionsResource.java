package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.DivisionsService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.DivisionsDTO;
import com.coder.ecommerce.service.dto.DivisionsCriteria;
import com.coder.ecommerce.service.DivisionsQueryService;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.Divisions}.
 */
@RestController
@RequestMapping("/api")
public class DivisionsResource {

    private final Logger log = LoggerFactory.getLogger(DivisionsResource.class);

    private static final String ENTITY_NAME = "divisions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DivisionsService divisionsService;

    private final DivisionsQueryService divisionsQueryService;

    public DivisionsResource(DivisionsService divisionsService, DivisionsQueryService divisionsQueryService) {
        this.divisionsService = divisionsService;
        this.divisionsQueryService = divisionsQueryService;
    }

    /**
     * {@code POST  /divisions} : Create a new divisions.
     *
     * @param divisionsDTO the divisionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new divisionsDTO, or with status {@code 400 (Bad Request)} if the divisions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/divisions")
    public ResponseEntity<DivisionsDTO> createDivisions(@Valid @RequestBody DivisionsDTO divisionsDTO) throws URISyntaxException {
        log.debug("REST request to save Divisions : {}", divisionsDTO);
        if (divisionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new divisions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DivisionsDTO result = divisionsService.save(divisionsDTO);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /divisions} : Updates an existing divisions.
     *
     * @param divisionsDTO the divisionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisionsDTO,
     * or with status {@code 400 (Bad Request)} if the divisionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the divisionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/divisions")
    public ResponseEntity<DivisionsDTO> updateDivisions(@Valid @RequestBody DivisionsDTO divisionsDTO) throws URISyntaxException {
        log.debug("REST request to update Divisions : {}", divisionsDTO);
        if (divisionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DivisionsDTO result = divisionsService.save(divisionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, divisionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /divisions} : get all the divisions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of divisions in body.
     */
    @GetMapping("/divisions")
    public ResponseEntity<List<DivisionsDTO>> getAllDivisions(DivisionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Divisions by criteria: {}", criteria);
        Page<DivisionsDTO> page = divisionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /divisions/count} : count all the divisions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/divisions/count")
    public ResponseEntity<Long> countDivisions(DivisionsCriteria criteria) {
        log.debug("REST request to count Divisions by criteria: {}", criteria);
        return ResponseEntity.ok().body(divisionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /divisions/:id} : get the "id" divisions.
     *
     * @param id the id of the divisionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the divisionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/divisions/{id}")
    public ResponseEntity<DivisionsDTO> getDivisions(@PathVariable Long id) {
        log.debug("REST request to get Divisions : {}", id);
        Optional<DivisionsDTO> divisionsDTO = divisionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(divisionsDTO);
    }

    /**
     * {@code DELETE  /divisions/:id} : delete the "id" divisions.
     *
     * @param id the id of the divisionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/divisions/{id}")
    public ResponseEntity<Void> deleteDivisions(@PathVariable Long id) {
        log.debug("REST request to delete Divisions : {}", id);
        divisionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
