package com.didate.domain;

import java.util.UUID;

public class DHISUserTestSamples {

    public static DHISUser getDHISUserSample1() {
        return new DHISUser()
            .id("id1")
            .code("code1")
            .name("name1")
            .displayName("displayName1")
            .username("username1")
            .email("email1")
            .phoneNumber("phoneNumber1");
    }

    public static DHISUser getDHISUserSample2() {
        return new DHISUser()
            .id("id2")
            .code("code2")
            .name("name2")
            .displayName("displayName2")
            .username("username2")
            .email("email2")
            .phoneNumber("phoneNumber2");
    }

    public static DHISUser getDHISUserRandomSampleGenerator() {
        return new DHISUser()
            .id(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .username(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString());
    }
}
