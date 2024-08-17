package com.didate.domain;

import static com.didate.domain.DHISUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DHISUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DHISUser.class);
        DHISUser dHISUser1 = getDHISUserSample1();
        DHISUser dHISUser2 = new DHISUser();
        assertThat(dHISUser1).isNotEqualTo(dHISUser2);

        dHISUser2.setId(dHISUser1.getId());
        assertThat(dHISUser1).isEqualTo(dHISUser2);

        dHISUser2 = getDHISUserSample2();
        assertThat(dHISUser1).isNotEqualTo(dHISUser2);
    }
}
