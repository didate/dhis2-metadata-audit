package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatasetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSet.class);
        DataSet dataset1 = new DataSet();
        dataset1.setId("id1");
        DataSet dataset2 = new DataSet();
        dataset2.setId(dataset1.getId());
        assertThat(dataset1).isEqualTo(dataset2);
        dataset2.setId("id2");
        assertThat(dataset1).isNotEqualTo(dataset2);
        dataset1.setId(null);
        assertThat(dataset1).isNotEqualTo(dataset2);
    }
}
