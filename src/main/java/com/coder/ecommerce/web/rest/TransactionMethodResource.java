package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.TransactionMethodService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.TransactionMethodDTO;
import com.coder.ecommerce.service.dto.TransactionMethodCriteria;
import com.coder.ecommerce.service.TransactionMethodQueryService;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.TransactionMethod}.
 */
@RestController
@RequestMapping("/api")
public class TransactionMethodResource {

    private final Logger log = LoggerFactory.getLogger(TransactionMethodResource.class);

    private static final String ENTITY_NAME = "transactionMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionMethodService transactionMethodService;

    private final TransactionMethodQueryService transactionMethodQueryService;

    public TransactionMethodResource(TransactionMethodService transactionMethodService, TransactionMethodQueryService transactionMethodQueryService) {
        this.transactionMethodService = transactionMethodService;
        this.transactionMethodQueryService = transactionMethodQueryService;
    }

    /**
     * {@code POST  /transaction-methods} : Create a new transactionMethod.
     *
     * @param transactionMethodDTO the transactionMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionMethodDTO, or with status {@code 400 (Bad Request)} if the transactionMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-methods")
    public ResponseEntity<TransactionMethodDTO> createTransactionMethod(@Valid @RequestBody TransactionMethodDTO transactionMethodDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionMethod : {}", transactionMethodDTO);
        if (transactionMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionMethodDTO result = transactionMethodService.save(transactionMethodDTO);
        return ResponseEntity.created(new URI("/api/transaction-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-methods} : Updates an existing transactionMethod.
     *
     * @param transactionMethodDTO the transactionMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionMethodDTO,
     * or with status {@code 400 (Bad Request)} if the transactionMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-methods")
    public ResponseEntity<TransactionMethodDTO> updateTransactionMethod(@Valid @RequestBody TransactionMethodDTO transactionMethodDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionMethod : {}", transactionMethodDTO);
        if (transactionMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionMethodDTO result = transactionMethodService.save(transactionMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-methods} : get all the transactionMethods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionMethods in body.
     */
    @GetMapping("/transaction-methods")
    public ResponseEntity<List<TransactionMethodDTO>> getAllTransactionMethods(TransactionMethodCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TransactionMethods by criteria: {}", criteria);
        Page<TransactionMethodDTO> page = transactionMethodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-methods/count} : count all the transactionMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-methods/count")
    public ResponseEntity<Long> countTransactionMethods(TransactionMethodCriteria criteria) {
        log.debug("REST request to count TransactionMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-methods/:id} : get the "id" transactionMethod.
     *
     * @param id the id of the transactionMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-methods/{id}")
    public ResponseEntity<TransactionMethodDTO> getTransactionMethod(@PathVariable Long id) {
        log.debug("REST request to get TransactionMethod : {}", id);
        Optional<TransactionMethodDTO> transactionMethodDTO = transactionMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionMethodDTO);
    }

    /**
     * {@code DELETE  /transaction-methods/:id} : delete the "id" transactionMethod.
     *
     * @param id the id of the transactionMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-methods/{id}")
    public ResponseEntity<Void> deleteTransactionMethod(@PathVariable Long id) {
        log.debug("REST request to delete TransactionMethod : {}", id);
        transactionMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
