package com.coder.ecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class MobileIntroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobileIntro.class);
        MobileIntro mobileIntro1 = new MobileIntro();
        mobileIntro1.setId(1L);
        MobileIntro mobileIntro2 = new MobileIntro();
        mobileIntro2.setId(mobileIntro1.getId());
        assertThat(mobileIntro1).isEqualTo(mobileIntro2);
        mobileIntro2.setId(2L);
        assertThat(mobileIntro1).isNotEqualTo(mobileIntro2);
        mobileIntro1.setId(null);
        assertThat(mobileIntro1).isNotEqualTo(mobileIntro2);
    }
}
