package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.didate.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganisationUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationUnit.class);
        OrganisationUnit organisationUnit1 = new OrganisationUnit();
        organisationUnit1.setId("id1");
        OrganisationUnit organisationUnit2 = new OrganisationUnit();
        organisationUnit2.setId(organisationUnit1.getId());
        assertThat(organisationUnit1).isEqualTo(organisationUnit2);
        organisationUnit2.setId("id2");
        assertThat(organisationUnit1).isNotEqualTo(organisationUnit2);
        organisationUnit1.setId(null);
        assertThat(organisationUnit1).isNotEqualTo(organisationUnit2);
    }
}
