package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.ShippingAddressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.coder.ecommerce.domain.ShippingAddress}.
 */
public interface ShippingAddressService {

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddressDTO the entity to save.
     * @return the persisted entity.
     */
    ShippingAddressDTO save(ShippingAddressDTO shippingAddressDTO);

    /**
     * Get all the shippingAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShippingAddressDTO> findAll(Pageable pageable);


    /**
     * Get the "id" shippingAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingAddressDTO> findOne(Long id);

    /**
     * Delete the "id" shippingAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
