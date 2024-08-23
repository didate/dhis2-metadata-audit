package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrackedEntityAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackedEntityAttribute.class);
        TrackedEntityAttribute trackedEntityAttribute1 = new TrackedEntityAttribute();
        trackedEntityAttribute1.setId("id1");
        TrackedEntityAttribute trackedEntityAttribute2 = new TrackedEntityAttribute();
        trackedEntityAttribute2.setId(trackedEntityAttribute1.getId());
        assertThat(trackedEntityAttribute1).isEqualTo(trackedEntityAttribute2);
        trackedEntityAttribute2.setId("id2");
        assertThat(trackedEntityAttribute1).isNotEqualTo(trackedEntityAttribute2);
        trackedEntityAttribute1.setId(null);
        assertThat(trackedEntityAttribute1).isNotEqualTo(trackedEntityAttribute2);
    }
}
