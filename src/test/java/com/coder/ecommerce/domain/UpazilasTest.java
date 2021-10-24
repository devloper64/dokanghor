package com.coder.ecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class UpazilasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upazilas.class);
        Upazilas upazilas1 = new Upazilas();
        upazilas1.setId(1L);
        Upazilas upazilas2 = new Upazilas();
        upazilas2.setId(upazilas1.getId());
        assertThat(upazilas1).isEqualTo(upazilas2);
        upazilas2.setId(2L);
        assertThat(upazilas1).isNotEqualTo(upazilas2);
        upazilas1.setId(null);
        assertThat(upazilas1).isNotEqualTo(upazilas2);
    }
}
