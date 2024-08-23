package com.didate.domain;

import java.util.UUID;

public class OptionGroupTestSamples {

    public static OptionGroup getOptionGroupSample1() {
        return new OptionGroup().id("id1");
    }

    public static OptionGroup getOptionGroupSample2() {
        return new OptionGroup().id("id2");
    }

    public static OptionGroup getOptionGroupRandomSampleGenerator() {
        return new OptionGroup().id(UUID.randomUUID().toString());
    }
}
