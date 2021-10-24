package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class DivisionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisionsDTO.class);
        DivisionsDTO divisionsDTO1 = new DivisionsDTO();
        divisionsDTO1.setId(1L);
        DivisionsDTO divisionsDTO2 = new DivisionsDTO();
        assertThat(divisionsDTO1).isNotEqualTo(divisionsDTO2);
        divisionsDTO2.setId(divisionsDTO1.getId());
        assertThat(divisionsDTO1).isEqualTo(divisionsDTO2);
        divisionsDTO2.setId(2L);
        assertThat(divisionsDTO1).isNotEqualTo(divisionsDTO2);
        divisionsDTO1.setId(null);
        assertThat(divisionsDTO1).isNotEqualTo(divisionsDTO2);
    }
}
