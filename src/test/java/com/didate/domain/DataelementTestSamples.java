package com.didate.domain;

import java.util.UUID;

public class DataelementTestSamples {

    public static Dataelement getDataelementSample1() {
        return new Dataelement()
            .id("id1")
            .name("name1")
            .shortName("shortName1")
            .formName("formName1")
            .description("description1")
            .displayShortName("displayShortName1")
            .displayName("displayName1")
            .displayFormName("displayFormName1")
            .publicAccess("publicAccess1")
            .dimensionItemType("dimensionItemType1")
            .aggregationType("aggregationType1")
            .valueType("valueType1")
            .domainType("domainType1")
            .optionSetValue("optionSetValue1")
            .dimensionItem("dimensionItem1");
    }

    public static Dataelement getDataelementSample2() {
        return new Dataelement()
            .id("id2")
            .name("name2")
            .shortName("shortName2")
            .formName("formName2")
            .description("description2")
            .displayShortName("displayShortName2")
            .displayName("displayName2")
            .displayFormName("displayFormName2")
            .publicAccess("publicAccess2")
            .dimensionItemType("dimensionItemType2")
            .aggregationType("aggregationType2")
            .valueType("valueType2")
            .domainType("domainType2")
            .optionSetValue("optionSetValue2")
            .dimensionItem("dimensionItem2");
    }

    public static Dataelement getDataelementRandomSampleGenerator() {
        return new Dataelement()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .formName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .publicAccess(UUID.randomUUID().toString())
            .dimensionItemType(UUID.randomUUID().toString())
            .aggregationType(UUID.randomUUID().toString())
            .valueType(UUID.randomUUID().toString())
            .domainType(UUID.randomUUID().toString())
            .optionSetValue(UUID.randomUUID().toString())
            .dimensionItem(UUID.randomUUID().toString());
    }
}
