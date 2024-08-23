package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramRuleTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProgramRule getProgramRuleSample1() {
        return new ProgramRule().id("id1").name("name1").displayName("displayName1").priority(1).condition("condition1");
    }

    public static ProgramRule getProgramRuleSample2() {
        return new ProgramRule().id("id2").name("name2").displayName("displayName2").priority(2).condition("condition2");
    }

    public static ProgramRule getProgramRuleRandomSampleGenerator() {
        return new ProgramRule()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .priority(intCount.incrementAndGet())
            .condition(UUID.randomUUID().toString());
    }
}
