package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleVariableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRuleVariable.class);
        ProgramRuleVariable programRuleVariable1 = new ProgramRuleVariable();
        programRuleVariable1.setId("id1");
        ProgramRuleVariable programRuleVariable2 = new ProgramRuleVariable();
        programRuleVariable2.setId(programRuleVariable1.getId());
        assertThat(programRuleVariable1).isEqualTo(programRuleVariable2);
        programRuleVariable2.setId("id2");
        assertThat(programRuleVariable1).isNotEqualTo(programRuleVariable2);
        programRuleVariable1.setId(null);
        assertThat(programRuleVariable1).isNotEqualTo(programRuleVariable2);
    }
}
