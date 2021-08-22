package com.coder.ecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class TransactionMethodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionMethod.class);
        TransactionMethod transactionMethod1 = new TransactionMethod();
        transactionMethod1.setId(1L);
        TransactionMethod transactionMethod2 = new TransactionMethod();
        transactionMethod2.setId(transactionMethod1.getId());
        assertThat(transactionMethod1).isEqualTo(transactionMethod2);
        transactionMethod2.setId(2L);
        assertThat(transactionMethod1).isNotEqualTo(transactionMethod2);
        transactionMethod1.setId(null);
        assertThat(transactionMethod1).isNotEqualTo(transactionMethod2);
    }
}
