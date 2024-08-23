package com.didate.domain;

import static com.didate.domain.CategorycomboTestSamples.*;
import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DataelementTestSamples.*;
import static com.didate.domain.OrganisationUnitTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Program.class);
        Program program1 = getProgramSample1();
        Program program2 = new Program();
        assertThat(program1).isNotEqualTo(program2);

        program2.setId(program1.getId());
        assertThat(program1).isEqualTo(program2);

        program2 = getProgramSample2();
        assertThat(program1).isNotEqualTo(program2);
    }

    @Test
    void projectTest() {
        Program program = getProgramRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        program.setProject(projectBack);
        assertThat(program.getProject()).isEqualTo(projectBack);

        program.project(null);
        assertThat(program.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        Program program = getProgramRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        program.setCreatedBy(dHISUserBack);
        assertThat(program.getCreatedBy()).isEqualTo(dHISUserBack);

        program.createdBy(null);
        assertThat(program.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        Program program = getProgramRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        program.setLastUpdatedBy(dHISUserBack);
        assertThat(program.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        program.lastUpdatedBy(null);
        assertThat(program.getLastUpdatedBy()).isNull();
    }

    @Test
    void categoryComboTest() {
        Program program = getProgramRandomSampleGenerator();
        Categorycombo categorycomboBack = getCategorycomboRandomSampleGenerator();

        program.setCategoryCombo(categorycomboBack);
        assertThat(program.getCategoryCombo()).isEqualTo(categorycomboBack);

        program.categoryCombo(null);
        assertThat(program.getCategoryCombo()).isNull();
    }

    @Test
    void dataElementsTest() {
        Program program = getProgramRandomSampleGenerator();
        Dataelement dataelementBack = getDataelementRandomSampleGenerator();

        program.addDataElements(dataelementBack);
        assertThat(program.getDataElements()).containsOnly(dataelementBack);

        program.removeDataElements(dataelementBack);
        assertThat(program.getDataElements()).doesNotContain(dataelementBack);

        program.dataElements(new HashSet<>(Set.of(dataelementBack)));
        assertThat(program.getDataElements()).containsOnly(dataelementBack);

        program.setDataElements(new HashSet<>());
        assertThat(program.getDataElements()).doesNotContain(dataelementBack);
    }

    @Test
    void organisationUnitsTest() {
        Program program = getProgramRandomSampleGenerator();
        OrganisationUnit organisationUnitBack = getOrganisationUnitRandomSampleGenerator();

        program.addOrganisationUnits(organisationUnitBack);
        assertThat(program.getOrganisationUnits()).containsOnly(organisationUnitBack);

        program.removeOrganisationUnits(organisationUnitBack);
        assertThat(program.getOrganisationUnits()).doesNotContain(organisationUnitBack);

        program.organisationUnits(new HashSet<>(Set.of(organisationUnitBack)));
        assertThat(program.getOrganisationUnits()).containsOnly(organisationUnitBack);

        program.setOrganisationUnits(new HashSet<>());
        assertThat(program.getOrganisationUnits()).doesNotContain(organisationUnitBack);
    }
}
