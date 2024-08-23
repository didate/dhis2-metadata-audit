package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRule.class);
        ProgramRule programRule1 = new ProgramRule();
        programRule1.setId("id1");
        ProgramRule programRule2 = new ProgramRule();
        programRule2.setId(programRule1.getId());
        assertThat(programRule1).isEqualTo(programRule2);
        programRule2.setId("id2");
        assertThat(programRule1).isNotEqualTo(programRule2);
        programRule1.setId(null);
        assertThat(programRule1).isNotEqualTo(programRule2);
    }
}
