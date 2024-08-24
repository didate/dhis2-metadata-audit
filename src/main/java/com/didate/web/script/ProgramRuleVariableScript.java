package com.didate.web.script;

import com.didate.domain.ProgramRuleVariable;
import com.didate.domain.Project;
import com.didate.service.ProgramRuleVariableService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramRuleVariableScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleVariableScript.class);
    private final DhisApiService<ProgramRuleVariable> programRuleVariableApiService;
    private final ProgramRuleVariableService programRuleVariableService;

    public ProgramRuleVariableScript(
        DhisApiService<ProgramRuleVariable> programRuleVariableApiService,
        ProgramRuleVariableService programRuleVariableService
    ) {
        this.programRuleVariableApiService = programRuleVariableApiService;
        this.programRuleVariableService = programRuleVariableService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programRuleVariables API...");

        List<ProgramRuleVariable> programRuleVariables = programRuleVariableApiService.getData(
            project,
            "programRuleVariables",
            new TypeReference<Dhis2ApiResponse<ProgramRuleVariable>>() {}
        );

        for (ProgramRuleVariable variable : programRuleVariables) {
            if (!programRuleVariableService.exist(variable.getId())) {
                programRuleVariableService.save(variable);
            }
        }

        log.info("Fetched program rule variables: {}", programRuleVariables.size());
    }
}
