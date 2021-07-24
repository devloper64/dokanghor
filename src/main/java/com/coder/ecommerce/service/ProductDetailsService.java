package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.ProductDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.ProductDetails}.
 */
public interface ProductDetailsService {

    /**
     * Save a productDetails.
     *
     * @param productDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDetailsDTO save(ProductDetailsDTO productDetailsDTO);

    /**
     * Get all the productDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductDetailsDTO> findAll(Pageable pageable);
    /**
     * Get all the ProductDetailsDTO where Product is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ProductDetailsDTO> findAllWhereProductIsNull();


    /**
     * Get the "id" productDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" productDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
