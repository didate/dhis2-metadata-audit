package com.didate.domain;

import java.util.UUID;

public class IndicatortypeTestSamples {

    public static Indicatortype getIndicatortypeSample1() {
        return new Indicatortype().id("id1").name("name1");
    }

    public static Indicatortype getIndicatortypeSample2() {
        return new Indicatortype().id("id2").name("name2");
    }

    public static Indicatortype getIndicatortypeRandomSampleGenerator() {
        return new Indicatortype().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
