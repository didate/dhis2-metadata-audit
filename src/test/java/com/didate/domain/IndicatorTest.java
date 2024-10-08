package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicator.class);
        Indicator indicator1 = new Indicator();
        indicator1.setId("id1");
        Indicator indicator2 = new Indicator();
        indicator2.setId(indicator1.getId());
        assertThat(indicator1).isEqualTo(indicator2);
        indicator2.setId("id2");
        assertThat(indicator1).isNotEqualTo(indicator2);
        indicator1.setId(null);
        assertThat(indicator1).isNotEqualTo(indicator2);
    }
}
