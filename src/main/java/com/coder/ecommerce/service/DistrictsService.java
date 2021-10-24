package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.DistrictsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.Districts}.
 */
public interface DistrictsService {

    /**
     * Save a districts.
     *
     * @param districtsDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictsDTO save(DistrictsDTO districtsDTO);

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" districts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictsDTO> findOne(Long id);

    /**
     * Delete the "id" districts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
