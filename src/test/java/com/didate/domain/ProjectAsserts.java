package com.didate.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectAllPropertiesEquals(Project expected, Project actual) {
        assertProjectAutoGeneratedPropertiesEquals(expected, actual);
        assertProjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectAllUpdatablePropertiesEquals(Project expected, Project actual) {
        assertProjectUpdatableFieldsEquals(expected, actual);
        assertProjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectAutoGeneratedPropertiesEquals(Project expected, Project actual) {
        assertThat(expected)
            .as("Verify Project auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectUpdatableFieldsEquals(Project expected, Project actual) {
        assertThat(expected)
            .as("Verify Project relevant properties")
            .satisfies(e -> assertThat(e.getProjectName()).as("check projectName").isEqualTo(actual.getProjectName()))
            .satisfies(e -> assertThat(e.getDhis2URL()).as("check dhis2URL").isEqualTo(actual.getDhis2URL()))
            .satisfies(e -> assertThat(e.getDhis2Version()).as("check dhis2Version").isEqualTo(actual.getDhis2Version()))
            .satisfies(e -> assertThat(e.getToken()).as("check token").isEqualTo(actual.getToken()))
            .satisfies(e -> assertThat(e.getEmailNotification()).as("check emailNotification").isEqualTo(actual.getEmailNotification()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectUpdatableRelationshipsEquals(Project expected, Project actual) {}
}
