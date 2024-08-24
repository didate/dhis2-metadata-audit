package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionsetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionSet.class);
        OptionSet optionset1 = new OptionSet();
        optionset1.setId("id1");
        OptionSet optionset2 = new OptionSet();
        optionset2.setId(optionset1.getId());
        assertThat(optionset1).isEqualTo(optionset2);
        optionset2.setId("id2");
        assertThat(optionset1).isNotEqualTo(optionset2);
        optionset1.setId(null);
        assertThat(optionset1).isNotEqualTo(optionset2);
    }
}
