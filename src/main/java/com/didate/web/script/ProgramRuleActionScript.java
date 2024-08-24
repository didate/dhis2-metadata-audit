package com.didate.web.script;

import com.didate.domain.ProgramRuleAction;
import com.didate.domain.Project;
import com.didate.service.ProgramRuleActionService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramRuleActionScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleActionScript.class);

    private final DhisApiService<ProgramRuleAction> programRuleActionApiService;
    private final ProgramRuleActionService programRuleActionService;

    public ProgramRuleActionScript(
        DhisApiService<ProgramRuleAction> programRuleActionApiService,
        ProgramRuleActionService programRuleActionService
    ) {
        this.programRuleActionApiService = programRuleActionApiService;
        this.programRuleActionService = programRuleActionService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programRuleActions API...");

        List<ProgramRuleAction> programRuleActions = programRuleActionApiService.getData(
            project,
            "programRuleActions",
            new TypeReference<Dhis2ApiResponse<ProgramRuleAction>>() {}
        );

        for (ProgramRuleAction programRuleAction : programRuleActions) {
            if (!programRuleActionService.exist(programRuleAction.getId())) {
                programRuleActionService.save(programRuleAction);
            }
        }

        log.info("Fetched program rule actions: {}", programRuleActions.size());
    }
}
