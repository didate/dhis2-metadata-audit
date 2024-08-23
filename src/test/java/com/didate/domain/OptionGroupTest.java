package com.didate.domain;

import static com.didate.domain.OptionGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionGroup.class);
        OptionGroup optionGroup1 = getOptionGroupSample1();
        OptionGroup optionGroup2 = new OptionGroup();
        assertThat(optionGroup1).isNotEqualTo(optionGroup2);

        optionGroup2.setId(optionGroup1.getId());
        assertThat(optionGroup1).isEqualTo(optionGroup2);

        optionGroup2 = getOptionGroupSample2();
        assertThat(optionGroup1).isNotEqualTo(optionGroup2);
    }

    @Test
    void hashCodeVerifier() {
        OptionGroup optionGroup = new OptionGroup();
        assertThat(optionGroup.hashCode()).isZero();

        OptionGroup optionGroup1 = getOptionGroupSample1();
        optionGroup.setId(optionGroup1.getId());
        assertThat(optionGroup).hasSameHashCodeAs(optionGroup1);
    }
}
