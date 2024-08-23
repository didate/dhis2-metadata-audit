package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DataelementTestSamples.*;
import static com.didate.domain.ProgramStageTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProgramStageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramStage.class);
        ProgramStage programStage1 = getProgramStageSample1();
        ProgramStage programStage2 = new ProgramStage();
        assertThat(programStage1).isNotEqualTo(programStage2);

        programStage2.setId(programStage1.getId());
        assertThat(programStage1).isEqualTo(programStage2);

        programStage2 = getProgramStageSample2();
        assertThat(programStage1).isNotEqualTo(programStage2);
    }

    @Test
    void createdByTest() {
        ProgramStage programStage = getProgramStageRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programStage.setCreatedBy(dHISUserBack);
        assertThat(programStage.getCreatedBy()).isEqualTo(dHISUserBack);

        programStage.createdBy(null);
        assertThat(programStage.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        ProgramStage programStage = getProgramStageRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programStage.setLastUpdatedBy(dHISUserBack);
        assertThat(programStage.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        programStage.lastUpdatedBy(null);
        assertThat(programStage.getLastUpdatedBy()).isNull();
    }

    @Test
    void programTest() {
        ProgramStage programStage = getProgramStageRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programStage.setProgram(programBack);
        assertThat(programStage.getProgram()).isEqualTo(programBack);

        programStage.program(null);
        assertThat(programStage.getProgram()).isNull();
    }

    @Test
    void programStageDataElementsTest() {
        ProgramStage programStage = getProgramStageRandomSampleGenerator();
        Dataelement dataelementBack = getDataelementRandomSampleGenerator();

        programStage.addProgramStageDataElements(dataelementBack);
        assertThat(programStage.getProgramStageDataElements()).containsOnly(dataelementBack);

        programStage.removeProgramStageDataElements(dataelementBack);
        assertThat(programStage.getProgramStageDataElements()).doesNotContain(dataelementBack);

        programStage.programStageDataElements(new HashSet<>(Set.of(dataelementBack)));
        assertThat(programStage.getProgramStageDataElements()).containsOnly(dataelementBack);

        programStage.setProgramStageDataElements(new HashSet<>());
        assertThat(programStage.getProgramStageDataElements()).doesNotContain(dataelementBack);
    }

    @Test
    void programTest() {
        ProgramStage programStage = getProgramStageRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programStage.addProgram(programBack);
        assertThat(programStage.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getProgramStages()).containsOnly(programStage);

        programStage.removeProgram(programBack);
        assertThat(programStage.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getProgramStages()).doesNotContain(programStage);

        programStage.programs(new HashSet<>(Set.of(programBack)));
        assertThat(programStage.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getProgramStages()).containsOnly(programStage);

        programStage.setPrograms(new HashSet<>());
        assertThat(programStage.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getProgramStages()).doesNotContain(programStage);
    }
}
