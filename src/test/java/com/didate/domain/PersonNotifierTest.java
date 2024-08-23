package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonNotifierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonNotifier.class);
        PersonNotifier personNotifier1 = new PersonNotifier();
        personNotifier1.setId(1L);
        PersonNotifier personNotifier2 = new PersonNotifier();
        personNotifier2.setId(personNotifier1.getId());
        assertThat(personNotifier1).isEqualTo(personNotifier2);
        personNotifier2.setId(2L);
        assertThat(personNotifier1).isNotEqualTo(personNotifier2);
        personNotifier1.setId(null);
        assertThat(personNotifier1).isNotEqualTo(personNotifier2);
    }
}
