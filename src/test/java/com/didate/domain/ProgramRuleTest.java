package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.ProgramRuleTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRule.class);
        ProgramRule programRule1 = getProgramRuleSample1();
        ProgramRule programRule2 = new ProgramRule();
        assertThat(programRule1).isNotEqualTo(programRule2);

        programRule2.setId(programRule1.getId());
        assertThat(programRule1).isEqualTo(programRule2);

        programRule2 = getProgramRuleSample2();
        assertThat(programRule1).isNotEqualTo(programRule2);
    }

    @Test
    void projectTest() {
        ProgramRule programRule = getProgramRuleRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        programRule.setProject(projectBack);
        assertThat(programRule.getProject()).isEqualTo(projectBack);

        programRule.project(null);
        assertThat(programRule.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        ProgramRule programRule = getProgramRuleRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRule.setCreatedBy(dHISUserBack);
        assertThat(programRule.getCreatedBy()).isEqualTo(dHISUserBack);

        programRule.createdBy(null);
        assertThat(programRule.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        ProgramRule programRule = getProgramRuleRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRule.setLastUpdatedBy(dHISUserBack);
        assertThat(programRule.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        programRule.lastUpdatedBy(null);
        assertThat(programRule.getLastUpdatedBy()).isNull();
    }

    @Test
    void programTest() {
        ProgramRule programRule = getProgramRuleRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programRule.setProgram(programBack);
        assertThat(programRule.getProgram()).isEqualTo(programBack);

        programRule.program(null);
        assertThat(programRule.getProgram()).isNull();
    }
}
