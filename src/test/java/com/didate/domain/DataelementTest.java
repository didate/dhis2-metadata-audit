package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataelementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dataelement.class);
        Dataelement dataelement1 = new Dataelement();
        dataelement1.setId("id1");
        Dataelement dataelement2 = new Dataelement();
        dataelement2.setId(dataelement1.getId());
        assertThat(dataelement1).isEqualTo(dataelement2);
        dataelement2.setId("id2");
        assertThat(dataelement1).isNotEqualTo(dataelement2);
        dataelement1.setId(null);
        assertThat(dataelement1).isNotEqualTo(dataelement2);
    }
}
