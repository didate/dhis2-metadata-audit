package com.didate.domain;

import static com.didate.domain.CategorycomboTestSamples.*;
import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DataelementTestSamples.*;
import static com.didate.domain.DatasetTestSamples.*;
import static com.didate.domain.OptionsetTestSamples.*;
import static com.didate.domain.ProgramStageTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DataelementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dataelement.class);
        Dataelement dataelement1 = getDataelementSample1();
        Dataelement dataelement2 = new Dataelement();
        assertThat(dataelement1).isNotEqualTo(dataelement2);

        dataelement2.setId(dataelement1.getId());
        assertThat(dataelement1).isEqualTo(dataelement2);

        dataelement2 = getDataelementSample2();
        assertThat(dataelement1).isNotEqualTo(dataelement2);
    }

    @Test
    void projectTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        dataelement.setProject(projectBack);
        assertThat(dataelement.getProject()).isEqualTo(projectBack);

        dataelement.project(null);
        assertThat(dataelement.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        dataelement.setCreatedBy(dHISUserBack);
        assertThat(dataelement.getCreatedBy()).isEqualTo(dHISUserBack);

        dataelement.createdBy(null);
        assertThat(dataelement.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        dataelement.setLastUpdatedBy(dHISUserBack);
        assertThat(dataelement.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        dataelement.lastUpdatedBy(null);
        assertThat(dataelement.getLastUpdatedBy()).isNull();
    }

    @Test
    void categoryComboTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        Categorycombo categorycomboBack = getCategorycomboRandomSampleGenerator();

        dataelement.setCategoryCombo(categorycomboBack);
        assertThat(dataelement.getCategoryCombo()).isEqualTo(categorycomboBack);

        dataelement.categoryCombo(null);
        assertThat(dataelement.getCategoryCombo()).isNull();
    }

    @Test
    void optionSetTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        Optionset optionsetBack = getOptionsetRandomSampleGenerator();

        dataelement.setOptionSet(optionsetBack);
        assertThat(dataelement.getOptionSet()).isEqualTo(optionsetBack);

        dataelement.optionSet(null);
        assertThat(dataelement.getOptionSet()).isNull();
    }

    @Test
    void datasetTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        Dataset datasetBack = getDatasetRandomSampleGenerator();

        dataelement.addDataset(datasetBack);
        assertThat(dataelement.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getDataSetElements()).containsOnly(dataelement);

        dataelement.removeDataset(datasetBack);
        assertThat(dataelement.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getDataSetElements()).doesNotContain(dataelement);

        dataelement.datasets(new HashSet<>(Set.of(datasetBack)));
        assertThat(dataelement.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getDataSetElements()).containsOnly(dataelement);

        dataelement.setDatasets(new HashSet<>());
        assertThat(dataelement.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getDataSetElements()).doesNotContain(dataelement);
    }

    @Test
    void programStageTest() {
        Dataelement dataelement = getDataelementRandomSampleGenerator();
        ProgramStage programStageBack = getProgramStageRandomSampleGenerator();

        dataelement.addProgramStage(programStageBack);
        assertThat(dataelement.getProgramStages()).containsOnly(programStageBack);
        assertThat(programStageBack.getProgramStageDataElements()).containsOnly(dataelement);

        dataelement.removeProgramStage(programStageBack);
        assertThat(dataelement.getProgramStages()).doesNotContain(programStageBack);
        assertThat(programStageBack.getProgramStageDataElements()).doesNotContain(dataelement);

        dataelement.programStages(new HashSet<>(Set.of(programStageBack)));
        assertThat(dataelement.getProgramStages()).containsOnly(programStageBack);
        assertThat(programStageBack.getProgramStageDataElements()).containsOnly(dataelement);

        dataelement.setProgramStages(new HashSet<>());
        assertThat(dataelement.getProgramStages()).doesNotContain(programStageBack);
        assertThat(programStageBack.getProgramStageDataElements()).doesNotContain(dataelement);
    }
}
