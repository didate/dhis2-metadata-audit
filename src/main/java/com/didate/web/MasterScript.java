package com.didate.web;

import com.didate.domain.PersonNotifier;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.*;
import com.didate.service.dto.DHISUserDTO;
import com.didate.web.script.CategoryComboScript;
import com.didate.web.script.DHISUserScript;
import com.didate.web.script.DataElementScript;
import com.didate.web.script.DataSetScript;
import com.didate.web.script.IndicatorScript;
import com.didate.web.script.IndicatorTypeScript;
import com.didate.web.script.OptionGroupScript;
import com.didate.web.script.OptionSetScript;
import com.didate.web.script.OrganisationUnitScript;
import com.didate.web.script.ProgramIndicatorScript;
import com.didate.web.script.ProgramRuleActionScript;
import com.didate.web.script.ProgramRuleScript;
import com.didate.web.script.ProgramRuleVariableScript;
import com.didate.web.script.ProgramScript;
import com.didate.web.script.ProgramStageScript;
import com.didate.web.script.TrackedEntityAttributeScript;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class MasterScript {

    private static final Logger log = LoggerFactory.getLogger(MasterScript.class);

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

    private final DataelementService dataElementService;
    private final IndicatorService indicatorService;
    private final CategorycomboService categoryComboService;
    private final OptionGroupService optionGroupService;
    private final TrackedEntityAttributeService trackedEntityAttributeService;
    private final ProgramStageService programStageService;
    private final ProgramIndicatorService programIndicatorService;
    private final ProgramRuleService programRuleService;
    private final ProgramRuleVariableService programRuleVariableService;
    private final ProgramRuleActionService programRuleActionService;
    private final OrganisationUnitService organisationUnitService;
    private final IndicatortypeService indicatorTypeService;
    private final OptionsetService optionSetService;
    private final ProgramService programService;
    private final DatasetService dataSetService;

    private final DHISUserService dHISUserService;

    private final MailService mailService;

    private final PersonNotifierService personNotifierService;

    public MasterScript(
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
        DataSetScript dataSetScript,
        DataelementService dataElementService,
        IndicatorService indicatorService,
        CategorycomboService categoryComboService,
        OptionGroupService optionGroupService,
        TrackedEntityAttributeService trackedEntityAttributeService,
        ProgramStageService programStageService,
        ProgramIndicatorService programIndicatorService,
        ProgramRuleService programRuleService,
        ProgramRuleVariableService programRuleVariableService,
        ProgramRuleActionService programRuleActionService,
        OrganisationUnitService organisationUnitService,
        IndicatortypeService indicatorTypeService,
        OptionsetService optionSetService,
        ProgramService programService,
        DatasetService dataSetService,
        DHISUserService dHISUserService,
        MailService mailService,
        PersonNotifierService personNotifierService
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

        this.dataElementService = dataElementService;
        this.indicatorService = indicatorService;
        this.categoryComboService = categoryComboService;
        this.optionGroupService = optionGroupService;
        this.trackedEntityAttributeService = trackedEntityAttributeService;
        this.programStageService = programStageService;
        this.programIndicatorService = programIndicatorService;
        this.programRuleService = programRuleService;
        this.programRuleVariableService = programRuleVariableService;
        this.programRuleActionService = programRuleActionService;
        this.organisationUnitService = organisationUnitService;
        this.indicatorTypeService = indicatorTypeService;
        this.optionSetService = optionSetService;
        this.programService = programService;
        this.dataSetService = dataSetService;
        this.dHISUserService = dHISUserService;
        this.mailService = mailService;
        this.personNotifierService = personNotifierService;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void script() throws IOException {
        log.info("Running audit script ...");

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

    // @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void disableUser() throws IOException {
        log.info("Running disabling users script ...");

        List<DHISUserDTO> dhisUserDTOs = dHISUserService
            .findAll(PageRequest.of(0, Integer.MAX_VALUE), null, null, null, 4, false)
            .getContent();

        List<Project> projects = projectService.findAll();

        if (!projects.isEmpty()) {
            for (DHISUserDTO userDTO : dhisUserDTOs) {
                userScript.disableUser(projects.get(0), userDTO.getId());
            }
        }
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void sendNotification() {
        long newDataElement = dataElementService.countByTrack(TypeTrack.NEW);
        long newIndicator = indicatorService.countByTrack(TypeTrack.NEW);
        long newCategoryCombo = categoryComboService.countByTrack(TypeTrack.NEW);
        long newOptionGroup = optionGroupService.countByTrack(TypeTrack.NEW);
        long newTrackedEntityAttribute = trackedEntityAttributeService.countByTrack(TypeTrack.NEW);
        long newProgramStage = programStageService.countByTrack(TypeTrack.NEW);
        long newProgramIndicator = programIndicatorService.countByTrack(TypeTrack.NEW);
        long newProgramRule = programRuleService.countByTrack(TypeTrack.NEW);
        long newProgramRuleVariable = programRuleVariableService.countByTrack(TypeTrack.NEW);
        long newProgramRuleAction = programRuleActionService.countByTrack(TypeTrack.NEW);
        long newOrganisationUnit = organisationUnitService.countByTrack(TypeTrack.NEW);
        long newIndicatorType = indicatorTypeService.countByTrack(TypeTrack.NEW);
        long newOptionSet = optionSetService.countByTrack(TypeTrack.NEW);
        long newProgram = programService.countByTrack(TypeTrack.NEW);
        long newDataSet = dataSetService.countByTrack(TypeTrack.NEW);
        long newdHISUser = dHISUserService.countByTrack(TypeTrack.NEW);

        long updateDataElement = dataElementService.countByTrack(TypeTrack.UPDATE);
        long updateIndicator = indicatorService.countByTrack(TypeTrack.UPDATE);
        long updateCategoryCombo = categoryComboService.countByTrack(TypeTrack.UPDATE);
        long updateOptionGroup = optionGroupService.countByTrack(TypeTrack.UPDATE);
        long updateTrackedEntityAttribute = trackedEntityAttributeService.countByTrack(TypeTrack.UPDATE);
        long updateProgramStage = programStageService.countByTrack(TypeTrack.UPDATE);
        long updateProgramIndicator = programIndicatorService.countByTrack(TypeTrack.UPDATE);
        long updateProgramRule = programRuleService.countByTrack(TypeTrack.UPDATE);
        long updateProgramRuleVariable = programRuleVariableService.countByTrack(TypeTrack.UPDATE);
        long updateProgramRuleAction = programRuleActionService.countByTrack(TypeTrack.UPDATE);
        long updateOrganisationUnit = organisationUnitService.countByTrack(TypeTrack.UPDATE);
        long updateIndicatorType = indicatorTypeService.countByTrack(TypeTrack.UPDATE);
        long updateOptionSet = optionSetService.countByTrack(TypeTrack.UPDATE);
        long updateProgram = programService.countByTrack(TypeTrack.UPDATE);
        long updateDataSet = dataSetService.countByTrack(TypeTrack.UPDATE);
        long updatedHISUser = dHISUserService.countByTrack(TypeTrack.UPDATE);

        Context context = new Context();
        context.setVariable("updateDataElement", updateDataElement);
        context.setVariable("updateIndicator", updateIndicator);
        context.setVariable("updateCategoryCombo", updateCategoryCombo);
        context.setVariable("updateOptionGroup", updateOptionGroup);
        context.setVariable("updateTrackedEntityAttribute", updateTrackedEntityAttribute);
        context.setVariable("updateProgramStage", updateProgramStage);
        context.setVariable("updateProgramIndicator", updateProgramIndicator);
        context.setVariable("updateProgramRule", updateProgramRule);
        context.setVariable("updateProgramRuleVariable", updateProgramRuleVariable);
        context.setVariable("updateProgramRuleAction", updateProgramRuleAction);
        context.setVariable("updateOrganisationUnit", updateOrganisationUnit);
        context.setVariable("updateIndicatorType", updateIndicatorType);
        context.setVariable("updateOptionSet", updateOptionSet);
        context.setVariable("updateProgram", updateProgram);
        context.setVariable("updateDataSet", updateDataSet);
        context.setVariable("updatedHISUser", updatedHISUser);

        context.setVariable("newDataElement", newDataElement);
        context.setVariable("newIndicator", newIndicator);
        context.setVariable("newCategoryCombo", newCategoryCombo);
        context.setVariable("newOptionGroup", newOptionGroup);
        context.setVariable("newTrackedEntityAttribute", newTrackedEntityAttribute);
        context.setVariable("newProgramStage", newProgramStage);
        context.setVariable("newProgramIndicator", newProgramIndicator);
        context.setVariable("newProgramRule", newProgramRule);
        context.setVariable("newProgramRuleVariable", newProgramRuleVariable);
        context.setVariable("newProgramRuleAction", newProgramRuleAction);
        context.setVariable("newOrganisationUnit", newOrganisationUnit);
        context.setVariable("newIndicatorType", newIndicatorType);
        context.setVariable("newOptionSet", newOptionSet);
        context.setVariable("newProgram", newProgram);
        context.setVariable("newDataSet", newDataSet);
        context.setVariable("newdHISUser", newdHISUser);

        List<PersonNotifier> personNotifiers = personNotifierService.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        for (PersonNotifier personNotifier : personNotifiers) {
            mailService.sendNotification(personNotifier.getPersonEmail(), context);
        }

        dataElementService.setTrackNone();
        indicatorService.setTrackNone();
        categoryComboService.setTrackNone();
        optionGroupService.setTrackNone();
        trackedEntityAttributeService.setTrackNone();
        programStageService.setTrackNone();
        programIndicatorService.setTrackNone();
        programRuleService.setTrackNone();
        programRuleVariableService.setTrackNone();
        programRuleActionService.setTrackNone();
        organisationUnitService.setTrackNone();
        indicatorTypeService.setTrackNone();
        optionSetService.setTrackNone();
        programService.setTrackNone();
        dataSetService.setTrackNone();
        dHISUserService.setTrackNone();
    }
}
