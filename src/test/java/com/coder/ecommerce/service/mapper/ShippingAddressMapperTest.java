package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingAddressMapperTest {

    private ShippingAddressMapper shippingAddressMapper;

    @BeforeEach
    public void setUp() {
        shippingAddressMapper = new ShippingAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shippingAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shippingAddressMapper.fromId(null)).isNull();
    }
}
