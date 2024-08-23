package com.didate.domain;

import java.util.UUID;

public class ProgramRuleActionTestSamples {

    public static ProgramRuleAction getProgramRuleActionSample1() {
        return new ProgramRuleAction()
            .id("id1")
            .created("created1")
            .programRuleActionType("programRuleActionType1")
            .evaluationTime("evaluationTime1")
            .data("data1")
            .templateUid("templateUid1")
            .content("content1")
            .displayContent("displayContent1");
    }

    public static ProgramRuleAction getProgramRuleActionSample2() {
        return new ProgramRuleAction()
            .id("id2")
            .created("created2")
            .programRuleActionType("programRuleActionType2")
            .evaluationTime("evaluationTime2")
            .data("data2")
            .templateUid("templateUid2")
            .content("content2")
            .displayContent("displayContent2");
    }

    public static ProgramRuleAction getProgramRuleActionRandomSampleGenerator() {
        return new ProgramRuleAction()
            .id(UUID.randomUUID().toString())
            .created(UUID.randomUUID().toString())
            .programRuleActionType(UUID.randomUUID().toString())
            .evaluationTime(UUID.randomUUID().toString())
            .data(UUID.randomUUID().toString())
            .templateUid(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .displayContent(UUID.randomUUID().toString());
    }
}
