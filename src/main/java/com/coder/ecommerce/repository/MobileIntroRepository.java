package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.MobileIntro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MobileIntro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileIntroRepository extends JpaRepository<MobileIntro, Long> {
}
