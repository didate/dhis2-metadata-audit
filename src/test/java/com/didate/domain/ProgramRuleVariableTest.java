package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DataelementTestSamples.*;
import static com.didate.domain.ProgramRuleVariableTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static com.didate.domain.TrackedEntityAttributeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramRuleVariableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramRuleVariable.class);
        ProgramRuleVariable programRuleVariable1 = getProgramRuleVariableSample1();
        ProgramRuleVariable programRuleVariable2 = new ProgramRuleVariable();
        assertThat(programRuleVariable1).isNotEqualTo(programRuleVariable2);

        programRuleVariable2.setId(programRuleVariable1.getId());
        assertThat(programRuleVariable1).isEqualTo(programRuleVariable2);

        programRuleVariable2 = getProgramRuleVariableSample2();
        assertThat(programRuleVariable1).isNotEqualTo(programRuleVariable2);
    }

    @Test
    void projectTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        programRuleVariable.setProject(projectBack);
        assertThat(programRuleVariable.getProject()).isEqualTo(projectBack);

        programRuleVariable.project(null);
        assertThat(programRuleVariable.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRuleVariable.setCreatedBy(dHISUserBack);
        assertThat(programRuleVariable.getCreatedBy()).isEqualTo(dHISUserBack);

        programRuleVariable.createdBy(null);
        assertThat(programRuleVariable.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programRuleVariable.setLastUpdatedBy(dHISUserBack);
        assertThat(programRuleVariable.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        programRuleVariable.lastUpdatedBy(null);
        assertThat(programRuleVariable.getLastUpdatedBy()).isNull();
    }

    @Test
    void programTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programRuleVariable.setProgram(programBack);
        assertThat(programRuleVariable.getProgram()).isEqualTo(programBack);

        programRuleVariable.program(null);
        assertThat(programRuleVariable.getProgram()).isNull();
    }

    @Test
    void trackedEntityAttributeTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        TrackedEntityAttribute trackedEntityAttributeBack = getTrackedEntityAttributeRandomSampleGenerator();

        programRuleVariable.setTrackedEntityAttribute(trackedEntityAttributeBack);
        assertThat(programRuleVariable.getTrackedEntityAttribute()).isEqualTo(trackedEntityAttributeBack);

        programRuleVariable.trackedEntityAttribute(null);
        assertThat(programRuleVariable.getTrackedEntityAttribute()).isNull();
    }

    @Test
    void dataElementTest() {
        ProgramRuleVariable programRuleVariable = getProgramRuleVariableRandomSampleGenerator();
        Dataelement dataelementBack = getDataelementRandomSampleGenerator();

        programRuleVariable.setDataElement(dataelementBack);
        assertThat(programRuleVariable.getDataElement()).isEqualTo(dataelementBack);

        programRuleVariable.dataElement(null);
        assertThat(programRuleVariable.getDataElement()).isNull();
    }
}
