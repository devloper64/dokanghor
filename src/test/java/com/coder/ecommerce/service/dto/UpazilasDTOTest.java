package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class UpazilasDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpazilasDTO.class);
        UpazilasDTO upazilasDTO1 = new UpazilasDTO();
        upazilasDTO1.setId(1L);
        UpazilasDTO upazilasDTO2 = new UpazilasDTO();
        assertThat(upazilasDTO1).isNotEqualTo(upazilasDTO2);
        upazilasDTO2.setId(upazilasDTO1.getId());
        assertThat(upazilasDTO1).isEqualTo(upazilasDTO2);
        upazilasDTO2.setId(2L);
        assertThat(upazilasDTO1).isNotEqualTo(upazilasDTO2);
        upazilasDTO1.setId(null);
        assertThat(upazilasDTO1).isNotEqualTo(upazilasDTO2);
    }
}
