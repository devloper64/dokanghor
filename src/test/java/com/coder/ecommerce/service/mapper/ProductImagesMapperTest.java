package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImagesMapperTest {

    private ProductImagesMapper productImagesMapper;

    @BeforeEach
    public void setUp() {
        productImagesMapper = new ProductImagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productImagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productImagesMapper.fromId(null)).isNull();
    }
}
