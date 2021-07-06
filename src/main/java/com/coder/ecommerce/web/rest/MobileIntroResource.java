package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.MobileIntroService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.MobileIntroDTO;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.MobileIntro}.
 */
@RestController
@RequestMapping("/api")
public class MobileIntroResource {

    private final Logger log = LoggerFactory.getLogger(MobileIntroResource.class);

    private static final String ENTITY_NAME = "mobileIntro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MobileIntroService mobileIntroService;

    public MobileIntroResource(MobileIntroService mobileIntroService) {
        this.mobileIntroService = mobileIntroService;
    }

    /**
     * {@code POST  /mobile-intros} : Create a new mobileIntro.
     *
     * @param mobileIntroDTO the mobileIntroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobileIntroDTO, or with status {@code 400 (Bad Request)} if the mobileIntro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mobile-intros")
    public ResponseEntity<MobileIntroDTO> createMobileIntro(@Valid @RequestBody MobileIntroDTO mobileIntroDTO) throws URISyntaxException {
        log.debug("REST request to save MobileIntro : {}", mobileIntroDTO);
        if (mobileIntroDTO.getId() != null) {
            throw new BadRequestAlertException("A new mobileIntro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MobileIntroDTO result = mobileIntroService.save(mobileIntroDTO);
        return ResponseEntity.created(new URI("/api/mobile-intros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mobile-intros} : Updates an existing mobileIntro.
     *
     * @param mobileIntroDTO the mobileIntroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileIntroDTO,
     * or with status {@code 400 (Bad Request)} if the mobileIntroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobileIntroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mobile-intros")
    public ResponseEntity<MobileIntroDTO> updateMobileIntro(@Valid @RequestBody MobileIntroDTO mobileIntroDTO) throws URISyntaxException {
        log.debug("REST request to update MobileIntro : {}", mobileIntroDTO);
        if (mobileIntroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MobileIntroDTO result = mobileIntroService.save(mobileIntroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mobileIntroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mobile-intros} : get all the mobileIntros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobileIntros in body.
     */
    @GetMapping("/getMobileIntros")
    public ResponseEntity<List<MobileIntroDTO>> getAllMobileIntros(Pageable pageable) {
        log.debug("REST request to get a page of MobileIntros");
        Page<MobileIntroDTO> page = mobileIntroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mobile-intros/:id} : get the "id" mobileIntro.
     *
     * @param id the id of the mobileIntroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobileIntroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mobile-intros/{id}")
    public ResponseEntity<MobileIntroDTO> getMobileIntro(@PathVariable Long id) {
        log.debug("REST request to get MobileIntro : {}", id);
        Optional<MobileIntroDTO> mobileIntroDTO = mobileIntroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mobileIntroDTO);
    }

    /**
     * {@code DELETE  /mobile-intros/:id} : delete the "id" mobileIntro.
     *
     * @param id the id of the mobileIntroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mobile-intros/{id}")
    public ResponseEntity<Void> deleteMobileIntro(@PathVariable Long id) {
        log.debug("REST request to delete MobileIntro : {}", id);
        mobileIntroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
