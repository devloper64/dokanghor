package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.Upazilas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Upazilas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpazilasRepository extends JpaRepository<Upazilas, Long>, JpaSpecificationExecutor<Upazilas> {
}
