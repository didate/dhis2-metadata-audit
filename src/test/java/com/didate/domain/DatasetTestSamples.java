package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DatasetTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Dataset getDatasetSample1() {
        return new Dataset()
            .id("id1")
            .name("name1")
            .shortName("shortName1")
            .description("description1")
            .dimensionItemType("dimensionItemType1")
            .periodType("periodType1")
            .mobile("mobile1")
            .notifyCompletingUser("notifyCompletingUser1")
            .formType("formType1")
            .displayName("displayName1")
            .dimensionItem("dimensionItem1")
            .displayShortName("displayShortName1")
            .displayDescription("displayDescription1")
            .displayFormName("displayFormName1")
            .dataSetElementsCount(1)
            .indicatorsCount(1)
            .organisationUnitsCount(1)
            .dataSetElementsContent("dataSetElementsContent1")
            .indicatorsContent("indicatorsContent1")
            .organisationUnitsContent("organisationUnitsContent1");
    }

    public static Dataset getDatasetSample2() {
        return new Dataset()
            .id("id2")
            .name("name2")
            .shortName("shortName2")
            .description("description2")
            .dimensionItemType("dimensionItemType2")
            .periodType("periodType2")
            .mobile("mobile2")
            .notifyCompletingUser("notifyCompletingUser2")
            .formType("formType2")
            .displayName("displayName2")
            .dimensionItem("dimensionItem2")
            .displayShortName("displayShortName2")
            .displayDescription("displayDescription2")
            .displayFormName("displayFormName2")
            .dataSetElementsCount(2)
            .indicatorsCount(2)
            .organisationUnitsCount(2)
            .dataSetElementsContent("dataSetElementsContent2")
            .indicatorsContent("indicatorsContent2")
            .organisationUnitsContent("organisationUnitsContent2");
    }

    public static Dataset getDatasetRandomSampleGenerator() {
        return new Dataset()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .dimensionItemType(UUID.randomUUID().toString())
            .periodType(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .notifyCompletingUser(UUID.randomUUID().toString())
            .formType(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .dimensionItem(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .displayDescription(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .dataSetElementsCount(intCount.incrementAndGet())
            .indicatorsCount(intCount.incrementAndGet())
            .organisationUnitsCount(intCount.incrementAndGet())
            .dataSetElementsContent(UUID.randomUUID().toString())
            .indicatorsContent(UUID.randomUUID().toString())
            .organisationUnitsContent(UUID.randomUUID().toString());
    }
}
