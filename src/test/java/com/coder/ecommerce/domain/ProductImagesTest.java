package com.coder.ecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class ProductImagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImages.class);
        ProductImages productImages1 = new ProductImages();
        productImages1.setId(1L);
        ProductImages productImages2 = new ProductImages();
        productImages2.setId(productImages1.getId());
        assertThat(productImages1).isEqualTo(productImages2);
        productImages2.setId(2L);
        assertThat(productImages1).isNotEqualTo(productImages2);
        productImages1.setId(null);
        assertThat(productImages1).isNotEqualTo(productImages2);
    }
}
