package com.didate.web.script;

import com.didate.domain.ProgramRule;
import com.didate.domain.Project;
import com.didate.service.ProgramRuleService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramRuleScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleScript.class);

    private final DhisApiService<ProgramRule> programRuleApiService;
    private final ProgramRuleService programRuleService;

    public ProgramRuleScript(DhisApiService<ProgramRule> programRuleApiService, ProgramRuleService programRuleService) {
        this.programRuleApiService = programRuleApiService;
        this.programRuleService = programRuleService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programRules API...");

        List<ProgramRule> programRules = programRuleApiService.getData(
            project,
            "programRules",
            new TypeReference<Dhis2ApiResponse<ProgramRule>>() {}
        );

        for (ProgramRule rule : programRules) {
            if (!programRuleService.exist(rule.getId())) {
                programRuleService.save(rule);
            }
        }

        log.info("Fetched program rules: {}", programRules.size());
    }
}
