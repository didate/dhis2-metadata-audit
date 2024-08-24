package com.didate.web.script;

import com.didate.domain.Categorycombo;
import com.didate.domain.DHISUser;
import com.didate.domain.Dataelement;
import com.didate.domain.Dataset;
import com.didate.domain.Indicator;
import com.didate.domain.Indicatortype;
import com.didate.domain.OptionGroup;
import com.didate.domain.Optionset;
import com.didate.domain.OrganisationUnit;
import com.didate.domain.Program;
import com.didate.domain.ProgramIndicator;
import com.didate.domain.ProgramRule;
import com.didate.domain.ProgramRuleAction;
import com.didate.domain.ProgramRuleVariable;
import com.didate.domain.ProgramStage;
import com.didate.domain.Project;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.CategorycomboService;
import com.didate.service.DHISUserService;
import com.didate.service.DataelementService;
import com.didate.service.DatasetService;
import com.didate.service.IndicatorService;
import com.didate.service.IndicatortypeService;
import com.didate.service.OptionGroupService;
import com.didate.service.OptionsetService;
import com.didate.service.OrganisationUnitService;
import com.didate.service.ProgramIndicatorService;
import com.didate.service.ProgramRuleActionService;
import com.didate.service.ProgramRuleService;
import com.didate.service.ProgramRuleVariableService;
import com.didate.service.ProgramService;
import com.didate.service.ProgramStageService;
import com.didate.service.ProjectService;
import com.didate.service.TrackedEntityAttributeService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
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

    private final DhisApiService<Dataelement> dataElementApiService;
    private final DhisApiService<Indicator> indicatorApiService;

    private final DhisApiService<Categorycombo> categoryComboApiService;
    private final DhisApiService<OptionGroup> optionGroupApiService;
    private final DhisApiService<Optionset> optionSetApiService;
    private final DhisApiService<Indicatortype> indicatorTypeApiService;
    private final DhisApiService<Program> programApiService;
    private final DhisApiService<TrackedEntityAttribute> trackedEntityAttributeApiService;
    private final DhisApiService<ProgramStage> programStageApiService;
    private final DhisApiService<ProgramIndicator> programIndicatorApiService;
    private final DhisApiService<ProgramRule> programRuleApiService;
    private final DhisApiService<ProgramRuleVariable> programRuleVariableApiService;
    private final DhisApiService<ProgramRuleAction> programRuleActionApiService;
    private final DhisApiService<Dataset> dataSetApiService;
    private final DhisApiService<OrganisationUnit> organisationUnitApiService;
    private final DhisApiService<DHISUser> dhisUserApiService;

    private final ProjectService projectService;
    private final DataelementService dataelementService;
    private final IndicatorService indicatorService;
    private final DatasetService datasetService;
    private final ProgramService programService;
    private final IndicatortypeService indicatortypeService;
    private final DHISUserService dhisUserService;
    private final OptionsetService optionsetService;
    private final CategorycomboService categorycomboService;

    private final OptionGroupService optionGroupService;
    private final TrackedEntityAttributeService trackedEntityAttributeService;
    private final ProgramStageService programStageService;
    private final ProgramIndicatorService programIndicatorService;
    private final ProgramRuleService programRuleService;
    private final ProgramRuleVariableService programRuleVariableService;
    private final ProgramRuleActionService programRuleActionService;
    private final OrganisationUnitService organisationUnitService;

    public DHISScript(
        DhisApiService<Dataelement> dataElementApiService,
        DhisApiService<Indicator> indicatorApiService,
        DhisApiService<Categorycombo> categoryComboApiService,
        DhisApiService<OptionGroup> optionGroupApiService,
        DhisApiService<Optionset> optionSetApiService,
        DhisApiService<Indicatortype> indicatorTypeApiService,
        DhisApiService<Program> programApiService,
        DhisApiService<TrackedEntityAttribute> trackedEntityAttributeApiService,
        DhisApiService<ProgramStage> programStageApiService,
        DhisApiService<ProgramIndicator> programIndicatorApiService,
        DhisApiService<ProgramRule> programRuleApiService,
        DhisApiService<ProgramRuleVariable> programRuleVariableApiService,
        DhisApiService<ProgramRuleAction> programRuleActionApiService,
        DhisApiService<Dataset> dataSetApiService,
        DhisApiService<OrganisationUnit> organisationUnitApiService,
        DhisApiService<DHISUser> dhisUserApiService,
        ProjectService projectService,
        DataelementService dataelementService,
        IndicatorService indicatorService,
        IndicatortypeService indicatortypeService,
        DHISUserService dhisUserService,
        OptionsetService optionsetService,
        CategorycomboService categorycomboService,
        DatasetService datasetService,
        ProgramService programService,
        OptionGroupService optionGroupService,
        TrackedEntityAttributeService trackedEntityAttributeService,
        ProgramStageService programStageService,
        ProgramIndicatorService programIndicatorService,
        ProgramRuleService programRuleService,
        ProgramRuleVariableService programRuleVariableService,
        ProgramRuleActionService programRuleActionService,
        OrganisationUnitService organisationUnitService
    ) {
        this.dataElementApiService = dataElementApiService;
        this.indicatorApiService = indicatorApiService;

        this.categoryComboApiService = categoryComboApiService;
        this.optionGroupApiService = optionGroupApiService;
        this.optionSetApiService = optionSetApiService;
        this.indicatorTypeApiService = indicatorTypeApiService;
        this.programApiService = programApiService;
        this.trackedEntityAttributeApiService = trackedEntityAttributeApiService;
        this.programStageApiService = programStageApiService;
        this.programIndicatorApiService = programIndicatorApiService;
        this.programRuleApiService = programRuleApiService;
        this.programRuleVariableApiService = programRuleVariableApiService;
        this.programRuleActionApiService = programRuleActionApiService;
        this.dataSetApiService = dataSetApiService;
        this.organisationUnitApiService = organisationUnitApiService;
        this.dhisUserApiService = dhisUserApiService;

        this.projectService = projectService;
        this.dataelementService = dataelementService;
        this.indicatorService = indicatorService;
        this.indicatortypeService = indicatortypeService;
        this.categorycomboService = categorycomboService;
        this.optionsetService = optionsetService;
        this.dhisUserService = dhisUserService;
        this.datasetService = datasetService;
        this.programService = programService;

        this.optionGroupService = optionGroupService;
        this.trackedEntityAttributeService = trackedEntityAttributeService;
        this.programStageService = programStageService;
        this.programIndicatorService = programIndicatorService;
        this.programRuleService = programRuleService;
        this.programRuleVariableService = programRuleVariableService;
        this.programRuleActionService = programRuleActionService;
        this.organisationUnitService = organisationUnitService;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void script() throws IOException {
        log.info("Calling dataElements API...");

        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            performUsers(dhisUserApiService.getData(project, "users", new TypeReference<Dhis2ApiResponse<DHISUser>>() {}), project);

            performDataElements(
                dataElementApiService.getData(project, "dataElements", new TypeReference<Dhis2ApiResponse<Dataelement>>() {}),
                project
            );

            performIndicators(
                indicatorApiService.getData(project, "indicators", new TypeReference<Dhis2ApiResponse<Indicator>>() {}),
                project
            );

            performOptionGroups(
                optionGroupApiService.getData(project, "optionGroups", new TypeReference<Dhis2ApiResponse<OptionGroup>>() {}),
                project
            );
            performTrackedEntityAttributes(
                trackedEntityAttributeApiService.getData(
                    project,
                    "trackedEntityAttributes",
                    new TypeReference<Dhis2ApiResponse<TrackedEntityAttribute>>() {}
                ),
                project
            );
            performProgramStages(
                programStageApiService.getData(project, "programStages", new TypeReference<Dhis2ApiResponse<ProgramStage>>() {}),
                project
            );
            performProgramIndicators(
                programIndicatorApiService.getData(
                    project,
                    "programIndicators",
                    new TypeReference<Dhis2ApiResponse<ProgramIndicator>>() {}
                ),
                project
            );
            performProgramRules(
                programRuleApiService.getData(project, "programRules", new TypeReference<Dhis2ApiResponse<ProgramRule>>() {}),
                project
            );
            performProgramRuleVariables(
                programRuleVariableApiService.getData(
                    project,
                    "programRuleVariables",
                    new TypeReference<Dhis2ApiResponse<ProgramRuleVariable>>() {}
                ),
                project
            );
            performProgramRuleActions(
                programRuleActionApiService.getData(
                    project,
                    "programRuleActions",
                    new TypeReference<Dhis2ApiResponse<ProgramRuleAction>>() {}
                ),
                project
            );
            performOrganisationUnits(
                organisationUnitApiService.getData(
                    project,
                    "organisationUnits",
                    new TypeReference<Dhis2ApiResponse<OrganisationUnit>>() {}
                ),
                project
            );
        }
    }

    private void performUsers(List<DHISUser> dhisUsers, Project project) {}

    private void performDataElements(List<Dataelement> dataelements, Project project) {
        log.info("Fetched data elements: {}", dataelements.size());
        log.info(dataelements.getClass().getName());

        dataelements
            .stream()
            .filter(e -> !dataelementService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy().track(TypeTrack.NONE));
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy().track(TypeTrack.NONE));
                }

                if (e.getOptionSet() != null && Boolean.FALSE.equals(optionsetService.exist(e.getOptionSet().getId()))) {
                    optionsetService.save(e.getOptionSet());
                }
                if (e.getCategoryCombo() != null && Boolean.FALSE.equals(categorycomboService.exist(e.getCategoryCombo().getId()))) {
                    categorycomboService.save(e.getCategoryCombo());
                }

                dataelementService.save(e.track(TypeTrack.NONE));
            });

        log.info("Fetched data elements: {}", dataelements.size());
    }

    private void performIndicators(List<Indicator> indicators, Project project) {
        indicators
            .stream()
            .filter(e -> !indicatorService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy());
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy());
                }

                if (e.getIndicatorType() != null && Boolean.FALSE.equals(indicatortypeService.exist(e.getIndicatorType().getId()))) {
                    indicatortypeService.save(e.getIndicatorType());
                }
                indicatorService.save(e);
            });

        log.info("Fetched indicators: {}", indicators.size());
    }

    private void performPrograms(List<Program> programs) {
        programs
            .stream()
            .filter(e -> !programService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy());
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy());
                }

                programService.save(e);
            });

        log.info("Fetched programs: {}", programs.size());
    }

    private void performDataSets(List<Dataset> datasets) {
        datasets
            .stream()
            .filter(e -> !datasetService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy());
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy());
                }
                datasetService.save(e);
            });

        log.info("Fetched datasets: {}", datasets.size());
    }

    private void performOptionGroups(List<OptionGroup> optionGroups, Project project) {}

    private void performTrackedEntityAttributes(List<TrackedEntityAttribute> trackedEntityAttributes, Project project) {}

    private void performProgramStages(List<ProgramStage> programStages, Project project) {}

    private void performProgramIndicators(List<ProgramIndicator> programIndicators, Project project) {}

    private void performProgramRules(List<ProgramRule> programRules, Project project) {}

    private void performProgramRuleVariables(List<ProgramRuleVariable> programRuleVariables, Project project) {}

    private void performProgramRuleActions(List<ProgramRuleAction> programRuleActions, Project project) {}

    private void performOrganisationUnits(List<OrganisationUnit> organisationUnits, Project project) {}
}
