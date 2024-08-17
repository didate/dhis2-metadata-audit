package com.didate.domain;

import java.util.UUID;

public class OptionsetTestSamples {

    public static Optionset getOptionsetSample1() {
        return new Optionset().id("id1").name("name1");
    }

    public static Optionset getOptionsetSample2() {
        return new Optionset().id("id2").name("name2");
    }

    public static Optionset getOptionsetRandomSampleGenerator() {
        return new Optionset().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
