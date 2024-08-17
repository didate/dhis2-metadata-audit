package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Project getProjectSample1() {
        return new Project().id(1L).projectName("projectName1").dhis2URL("dhis2URL1").token("token1");
    }

    public static Project getProjectSample2() {
        return new Project().id(2L).projectName("projectName2").dhis2URL("dhis2URL2").token("token2");
    }

    public static Project getProjectRandomSampleGenerator() {
        return new Project()
            .id(longCount.incrementAndGet())
            .projectName(UUID.randomUUID().toString())
            .dhis2URL(UUID.randomUUID().toString())
            .token(UUID.randomUUID().toString());
    }
}
