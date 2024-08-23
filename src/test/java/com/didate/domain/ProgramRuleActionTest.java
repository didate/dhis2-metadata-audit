package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.OptionGroupTestSamples.*;
import static com.didate.domain.ProgramRuleActionTestSamples.*;
import static com.didate.domain.ProgramRuleTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static com.didate.domain.TrackedEntityAttributeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRuleAction.class);
        ProgramRuleAction programRuleAction1 = getProgramRuleActionSample1();
        ProgramRuleAction programRuleAction2 = new ProgramRuleAction();
        assertThat(programRuleAction1).isNotEqualTo(programRuleAction2);

        programRuleAction2.setId(programRuleAction1.getId());
        assertThat(programRuleAction1).isEqualTo(programRuleAction2);

        programRuleAction2 = getProgramRuleActionSample2();
        assertThat(programRuleAction1).isNotEqualTo(programRuleAction2);
    }

    @Test
    void projectTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        programRuleAction.setProject(projectBack);
        assertThat(programRuleAction.getProject()).isEqualTo(projectBack);

        programRuleAction.project(null);
        assertThat(programRuleAction.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRuleAction.setCreatedBy(dHISUserBack);
        assertThat(programRuleAction.getCreatedBy()).isEqualTo(dHISUserBack);

        programRuleAction.createdBy(null);
        assertThat(programRuleAction.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRuleAction.setLastUpdatedBy(dHISUserBack);
        assertThat(programRuleAction.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        programRuleAction.lastUpdatedBy(null);
        assertThat(programRuleAction.getLastUpdatedBy()).isNull();
    }

    @Test
    void programRuleTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        ProgramRule programRuleBack = getProgramRuleRandomSampleGenerator();

        programRuleAction.setProgramRule(programRuleBack);
        assertThat(programRuleAction.getProgramRule()).isEqualTo(programRuleBack);

        programRuleAction.programRule(null);
        assertThat(programRuleAction.getProgramRule()).isNull();
    }

    @Test
    void trackedEntityAttributeTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        TrackedEntityAttribute trackedEntityAttributeBack = getTrackedEntityAttributeRandomSampleGenerator();

        programRuleAction.setTrackedEntityAttribute(trackedEntityAttributeBack);
        assertThat(programRuleAction.getTrackedEntityAttribute()).isEqualTo(trackedEntityAttributeBack);

        programRuleAction.trackedEntityAttribute(null);
        assertThat(programRuleAction.getTrackedEntityAttribute()).isNull();
    }

    @Test
    void dataElementTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        TrackedEntityAttribute trackedEntityAttributeBack = getTrackedEntityAttributeRandomSampleGenerator();

        programRuleAction.setDataElement(trackedEntityAttributeBack);
        assertThat(programRuleAction.getDataElement()).isEqualTo(trackedEntityAttributeBack);

        programRuleAction.dataElement(null);
        assertThat(programRuleAction.getDataElement()).isNull();
    }

    @Test
    void optionGroupTest() {
        ProgramRuleAction programRuleAction = getProgramRuleActionRandomSampleGenerator();
        OptionGroup optionGroupBack = getOptionGroupRandomSampleGenerator();

        programRuleAction.setOptionGroup(optionGroupBack);
        assertThat(programRuleAction.getOptionGroup()).isEqualTo(optionGroupBack);

        programRuleAction.optionGroup(null);
        assertThat(programRuleAction.getOptionGroup()).isNull();
    }
}
