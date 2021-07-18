package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.ProductTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductType} and its DTO {@link ProductTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class})
public interface ProductTypeMapper extends EntityMapper<ProductTypeDTO, ProductType> {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    ProductTypeDTO toDto(ProductType productType);


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    @Mapping(source = "subCategoryId", target = "subCategory")
    ProductType toEntity(ProductTypeDTO productTypeDTO);

    default ProductType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductType productType = new ProductType();
        productType.setId(id);
        return productType;
    }
}
