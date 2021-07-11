package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class ProductImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImagesDTO.class);
        ProductImagesDTO productImagesDTO1 = new ProductImagesDTO();
        productImagesDTO1.setId(1L);
        ProductImagesDTO productImagesDTO2 = new ProductImagesDTO();
        assertThat(productImagesDTO1).isNotEqualTo(productImagesDTO2);
        productImagesDTO2.setId(productImagesDTO1.getId());
        assertThat(productImagesDTO1).isEqualTo(productImagesDTO2);
        productImagesDTO2.setId(2L);
        assertThat(productImagesDTO1).isNotEqualTo(productImagesDTO2);
        productImagesDTO1.setId(null);
        assertThat(productImagesDTO1).isNotEqualTo(productImagesDTO2);
    }
}
