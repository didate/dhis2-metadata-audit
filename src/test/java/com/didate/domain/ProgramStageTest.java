package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramStageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramStage.class);
        ProgramStage programStage1 = new ProgramStage();
        programStage1.setId("id1");
        ProgramStage programStage2 = new ProgramStage();
        programStage2.setId(programStage1.getId());
        assertThat(programStage1).isEqualTo(programStage2);
        programStage2.setId("id2");
        assertThat(programStage1).isNotEqualTo(programStage2);
        programStage1.setId(null);
        assertThat(programStage1).isNotEqualTo(programStage2);
    }
}
