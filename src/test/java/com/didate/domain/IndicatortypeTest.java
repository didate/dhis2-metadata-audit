package com.didate.domain;

import static com.didate.domain.IndicatortypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicatortypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicatortype.class);
        Indicatortype indicatortype1 = getIndicatortypeSample1();
        Indicatortype indicatortype2 = new Indicatortype();
        assertThat(indicatortype1).isNotEqualTo(indicatortype2);

        indicatortype2.setId(indicatortype1.getId());
        assertThat(indicatortype1).isEqualTo(indicatortype2);

        indicatortype2 = getIndicatortypeSample2();
        assertThat(indicatortype1).isNotEqualTo(indicatortype2);
    }
}
