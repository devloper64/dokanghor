package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.TransactionMethodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.TransactionMethod}.
 */
public interface TransactionMethodService {

    /**
     * Save a transactionMethod.
     *
     * @param transactionMethodDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionMethodDTO save(TransactionMethodDTO transactionMethodDTO);

    /**
     * Get all the transactionMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionMethodDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transactionMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionMethodDTO> findOne(Long id);

    /**
     * Delete the "id" transactionMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
