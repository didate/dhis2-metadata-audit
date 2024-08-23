package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.ProgramIndicatorTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProgramIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramIndicator.class);
        ProgramIndicator programIndicator1 = getProgramIndicatorSample1();
        ProgramIndicator programIndicator2 = new ProgramIndicator();
        assertThat(programIndicator1).isNotEqualTo(programIndicator2);

        programIndicator2.setId(programIndicator1.getId());
        assertThat(programIndicator1).isEqualTo(programIndicator2);

        programIndicator2 = getProgramIndicatorSample2();
        assertThat(programIndicator1).isNotEqualTo(programIndicator2);
    }

    @Test
    void createdByTest() {
        ProgramIndicator programIndicator = getProgramIndicatorRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programIndicator.setCreatedBy(dHISUserBack);
        assertThat(programIndicator.getCreatedBy()).isEqualTo(dHISUserBack);

        programIndicator.createdBy(null);
        assertThat(programIndicator.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        ProgramIndicator programIndicator = getProgramIndicatorRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        programIndicator.setLastUpdatedBy(dHISUserBack);
        assertThat(programIndicator.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        programIndicator.lastUpdatedBy(null);
        assertThat(programIndicator.getLastUpdatedBy()).isNull();
    }

    @Test
    void programTest() {
        ProgramIndicator programIndicator = getProgramIndicatorRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programIndicator.setProgram(programBack);
        assertThat(programIndicator.getProgram()).isEqualTo(programBack);

        programIndicator.program(null);
        assertThat(programIndicator.getProgram()).isNull();
    }

    @Test
    void programTest() {
        ProgramIndicator programIndicator = getProgramIndicatorRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        programIndicator.addProgram(programBack);
        assertThat(programIndicator.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getProgramIndicators()).containsOnly(programIndicator);

        programIndicator.removeProgram(programBack);
        assertThat(programIndicator.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getProgramIndicators()).doesNotContain(programIndicator);

        programIndicator.programs(new HashSet<>(Set.of(programBack)));
        assertThat(programIndicator.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getProgramIndicators()).containsOnly(programIndicator);

        programIndicator.setPrograms(new HashSet<>());
        assertThat(programIndicator.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getProgramIndicators()).doesNotContain(programIndicator);
    }
}
