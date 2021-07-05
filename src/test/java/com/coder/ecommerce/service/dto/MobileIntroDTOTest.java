package com.coder.ecommerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class MobileIntroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobileIntroDTO.class);
        MobileIntroDTO mobileIntroDTO1 = new MobileIntroDTO();
        mobileIntroDTO1.setId(1L);
        MobileIntroDTO mobileIntroDTO2 = new MobileIntroDTO();
        assertThat(mobileIntroDTO1).isNotEqualTo(mobileIntroDTO2);
        mobileIntroDTO2.setId(mobileIntroDTO1.getId());
        assertThat(mobileIntroDTO1).isEqualTo(mobileIntroDTO2);
        mobileIntroDTO2.setId(2L);
        assertThat(mobileIntroDTO1).isNotEqualTo(mobileIntroDTO2);
        mobileIntroDTO1.setId(null);
        assertThat(mobileIntroDTO1).isNotEqualTo(mobileIntroDTO2);
    }
}
