package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DatasetTestSamples.*;
import static com.didate.domain.IndicatorTestSamples.*;
import static com.didate.domain.IndicatortypeTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicator.class);
        Indicator indicator1 = getIndicatorSample1();
        Indicator indicator2 = new Indicator();
        assertThat(indicator1).isNotEqualTo(indicator2);

        indicator2.setId(indicator1.getId());
        assertThat(indicator1).isEqualTo(indicator2);

        indicator2 = getIndicatorSample2();
        assertThat(indicator1).isNotEqualTo(indicator2);
    }

    @Test
    void projectTest() {
        Indicator indicator = getIndicatorRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        indicator.setProject(projectBack);
        assertThat(indicator.getProject()).isEqualTo(projectBack);

        indicator.project(null);
        assertThat(indicator.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        Indicator indicator = getIndicatorRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        indicator.setCreatedBy(dHISUserBack);
        assertThat(indicator.getCreatedBy()).isEqualTo(dHISUserBack);

        indicator.createdBy(null);
        assertThat(indicator.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        Indicator indicator = getIndicatorRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        indicator.setLastUpdatedBy(dHISUserBack);
        assertThat(indicator.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        indicator.lastUpdatedBy(null);
        assertThat(indicator.getLastUpdatedBy()).isNull();
    }

    @Test
    void indicatorTypeTest() {
        Indicator indicator = getIndicatorRandomSampleGenerator();
        Indicatortype indicatortypeBack = getIndicatortypeRandomSampleGenerator();

        indicator.setIndicatorType(indicatortypeBack);
        assertThat(indicator.getIndicatorType()).isEqualTo(indicatortypeBack);

        indicator.indicatorType(null);
        assertThat(indicator.getIndicatorType()).isNull();
    }

    @Test
    void datasetTest() {
        Indicator indicator = getIndicatorRandomSampleGenerator();
        Dataset datasetBack = getDatasetRandomSampleGenerator();

        indicator.addDataset(datasetBack);
        assertThat(indicator.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getIndicators()).containsOnly(indicator);

        indicator.removeDataset(datasetBack);
        assertThat(indicator.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getIndicators()).doesNotContain(indicator);

        indicator.datasets(new HashSet<>(Set.of(datasetBack)));
        assertThat(indicator.getDatasets()).containsOnly(datasetBack);
        assertThat(datasetBack.getIndicators()).containsOnly(indicator);

        indicator.setDatasets(new HashSet<>());
        assertThat(indicator.getDatasets()).doesNotContain(datasetBack);
        assertThat(datasetBack.getIndicators()).doesNotContain(indicator);
    }
}
