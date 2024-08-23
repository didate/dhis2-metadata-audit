package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static com.didate.domain.OptionsetTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static com.didate.domain.TrackedEntityAttributeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrackedEntityAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackedEntityAttribute.class);
        TrackedEntityAttribute trackedEntityAttribute1 = getTrackedEntityAttributeSample1();
        TrackedEntityAttribute trackedEntityAttribute2 = new TrackedEntityAttribute();
        assertThat(trackedEntityAttribute1).isNotEqualTo(trackedEntityAttribute2);

        trackedEntityAttribute2.setId(trackedEntityAttribute1.getId());
        assertThat(trackedEntityAttribute1).isEqualTo(trackedEntityAttribute2);

        trackedEntityAttribute2 = getTrackedEntityAttributeSample2();
        assertThat(trackedEntityAttribute1).isNotEqualTo(trackedEntityAttribute2);
    }

    @Test
    void projectTest() {
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttributeRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        trackedEntityAttribute.setProject(projectBack);
        assertThat(trackedEntityAttribute.getProject()).isEqualTo(projectBack);

        trackedEntityAttribute.project(null);
        assertThat(trackedEntityAttribute.getProject()).isNull();
    }

    @Test
    void createdByTest() {
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttributeRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        trackedEntityAttribute.setCreatedBy(dHISUserBack);
        assertThat(trackedEntityAttribute.getCreatedBy()).isEqualTo(dHISUserBack);

        trackedEntityAttribute.createdBy(null);
        assertThat(trackedEntityAttribute.getCreatedBy()).isNull();
    }

    @Test
    void lastUpdatedByTest() {
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttributeRandomSampleGenerator();
        DHISUser dHISUserBack = getDHISUserRandomSampleGenerator();

        trackedEntityAttribute.setLastUpdatedBy(dHISUserBack);
        assertThat(trackedEntityAttribute.getLastUpdatedBy()).isEqualTo(dHISUserBack);

        trackedEntityAttribute.lastUpdatedBy(null);
        assertThat(trackedEntityAttribute.getLastUpdatedBy()).isNull();
    }

    @Test
    void optionSetTest() {
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttributeRandomSampleGenerator();
        Optionset optionsetBack = getOptionsetRandomSampleGenerator();

        trackedEntityAttribute.setOptionSet(optionsetBack);
        assertThat(trackedEntityAttribute.getOptionSet()).isEqualTo(optionsetBack);

        trackedEntityAttribute.optionSet(null);
        assertThat(trackedEntityAttribute.getOptionSet()).isNull();
    }
}
