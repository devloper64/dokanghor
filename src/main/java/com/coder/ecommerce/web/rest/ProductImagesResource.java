package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.ProductImagesService;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.ProductImagesDTO;

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
 * REST controller for managing {@link com.coder.ecommerce.domain.ProductImages}.
 */
@RestController
@RequestMapping("/api")
public class ProductImagesResource {

    private final Logger log = LoggerFactory.getLogger(ProductImagesResource.class);

    private static final String ENTITY_NAME = "productImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductImagesService productImagesService;

    public ProductImagesResource(ProductImagesService productImagesService) {
        this.productImagesService = productImagesService;
    }

    /**
     * {@code POST  /product-images} : Create a new productImages.
     *
     * @param productImagesDTO the productImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productImagesDTO, or with status {@code 400 (Bad Request)} if the productImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-images")
    public ResponseEntity<ProductImagesDTO> createProductImages(@Valid @RequestBody ProductImagesDTO productImagesDTO) throws URISyntaxException {
        log.debug("REST request to save ProductImages : {}", productImagesDTO);
        if (productImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new productImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductImagesDTO result = productImagesService.save(productImagesDTO);
        return ResponseEntity.created(new URI("/api/product-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-images} : Updates an existing productImages.
     *
     * @param productImagesDTO the productImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productImagesDTO,
     * or with status {@code 400 (Bad Request)} if the productImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-images")
    public ResponseEntity<ProductImagesDTO> updateProductImages(@Valid @RequestBody ProductImagesDTO productImagesDTO) throws URISyntaxException {
        log.debug("REST request to update ProductImages : {}", productImagesDTO);
        if (productImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductImagesDTO result = productImagesService.save(productImagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-images} : get all the productImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productImages in body.
     */
    @GetMapping("/product-images")
    public ResponseEntity<List<ProductImagesDTO>> getAllProductImages(Pageable pageable) {
        log.debug("REST request to get a page of ProductImages");
        Page<ProductImagesDTO> page = productImagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/product_images_by_product_id/{product_id}")
    public List<ProductImagesDTO> getProductImagesByProductId(@PathVariable  long product_id) {
        log.debug("REST request to get a page of ProductImages");
        List<ProductImagesDTO> items = productImagesService.findByProductId(product_id);
        return items;
    }


    /**
     * {@code GET  /product-images/:id} : get the "id" productImages.
     *
     * @param id the id of the productImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-images/{id}")
    public ResponseEntity<ProductImagesDTO> getProductImages(@PathVariable Long id) {
        log.debug("REST request to get ProductImages : {}", id);
        Optional<ProductImagesDTO> productImagesDTO = productImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productImagesDTO);
    }

    /**
     * {@code DELETE  /product-images/:id} : delete the "id" productImages.
     *
     * @param id the id of the productImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-images/{id}")
    public ResponseEntity<Void> deleteProductImages(@PathVariable Long id) {
        log.debug("REST request to delete ProductImages : {}", id);
        productImagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
