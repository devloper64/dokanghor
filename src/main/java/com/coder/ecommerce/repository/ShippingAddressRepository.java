package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.ShippingAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShippingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long>, JpaSpecificationExecutor<ShippingAddress> {
}
