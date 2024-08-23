package com.didate.domain;

import java.util.UUID;

public class ProgramIndicatorTestSamples {

    public static ProgramIndicator getProgramIndicatorSample1() {
        return new ProgramIndicator()
            .id("id1")
            .name("name1")
            .shortName("shortName1")
            .dimensionItemType("dimensionItemType1")
            .expression("expression1")
            .filter("filter1")
            .analyticsType("analyticsType1")
            .dimensionItem("dimensionItem1")
            .displayShortName("displayShortName1")
            .displayName("displayName1")
            .displayFormName("displayFormName1");
    }

    public static ProgramIndicator getProgramIndicatorSample2() {
        return new ProgramIndicator()
            .id("id2")
            .name("name2")
            .shortName("shortName2")
            .dimensionItemType("dimensionItemType2")
            .expression("expression2")
            .filter("filter2")
            .analyticsType("analyticsType2")
            .dimensionItem("dimensionItem2")
            .displayShortName("displayShortName2")
            .displayName("displayName2")
            .displayFormName("displayFormName2");
    }

    public static ProgramIndicator getProgramIndicatorRandomSampleGenerator() {
        return new ProgramIndicator()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .dimensionItemType(UUID.randomUUID().toString())
            .expression(UUID.randomUUID().toString())
            .filter(UUID.randomUUID().toString())
            .analyticsType(UUID.randomUUID().toString())
            .dimensionItem(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString());
    }
}
