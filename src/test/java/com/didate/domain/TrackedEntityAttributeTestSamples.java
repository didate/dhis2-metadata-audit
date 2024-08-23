package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TrackedEntityAttributeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TrackedEntityAttribute getTrackedEntityAttributeSample1() {
        return new TrackedEntityAttribute()
            .id("id1")
            .name("name1")
            .shortName("shortName1")
            .valueType("valueType1")
            .displayFormName("displayFormName1")
            .dimensionItemType("dimensionItemType1")
            .aggregationType("aggregationType1")
            .displayName("displayName1")
            .patterne("patterne1")
            .displayShortName("displayShortName1")
            .periodOffset(1)
            .formName("formName1")
            .dimensionItem("dimensionItem1");
    }

    public static TrackedEntityAttribute getTrackedEntityAttributeSample2() {
        return new TrackedEntityAttribute()
            .id("id2")
            .name("name2")
            .shortName("shortName2")
            .valueType("valueType2")
            .displayFormName("displayFormName2")
            .dimensionItemType("dimensionItemType2")
            .aggregationType("aggregationType2")
            .displayName("displayName2")
            .patterne("patterne2")
            .displayShortName("displayShortName2")
            .periodOffset(2)
            .formName("formName2")
            .dimensionItem("dimensionItem2");
    }

    public static TrackedEntityAttribute getTrackedEntityAttributeRandomSampleGenerator() {
        return new TrackedEntityAttribute()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shortName(UUID.randomUUID().toString())
            .valueType(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .dimensionItemType(UUID.randomUUID().toString())
            .aggregationType(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .patterne(UUID.randomUUID().toString())
            .displayShortName(UUID.randomUUID().toString())
            .periodOffset(intCount.incrementAndGet())
            .formName(UUID.randomUUID().toString())
            .dimensionItem(UUID.randomUUID().toString());
    }
}
