package com.didate.web.script;

import com.didate.domain.ProgramRuleAction;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramRuleActionService;
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
public class ProgramRuleActionScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleActionScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        log.info("Calling program rule actions API...");

        boolean hasExistingActions = programRuleActionService.count() > 0;
        String lastUpdated = hasExistingActions ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<ProgramRuleAction> programRuleActions = programRuleActionApiService.getData(
            project,
            "programRuleActions",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<ProgramRuleAction>>() {}
        );

        for (ProgramRuleAction action : programRuleActions) {
            TypeTrack typeTrack = determineTypeTrack(action.getId(), hasExistingActions);
            action = action.createdBy(action.getLastUpdatedBy()).track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                programRuleActionService.partialUpdate(action);
            } else {
                programRuleActionService.save(action);
            }
        }

        log.info("Fetched program rule actions: {}", programRuleActions.size());
    }

    private TypeTrack determineTypeTrack(String actionId, boolean hasExistingActions) {
        if (!hasExistingActions) {
            return TypeTrack.NONE;
        }
        return programRuleActionService.exist(actionId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
