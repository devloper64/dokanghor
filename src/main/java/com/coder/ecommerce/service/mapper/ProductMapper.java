package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class,ProductTypeMapper.class,ProductDetailsMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "subCategory.name", target = "subCategoryName")
    @Mapping(source = "productType.id", target = "productTypeId")
    @Mapping(source = "productDetails.id", target = "productDetailsId")
    ProductDTO toDto(Product product);

    @Mapping(source = "subCategoryId", target = "subCategory")
    @Mapping(source = "productTypeId", target = "productType")
    @Mapping(source = "productDetailsId", target = "productDetails")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
