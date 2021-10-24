package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.UpazilasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.Upazilas}.
 */
public interface UpazilasService {

    /**
     * Save a upazilas.
     *
     * @param upazilasDTO the entity to save.
     * @return the persisted entity.
     */
    UpazilasDTO save(UpazilasDTO upazilasDTO);

    /**
     * Get all the upazilas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpazilasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" upazilas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UpazilasDTO> findOne(Long id);

    /**
     * Delete the "id" upazilas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
