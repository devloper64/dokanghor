package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.TransactionMethod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionMethodRepository extends JpaRepository<TransactionMethod, Long>, JpaSpecificationExecutor<TransactionMethod> {
}
