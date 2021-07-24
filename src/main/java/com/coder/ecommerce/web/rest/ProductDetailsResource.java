package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.ProductDetailsService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.ProductDetailsDTO;
import com.coder.ecommerce.service.dto.ProductDetailsCriteria;
import com.coder.ecommerce.service.ProductDetailsQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.coder.ecommerce.domain.ProductDetails}.
 */
@RestController
@RequestMapping("/api")
public class ProductDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsResource.class);

    private static final String ENTITY_NAME = "productDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDetailsService productDetailsService;

    private final ProductDetailsQueryService productDetailsQueryService;

    public ProductDetailsResource(ProductDetailsService productDetailsService, ProductDetailsQueryService productDetailsQueryService) {
        this.productDetailsService = productDetailsService;
        this.productDetailsQueryService = productDetailsQueryService;
    }

    /**
     * {@code POST  /product-details} : Create a new productDetails.
     *
     * @param productDetailsDTO the productDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDetailsDTO, or with status {@code 400 (Bad Request)} if the productDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-details")
    public ResponseEntity<ProductDetailsDTO> createProductDetails(@RequestBody ProductDetailsDTO productDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDetails : {}", productDetailsDTO);
        if (productDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity.created(new URI("/api/product-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-details} : Updates an existing productDetails.
     *
     * @param productDetailsDTO the productDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-details")
    public ResponseEntity<ProductDetailsDTO> updateProductDetails(@RequestBody ProductDetailsDTO productDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDetails : {}", productDetailsDTO);
        if (productDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-details} : get all the productDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDetails in body.
     */
    @GetMapping("/product-details")
    public ResponseEntity<List<ProductDetailsDTO>> getAllProductDetails(ProductDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductDetails by criteria: {}", criteria);
        Page<ProductDetailsDTO> page = productDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-details/count} : count all the productDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-details/count")
    public ResponseEntity<Long> countProductDetails(ProductDetailsCriteria criteria) {
        log.debug("REST request to count ProductDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(productDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-details/:id} : get the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/one-product-details/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductDetails : {}", id);
        Optional<ProductDetailsDTO> productDetailsDTO = productDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDetailsDTO);
    }

    /**
     * {@code DELETE  /product-details/:id} : delete the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-details/{id}")
    public ResponseEntity<Void> deleteProductDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductDetails : {}", id);
        productDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
