package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UpazilasMapperTest {

    private UpazilasMapper upazilasMapper;

    @BeforeEach
    public void setUp() {
        upazilasMapper = new UpazilasMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(upazilasMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(upazilasMapper.fromId(null)).isNull();
    }
}
