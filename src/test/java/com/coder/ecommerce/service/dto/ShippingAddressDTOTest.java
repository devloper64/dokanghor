package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class ShippingAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingAddressDTO.class);
        ShippingAddressDTO shippingAddressDTO1 = new ShippingAddressDTO();
        shippingAddressDTO1.setId(1L);
        ShippingAddressDTO shippingAddressDTO2 = new ShippingAddressDTO();
        assertThat(shippingAddressDTO1).isNotEqualTo(shippingAddressDTO2);
        shippingAddressDTO2.setId(shippingAddressDTO1.getId());
        assertThat(shippingAddressDTO1).isEqualTo(shippingAddressDTO2);
        shippingAddressDTO2.setId(2L);
        assertThat(shippingAddressDTO1).isNotEqualTo(shippingAddressDTO2);
        shippingAddressDTO1.setId(null);
        assertThat(shippingAddressDTO1).isNotEqualTo(shippingAddressDTO2);
    }
}
