package com.didate.domain;

import java.util.UUID;

public class CategorycomboTestSamples {

    public static Categorycombo getCategorycomboSample1() {
        return new Categorycombo().id("id1").name("name1");
    }

    public static Categorycombo getCategorycomboSample2() {
        return new Categorycombo().id("id2").name("name2");
    }

    public static Categorycombo getCategorycomboRandomSampleGenerator() {
        return new Categorycombo().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
