package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRuleAction.class);
        ProgramRuleAction programRuleAction1 = new ProgramRuleAction();
        programRuleAction1.setId("id1");
        ProgramRuleAction programRuleAction2 = new ProgramRuleAction();
        programRuleAction2.setId(programRuleAction1.getId());
        assertThat(programRuleAction1).isEqualTo(programRuleAction2);
        programRuleAction2.setId("id2");
        assertThat(programRuleAction1).isNotEqualTo(programRuleAction2);
        programRuleAction1.setId(null);
        assertThat(programRuleAction1).isNotEqualTo(programRuleAction2);
    }
}
