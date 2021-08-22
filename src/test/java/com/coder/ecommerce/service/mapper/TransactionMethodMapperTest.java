package com.coder.ecommerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionMethodMapperTest {

    private TransactionMethodMapper transactionMethodMapper;

    @BeforeEach
    public void setUp() {
        transactionMethodMapper = new TransactionMethodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionMethodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionMethodMapper.fromId(null)).isNull();
    }
}
