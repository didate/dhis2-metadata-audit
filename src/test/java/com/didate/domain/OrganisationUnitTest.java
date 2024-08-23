package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DatasetTestSamples.*;
import static com.didate.domain.OrganisationUnitTestSamples.*;
import static com.didate.domain.ProgramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrganisationUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationUnit.class);
        OrganisationUnit organisationUnit1 = getOrganisationUnitSample1();
        OrganisationUnit organisationUnit2 = new OrganisationUnit();
        assertThat(organisationUnit1).isNotEqualTo(organisationUnit2);

        organisationUnit2.setId(organisationUnit1.getId());
        assertThat(organisationUnit1).isEqualTo(organisationUnit2);

        organisationUnit2 = getOrganisationUnitSample2();
        assertThat(organisationUnit1).isNotEqualTo(organisationUnit2);
    }

    @Test
    void createdByTest() {
        OrganisationUnit organisationUnit = getOrganisationUnitRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        organisationUnit.setCreatedBy(dHISUserBack);
        assertThat(organisationUnit.getCreatedBy()).isEqualTo(dHISUserBack);

        organisationUnit.createdBy(null);
        assertThat(organisationUnit.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        OrganisationUnit organisationUnit = getOrganisationUnitRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        organisationUnit.setLastUpdatedBy(dHISUserBack);
        assertThat(organisationUnit.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        organisationUnit.lastUpdatedBy(null);
        assertThat(organisationUnit.getLastUpdatedBy()).isNull();
    }

    @Test
    void programTest() {
        OrganisationUnit organisationUnit = getOrganisationUnitRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        organisationUnit.addProgram(programBack);
        assertThat(organisationUnit.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getOrganisationUnits()).containsOnly(organisationUnit);

        organisationUnit.removeProgram(programBack);
        assertThat(organisationUnit.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getOrganisationUnits()).doesNotContain(organisationUnit);

        organisationUnit.programs(new HashSet<>(Set.of(programBack)));
        assertThat(organisationUnit.getPrograms()).containsOnly(programBack);
        assertThat(programBack.getOrganisationUnits()).containsOnly(organisationUnit);

        organisationUnit.setPrograms(new HashSet<>());
        assertThat(organisationUnit.getPrograms()).doesNotContain(programBack);
        assertThat(programBack.getOrganisationUnits()).doesNotContain(organisationUnit);
    }

    @Test
    void datasetTest() {
        OrganisationUnit organisationUnit = getOrganisationUnitRandomSampleGenerator();
        Dataset datasetBack = getDatasetRandomSampleGenerator();

        organisationUnit.addDataset(datasetBack);
        assertThat(organisationUnit.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getOrganisationUnits()).containsOnly(organisationUnit);

        organisationUnit.removeDataset(datasetBack);
        assertThat(organisationUnit.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getOrganisationUnits()).doesNotContain(organisationUnit);

        organisationUnit.datasets(new HashSet<>(Set.of(datasetBack)));
        assertThat(organisationUnit.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getOrganisationUnits()).containsOnly(organisationUnit);

        organisationUnit.setDatasets(new HashSet<>());
        assertThat(organisationUnit.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getOrganisationUnits()).doesNotContain(organisationUnit);
    }
}
