package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.ProductImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductImages} and its DTO {@link ProductImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductImagesMapper extends EntityMapper<ProductImagesDTO, ProductImages> {

    @Mapping(source = "product.id", target = "productId")
    ProductImagesDTO toDto(ProductImages productImages);

    @Mapping(source = "productId", target = "product")
    ProductImages toEntity(ProductImagesDTO productImagesDTO);

    default ProductImages fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductImages productImages = new ProductImages();
        productImages.setId(id);
        return productImages;
    }
}
