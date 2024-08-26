package com.didate.web.script;

import com.didate.domain.ProgramRule;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramRuleService;
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
public class ProgramRuleScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<ProgramRule> programRuleApiService;
    private final ProgramRuleService programRuleService;

    public ProgramRuleScript(DhisApiService<ProgramRule> programRuleApiService, ProgramRuleService programRuleService) {
        this.programRuleApiService = programRuleApiService;
        this.programRuleService = programRuleService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling program rules API...");

        boolean hasExistingRules = programRuleService.count() > 0;
        String lastUpdated = hasExistingRules ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<ProgramRule> programRules = programRuleApiService.getData(
            project,
            "programRules",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<ProgramRule>>() {}
        );

        for (ProgramRule rule : programRules) {
            TypeTrack typeTrack = determineTypeTrack(rule.getId(), hasExistingRules);
            rule = rule.createdBy(rule.getLastUpdatedBy()).track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                programRuleService.partialUpdate(rule);
            } else {
                programRuleService.save(rule);
            }
        }

        log.info("Fetched program rules: {}", programRules.size());
    }

    private TypeTrack determineTypeTrack(String ruleId, boolean hasExistingRules) {
        if (!hasExistingRules) {
            return TypeTrack.NONE;
        }
        return programRuleService.exist(ruleId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
