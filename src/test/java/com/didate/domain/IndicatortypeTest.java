package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicatortypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicatorType.class);
        IndicatorType indicatortype1 = new IndicatorType();
        indicatortype1.setId("id1");
        IndicatorType indicatortype2 = new IndicatorType();
        indicatortype2.setId(indicatortype1.getId());
        assertThat(indicatortype1).isEqualTo(indicatortype2);
        indicatortype2.setId("id2");
        assertThat(indicatortype1).isNotEqualTo(indicatortype2);
        indicatortype1.setId(null);
        assertThat(indicatortype1).isNotEqualTo(indicatortype2);
    }
}
