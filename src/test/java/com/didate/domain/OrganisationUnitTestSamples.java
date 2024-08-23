package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OrganisationUnitTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrganisationUnit getOrganisationUnitSample1() {
        return new OrganisationUnit().id("id1").name("name1").path("path1").level(1);
    }

    public static OrganisationUnit getOrganisationUnitSample2() {
        return new OrganisationUnit().id("id2").name("name2").path("path2").level(2);
    }

    public static OrganisationUnit getOrganisationUnitRandomSampleGenerator() {
        return new OrganisationUnit()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .path(UUID.randomUUID().toString())
            .level(intCount.incrementAndGet());
    }
}
