package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class ProductDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDetailsDTO.class);
        ProductDetailsDTO productDetailsDTO1 = new ProductDetailsDTO();
        productDetailsDTO1.setId(1L);
        ProductDetailsDTO productDetailsDTO2 = new ProductDetailsDTO();
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
        productDetailsDTO2.setId(productDetailsDTO1.getId());
        assertThat(productDetailsDTO1).isEqualTo(productDetailsDTO2);
        productDetailsDTO2.setId(2L);
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
        productDetailsDTO1.setId(null);
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
    }
}
