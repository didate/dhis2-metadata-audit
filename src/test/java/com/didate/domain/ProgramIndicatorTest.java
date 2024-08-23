package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramIndicator.class);
        ProgramIndicator programIndicator1 = new ProgramIndicator();
        programIndicator1.setId("id1");
        ProgramIndicator programIndicator2 = new ProgramIndicator();
        programIndicator2.setId(programIndicator1.getId());
        assertThat(programIndicator1).isEqualTo(programIndicator2);
        programIndicator2.setId("id2");
        assertThat(programIndicator1).isNotEqualTo(programIndicator2);
        programIndicator1.setId(null);
        assertThat(programIndicator1).isNotEqualTo(programIndicator2);
    }
}
