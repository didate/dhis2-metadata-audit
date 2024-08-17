package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonNotifierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PersonNotifier getPersonNotifierSample1() {
        return new PersonNotifier()
            .id(1L)
            .personName("personName1")
            .personPhone("personPhone1")
            .personEmail("personEmail1")
            .personOrganization("personOrganization1");
    }

    public static PersonNotifier getPersonNotifierSample2() {
        return new PersonNotifier()
            .id(2L)
            .personName("personName2")
            .personPhone("personPhone2")
            .personEmail("personEmail2")
            .personOrganization("personOrganization2");
    }

    public static PersonNotifier getPersonNotifierRandomSampleGenerator() {
        return new PersonNotifier()
            .id(longCount.incrementAndGet())
            .personName(UUID.randomUUID().toString())
            .personPhone(UUID.randomUUID().toString())
            .personEmail(UUID.randomUUID().toString())
            .personOrganization(UUID.randomUUID().toString());
    }
}
