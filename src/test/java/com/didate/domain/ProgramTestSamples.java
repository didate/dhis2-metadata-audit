package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Program getProgramSample1() {
        return new Program()
            .id("id1")
            .name("name1")
            .created("created1")
            .lastUpdated("lastUpdated1")
            .shortName("shortName1")
            .description("description1")
            .enrollmentDateLabel("enrollmentDateLabel1")
            .incidentDateLabel("incidentDateLabel1")
            .programType("programType1")
            .userRoles("userRoles1")
            .programIndicators("programIndicators1")
            .programRuleVariables("programRuleVariables1")
            .notificationTemplates("notificationTemplates1")
            .trackedEntityType("trackedEntityType1")
            .style("style1")
            .accessLevel("accessLevel1")
            .displayEnrollmentDateLabel("displayEnrollmentDateLabel1")
            .displayIncidentDateLabel("displayIncidentDateLabel1")
            .displayShortName("displayShortName1")
            .displayDescription("displayDescription1")
            .displayFormName("displayFormName1")
            .displayName("displayName1")
            .attributeValuesCount(1)
            .organisationUnitsCount(1)
            .programStagesCount(1)
            .programSectionsCount(1)
            .programTrackedEntityAttributesCount(1);
    }

    public static Program getProgramSample2() {
        return new Program()
            .id("id2")
            .name("name2")
            .created("created2")
            .lastUpdated("lastUpdated2")
            .shortName("shortName2")
            .description("description2")
            .enrollmentDateLabel("enrollmentDateLabel2")
            .incidentDateLabel("incidentDateLabel2")
            .programType("programType2")
            .userRoles("userRoles2")
            .programIndicators("programIndicators2")
            .programRuleVariables("programRuleVariables2")
            .notificationTemplates("notificationTemplates2")
            .trackedEntityType("trackedEntityType2")
            .style("style2")
            .accessLevel("accessLevel2")
            .displayEnrollmentDateLabel("displayEnrollmentDateLabel2")
            .displayIncidentDateLabel("displayIncidentDateLabel2")
            .displayShortName("displayShortName2")
            .displayDescription("displayDescription2")
            .displayFormName("displayFormName2")
            .displayName("displayName2")
            .attributeValuesCount(2)
            .organisationUnitsCount(2)
            .programStagesCount(2)
            .programSectionsCount(2)
            .programTrackedEntityAttributesCount(2);
    }

    public static Program getProgramRandomSampleGenerator() {
        return new Program()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .created(UUID.randomUUID().toString())
            .lastUpdated(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .enrollmentDateLabel(UUID.randomUUID().toString())
            .incidentDateLabel(UUID.randomUUID().toString())
            .programType(UUID.randomUUID().toString())
            .userRoles(UUID.randomUUID().toString())
            .programIndicators(UUID.randomUUID().toString())
            .programRuleVariables(UUID.randomUUID().toString())
            .notificationTemplates(UUID.randomUUID().toString())
            .trackedEntityType(UUID.randomUUID().toString())
            .style(UUID.randomUUID().toString())
            .accessLevel(UUID.randomUUID().toString())
            .displayEnrollmentDateLabel(UUID.randomUUID().toString())
            .displayIncidentDateLabel(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .displayDescription(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .attributeValuesCount(intCount.incrementAndGet())
            .organisationUnitsCount(intCount.incrementAndGet())
            .programStagesCount(intCount.incrementAndGet())
            .programSectionsCount(intCount.incrementAndGet())
            .programTrackedEntityAttributesCount(intCount.incrementAndGet());
    }
}
