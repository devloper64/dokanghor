package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.Divisions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Divisions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisionsRepository extends JpaRepository<Divisions, Long>, JpaSpecificationExecutor<Divisions> {
}
