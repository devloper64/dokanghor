package com.coder.ecommerce.repository;

import com.coder.ecommerce.domain.ProductImages;

import com.coder.ecommerce.service.dto.ProductImagesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProductImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

    List<ProductImages> findByProductId(long id);
}
