package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDetailsMapperTest {

    private ProductDetailsMapper productDetailsMapper;

    @BeforeEach
    public void setUp() {
        productDetailsMapper = new ProductDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productDetailsMapper.fromId(null)).isNull();
    }
}
