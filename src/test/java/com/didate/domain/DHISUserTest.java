package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DHISUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DHISUser.class);
        DHISUser dHISUser1 = new DHISUser();
        dHISUser1.setId("id1");
        DHISUser dHISUser2 = new DHISUser();
        dHISUser2.setId(dHISUser1.getId());
        assertThat(dHISUser1).isEqualTo(dHISUser2);
        dHISUser2.setId("id2");
        assertThat(dHISUser1).isNotEqualTo(dHISUser2);
        dHISUser1.setId(null);
        assertThat(dHISUser1).isNotEqualTo(dHISUser2);
    }
}
