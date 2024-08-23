package com.didate.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramStageTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProgramStage getProgramStageSample1() {
        return new ProgramStage()
            .id("id1")
            .name("name1")
            .minDaysFromStart(1)
            .executionDateLabel("executionDateLabel1")
            .validationStrategy("validationStrategy1")
            .featureType("featureType1")
            .sortOrder(1)
            .displayExecutionDateLabel("displayExecutionDateLabel1")
            .formType("formType1")
            .displayFormName("displayFormName1")
            .displayName("displayName1")
            .programStageDataElementsCount(1)
            .programStageDataElementsContent(1);
    }

    public static ProgramStage getProgramStageSample2() {
        return new ProgramStage()
            .id("id2")
            .name("name2")
            .minDaysFromStart(2)
            .executionDateLabel("executionDateLabel2")
            .validationStrategy("validationStrategy2")
            .featureType("featureType2")
            .sortOrder(2)
            .displayExecutionDateLabel("displayExecutionDateLabel2")
            .formType("formType2")
            .displayFormName("displayFormName2")
            .displayName("displayName2")
            .programStageDataElementsCount(2)
            .programStageDataElementsContent(2);
    }

    public static ProgramStage getProgramStageRandomSampleGenerator() {
        return new ProgramStage()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .minDaysFromStart(intCount.incrementAndGet())
            .executionDateLabel(UUID.randomUUID().toString())
            .validationStrategy(UUID.randomUUID().toString())
            .featureType(UUID.randomUUID().toString())
            .sortOrder(intCount.incrementAndGet())
            .displayExecutionDateLabel(UUID.randomUUID().toString())
            .formType(UUID.randomUUID().toString())
            .displayFormName(UUID.randomUUID().toString())
            .displayName(UUID.randomUUID().toString())
            .programStageDataElementsCount(intCount.incrementAndGet())
            .programStageDataElementsContent(intCount.incrementAndGet());
    }
}
