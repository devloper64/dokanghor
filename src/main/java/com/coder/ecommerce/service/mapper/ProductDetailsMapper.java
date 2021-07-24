package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.ProductDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDetails} and its DTO {@link ProductDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductDetailsMapper extends EntityMapper<ProductDetailsDTO, ProductDetails> {


    @Mapping(target = "product", ignore = true)
    ProductDetails toEntity(ProductDetailsDTO productDetailsDTO);

    default ProductDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDetails productDetails = new ProductDetails();
        productDetails.setId(id);
        return productDetails;
    }
}
