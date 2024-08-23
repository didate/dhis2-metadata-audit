package com.didate.domain;

import static com.didate.domain.CategorycomboTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorycomboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorycombo.class);
        Categorycombo categorycombo1 = getCategorycomboSample1();
        Categorycombo categorycombo2 = new Categorycombo();
        assertThat(categorycombo1).isNotEqualTo(categorycombo2);

        categorycombo2.setId(categorycombo1.getId());
        assertThat(categorycombo1).isEqualTo(categorycombo2);

        categorycombo2 = getCategorycomboSample2();
        assertThat(categorycombo1).isNotEqualTo(categorycombo2);
    }
}