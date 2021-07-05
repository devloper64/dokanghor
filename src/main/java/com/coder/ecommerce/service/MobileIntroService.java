package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.MobileIntroDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.MobileIntro}.
 */
public interface MobileIntroService {

    /**
     * Save a mobileIntro.
     *
     * @param mobileIntroDTO the entity to save.
     * @return the persisted entity.
     */
    MobileIntroDTO save(MobileIntroDTO mobileIntroDTO);

    /**
     * Get all the mobileIntros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MobileIntroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mobileIntro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobileIntroDTO> findOne(Long id);

    /**
     * Delete the "id" mobileIntro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
