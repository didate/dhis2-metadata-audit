package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionGroup.class);
        OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.setId("id1");
        OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.setId(optionGroup1.getId());
        assertThat(optionGroup1).isEqualTo(optionGroup2);
        optionGroup2.setId("id2");
        assertThat(optionGroup1).isNotEqualTo(optionGroup2);
        optionGroup1.setId(null);
        assertThat(optionGroup1).isNotEqualTo(optionGroup2);
    }
}
