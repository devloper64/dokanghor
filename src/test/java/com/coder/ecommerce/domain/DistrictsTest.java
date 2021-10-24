package com.coder.ecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.coder.ecommerce.web.rest.TestUtil;

public class DistrictsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Districts.class);
        Districts districts1 = new Districts();
        districts1.setId(1L);
        Districts districts2 = new Districts();
        districts2.setId(districts1.getId());
        assertThat(districts1).isEqualTo(districts2);
        districts2.setId(2L);
        assertThat(districts1).isNotEqualTo(districts2);
        districts1.setId(null);
        assertThat(districts1).isNotEqualTo(districts2);
    }
}
