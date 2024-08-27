package com.didate.web.script;

import com.didate.domain.Project;
import com.didate.service.ProjectService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DHISScript {

    private static final Logger log = LoggerFactory.getLogger(DHISScript.class);

    private final ProjectService projectService;
    private final DHISUserScript userScript;
    private final DataElementScript dataElementScript;
    private final IndicatorScript indicatorScript;
    private final CategoryComboScript categoryComboScript;
    private final OptionGroupScript optionGroupScript;
    private final TrackedEntityAttributeScript trackedEntityAttributeScript;
    private final ProgramStageScript programStageScript;
    private final ProgramIndicatorScript programIndicatorScript;
    private final ProgramRuleScript programRuleScript;
    private final ProgramRuleVariableScript programRuleVariableScript;
    private final ProgramRuleActionScript programRuleActionScript;
    private final OrganisationUnitScript organisationUnitScript;
    private final IndicatorTypeScript indicatorTypeScript;
    private final OptionSetScript optionSetScript;
    private final ProgramScript programScript;
    private final DataSetScript dataSetScript;

    public DHISScript(
        ProjectService projectService,
        DHISUserScript userScript,
        DataElementScript dataElementScript,
        IndicatorScript indicatorScript,
        CategoryComboScript categoryComboScript,
        OptionGroupScript optionGroupScript,
        TrackedEntityAttributeScript trackedEntityAttributeScript,
        ProgramStageScript programStageScript,
        ProgramIndicatorScript programIndicatorScript,
        ProgramRuleScript programRuleScript,
        ProgramRuleVariableScript programRuleVariableScript,
        ProgramRuleActionScript programRuleActionScript,
        OrganisationUnitScript organisationUnitScript,
        IndicatorTypeScript indicatorTypeScript,
        OptionSetScript optionSetScript,
        ProgramScript programScript,
        DataSetScript dataSetScript
    ) {
        this.projectService = projectService;
        this.userScript = userScript;
        this.dataElementScript = dataElementScript;
        this.indicatorScript = indicatorScript;
        this.categoryComboScript = categoryComboScript;
        this.optionGroupScript = optionGroupScript;
        this.trackedEntityAttributeScript = trackedEntityAttributeScript;
        this.programStageScript = programStageScript;
        this.programIndicatorScript = programIndicatorScript;
        this.programRuleScript = programRuleScript;
        this.programRuleVariableScript = programRuleVariableScript;
        this.programRuleActionScript = programRuleActionScript;
        this.organisationUnitScript = organisationUnitScript;
        this.indicatorTypeScript = indicatorTypeScript;
        this.optionSetScript = optionSetScript;
        this.programScript = programScript;
        this.dataSetScript = dataSetScript;
    }

    //@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void script() throws IOException {
        log.info("Calling dataElements API...");

        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            userScript.perform(project);
            optionGroupScript.perform(project);
            categoryComboScript.perform(project);
            indicatorTypeScript.perform(project);
            optionSetScript.perform(project);
            organisationUnitScript.perform(project);
            dataElementScript.perform(project);
            indicatorScript.perform(project);
            dataSetScript.perform(project);

            trackedEntityAttributeScript.perform(project);

            programScript.perform(project);
            programStageScript.perform(project);

            programIndicatorScript.perform(project);

            programRuleScript.perform(project);

            programRuleVariableScript.perform(project);

            programRuleActionScript.perform(project);
        }
    }
}
