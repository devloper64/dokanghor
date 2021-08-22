package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class TransactionMethodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionMethodDTO.class);
        TransactionMethodDTO transactionMethodDTO1 = new TransactionMethodDTO();
        transactionMethodDTO1.setId(1L);
        TransactionMethodDTO transactionMethodDTO2 = new TransactionMethodDTO();
        assertThat(transactionMethodDTO1).isNotEqualTo(transactionMethodDTO2);
        transactionMethodDTO2.setId(transactionMethodDTO1.getId());
        assertThat(transactionMethodDTO1).isEqualTo(transactionMethodDTO2);
        transactionMethodDTO2.setId(2L);
        assertThat(transactionMethodDTO1).isNotEqualTo(transactionMethodDTO2);
        transactionMethodDTO1.setId(null);
        assertThat(transactionMethodDTO1).isNotEqualTo(transactionMethodDTO2);
    }
}
