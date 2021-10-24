package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DistrictsMapperTest {

    private DistrictsMapper districtsMapper;

    @BeforeEach
    public void setUp() {
        districtsMapper = new DistrictsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(districtsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(districtsMapper.fromId(null)).isNull();
    }
}
