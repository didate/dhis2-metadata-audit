package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.DatasetTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatasetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dataset.class);
        Dataset dataset1 = getDatasetSample1();
        Dataset dataset2 = new Dataset();
        assertThat(dataset1).isNotEqualTo(dataset2);

        dataset2.setId(dataset1.getId());
        assertThat(dataset1).isEqualTo(dataset2);

        dataset2 = getDatasetSample2();
        assertThat(dataset1).isNotEqualTo(dataset2);
    }

    @Test
    void projectTest() {
        Dataset dataset = getDatasetRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        dataset.setProject(projectBack);
        assertThat(dataset.getProject()).isEqualTo(projectBack);

        dataset.project(null);
        assertThat(dataset.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        Dataset dataset = getDatasetRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        dataset.setCreatedBy(dHISUserBack);
        assertThat(dataset.getCreatedBy()).isEqualTo(dHISUserBack);

        dataset.createdBy(null);
        assertThat(dataset.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        Dataset dataset = getDatasetRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        dataset.setLastUpdatedBy(dHISUserBack);
        assertThat(dataset.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        dataset.lastUpdatedBy(null);
        assertThat(dataset.getLastUpdatedBy()).isNull();
    }
}
