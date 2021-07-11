package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.ProductImagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.ProductImages}.
 */
public interface ProductImagesService {

    /**
     * Save a productImages.
     *
     * @param productImagesDTO the entity to save.
     * @return the persisted entity.
     */
    ProductImagesDTO save(ProductImagesDTO productImagesDTO);

    /**
     * Get all the productImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductImagesDTO> findAll(Pageable pageable);

    List<ProductImagesDTO> findByProductId(Long id);
    /**
     * Get the "id" productImages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductImagesDTO> findOne(Long id);

    /**
     * Delete the "id" productImages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
