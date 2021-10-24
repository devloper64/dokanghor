package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.DivisionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.Divisions}.
 */
public interface DivisionsService {

    /**
     * Save a divisions.
     *
     * @param divisionsDTO the entity to save.
     * @return the persisted entity.
     */
    DivisionsDTO save(DivisionsDTO divisionsDTO);

    /**
     * Get all the divisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DivisionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" divisions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DivisionsDTO> findOne(Long id);

    /**
     * Delete the "id" divisions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
