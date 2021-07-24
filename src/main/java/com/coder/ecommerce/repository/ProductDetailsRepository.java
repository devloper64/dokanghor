package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.ProductDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long>, JpaSpecificationExecutor<ProductDetails> {
}
