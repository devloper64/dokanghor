package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DivisionsMapperTest {

    private DivisionsMapper divisionsMapper;

    @BeforeEach
    public void setUp() {
        divisionsMapper = new DivisionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(divisionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(divisionsMapper.fromId(null)).isNull();
    }
}
