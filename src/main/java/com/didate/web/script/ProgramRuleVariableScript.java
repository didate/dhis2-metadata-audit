package com.didate.web.script;

import com.didate.domain.ProgramRuleVariable;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramRuleVariableService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramRuleVariableScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleVariableScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        log.info("Calling program rule variables API...");

        boolean hasExistingVariables = programRuleVariableService.count() > 0;
        String lastUpdated = hasExistingVariables ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<ProgramRuleVariable> programRuleVariables = programRuleVariableApiService.getData(
            project,
            "programRuleVariables",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<ProgramRuleVariable>>() {}
        );

        for (ProgramRuleVariable variable : programRuleVariables) {
            TypeTrack typeTrack = determineTypeTrack(variable.getId(), hasExistingVariables);
            if (variable.getCreatedBy() == null) {
                variable = variable.createdBy(variable.getLastUpdatedBy());
            }
            variable = variable.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                programRuleVariableService.partialUpdate(variable);
            } else {
                programRuleVariableService.save(variable);
            }
        }

        log.info("Fetched program rule variables: {}", programRuleVariables.size());
    }

    private TypeTrack determineTypeTrack(String variableId, boolean hasExistingVariables) {
        if (!hasExistingVariables) {
            return TypeTrack.NONE;
        }
        return programRuleVariableService.exist(variableId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
