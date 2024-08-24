package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorycomboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryCombo.class);
        CategoryCombo categorycombo1 = new CategoryCombo();
        categorycombo1.setId("id1");
        CategoryCombo categorycombo2 = new CategoryCombo();
        categorycombo2.setId(categorycombo1.getId());
        assertThat(categorycombo1).isEqualTo(categorycombo2);
        categorycombo2.setId("id2");
        assertThat(categorycombo1).isNotEqualTo(categorycombo2);
        categorycombo1.setId(null);
        assertThat(categorycombo1).isNotEqualTo(categorycombo2);
    }
}
