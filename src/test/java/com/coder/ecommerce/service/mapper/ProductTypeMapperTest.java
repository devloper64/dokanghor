package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeMapperTest {

    private ProductTypeMapper productTypeMapper;

    @BeforeEach
    public void setUp() {
        productTypeMapper = new ProductTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productTypeMapper.fromId(null)).isNull();
    }
}
