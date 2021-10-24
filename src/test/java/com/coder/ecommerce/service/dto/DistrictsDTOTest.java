package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class DistrictsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictsDTO.class);
        DistrictsDTO districtsDTO1 = new DistrictsDTO();
        districtsDTO1.setId(1L);
        DistrictsDTO districtsDTO2 = new DistrictsDTO();
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
        districtsDTO2.setId(districtsDTO1.getId());
        assertThat(districtsDTO1).isEqualTo(districtsDTO2);
        districtsDTO2.setId(2L);
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
        districtsDTO1.setId(null);
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
    }
}
