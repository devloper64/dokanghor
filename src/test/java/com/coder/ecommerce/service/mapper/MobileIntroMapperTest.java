package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MobileIntroMapperTest {

    private MobileIntroMapper mobileIntroMapper;

    @BeforeEach
    public void setUp() {
        mobileIntroMapper = new MobileIntroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mobileIntroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mobileIntroMapper.fromId(null)).isNull();
    }
}
