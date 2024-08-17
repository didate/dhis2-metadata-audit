package com.didate.domain;

import static com.didate.domain.PersonNotifierTestSamples.*;
import static com.didate.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonNotifierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonNotifier.class);
        PersonNotifier personNotifier1 = getPersonNotifierSample1();
        PersonNotifier personNotifier2 = new PersonNotifier();
        assertThat(personNotifier1).isNotEqualTo(personNotifier2);

        personNotifier2.setId(personNotifier1.getId());
        assertThat(personNotifier1).isEqualTo(personNotifier2);

        personNotifier2 = getPersonNotifierSample2();
        assertThat(personNotifier1).isNotEqualTo(personNotifier2);
    }

    @Test
    void projectTest() {
        PersonNotifier personNotifier = getPersonNotifierRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        personNotifier.setProject(projectBack);
        assertThat(personNotifier.getProject()).isEqualTo(projectBack);

        personNotifier.project(null);
        assertThat(personNotifier.getProject()).isNull();
    }
}
