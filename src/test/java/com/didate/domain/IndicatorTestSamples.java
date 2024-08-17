package com.didate.domain;

import java.util.UUID;

public class IndicatorTestSamples {

    public static Indicator getIndicatorSample1() {
        return new Indicator()
            .id("id1")
            .name("name1")
            .shortName("shortName1")
            .displayShortName("displayShortName1")
            .displayName("displayName1")
            .displayFormName("displayFormName1")
            .publicAccess("publicAccess1")
            .dimensionItemType("dimensionItemType1")
            .numerator("numerator1")
            .numeratorDescription("numeratorDescription1")
            .denominator("denominator1")
            .denominatorDescription("denominatorDescription1")
            .displayNumeratorDescription("displayNumeratorDescription1")
            .displayDenominatorDescription("displayDenominatorDescription1")
            .dimensionItem("dimensionItem1");
    }

    public static Indicator getIndicatorSample2() {
        return new Indicator()
            .id("id2")
            .name("name2")
            .shortName("shortName2")
            .displayShortName("displayShortName2")
            .displayName("displayName2")
            .displayFormName("displayFormName2")
            .publicAccess("publicAccess2")
            .dimensionItemType("dimensionItemType2")
            .numerator("numerator2")
            .numeratorDescription("numeratorDescription2")
            .denominator("denominator2")
            .denominatorDescription("denominatorDescription2")
            .displayNumeratorDescription("displayNumeratorDescription2")
            .displayDenominatorDescription("displayDenominatorDescription2")
            .dimensionItem("dimensionItem2");
    }

    public static Indicator getIndicatorRandomSampleGenerator() {
        return new Indicator()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .publicAccess(UUID.randomUUID().toString())
            .dimensionItemType(UUID.randomUUID().toString())
            .numerator(UUID.randomUUID().toString())
            .numeratorDescription(UUID.randomUUID().toString())
            .denominator(UUID.randomUUID().toString())
            .denominatorDescription(UUID.randomUUID().toString())
            .displayNumeratorDescription(UUID.randomUUID().toString())
            .displayDenominatorDescription(UUID.randomUUID().toString())
            .dimensionItem(UUID.randomUUID().toString());
    }
}
