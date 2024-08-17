package com.didate.domain;

import static com.didate.domain.OptionsetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionsetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Optionset.class);
        Optionset optionset1 = getOptionsetSample1();
        Optionset optionset2 = new Optionset();
        assertThat(optionset1).isNotEqualTo(optionset2);

        optionset2.setId(optionset1.getId());
        assertThat(optionset1).isEqualTo(optionset2);

        optionset2 = getOptionsetSample2();
        assertThat(optionset1).isNotEqualTo(optionset2);
    }
}
