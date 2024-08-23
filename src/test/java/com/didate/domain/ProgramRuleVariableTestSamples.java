package com.didate.domain;

import java.util.UUID;

public class ProgramRuleVariableTestSamples {

    public static ProgramRuleVariable getProgramRuleVariableSample1() {
        return new ProgramRuleVariable()
            .id("id1")
            .name("name1")
            .displayName("displayName1")
            .programRuleVariableSourceType("programRuleVariableSourceType1");
    }

    public static ProgramRuleVariable getProgramRuleVariableSample2() {
        return new ProgramRuleVariable()
            .id("id2")
            .name("name2")
            .displayName("displayName2")
            .programRuleVariableSourceType("programRuleVariableSourceType2");
    }

    public static ProgramRuleVariable getProgramRuleVariableRandomSampleGenerator() {
        return new ProgramRuleVariable()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .programRuleVariableSourceType(UUID.randomUUID().toString());
    }
}
