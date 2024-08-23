package com.didate.web.script;

import com.didate.domain.Dataelement;
import com.didate.domain.Dataset;
import com.didate.domain.Indicator;
import com.didate.domain.Program;
import com.didate.domain.Project;
import com.didate.service.CategorycomboService;
import com.didate.service.DHISUserService;
import com.didate.service.DataelementService;
import com.didate.service.DatasetService;
import com.didate.service.IndicatorService;
import com.didate.service.IndicatortypeService;
import com.didate.service.OptionsetService;
import com.didate.service.ProgramService;
import com.didate.service.ProjectService;
import com.didate.service.dhis2.request.DataElementApiService;
import com.didate.service.dhis2.request.DatasetApiService;
import com.didate.service.dhis2.request.IndicatorApiService;
import com.didate.service.dhis2.request.ProgramApiService;
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
    private final DataElementApiService dataElementApiService;
    private final IndicatorApiService indicatorApiService;
    private final ProgramApiService programApiService;
    private final DatasetApiService datasetApiService;

    private final ProjectService projectService;
    private final DataelementService dataelementService;
    private final IndicatorService indicatorService;
    private final DatasetService datasetService;
    private final ProgramService programService;
    private final IndicatortypeService indicatortypeService;
    private final DHISUserService dhisUserService;
    private final OptionsetService optionsetService;
    private final CategorycomboService categorycomboService;

    public DHISScript(
        DataElementApiService dataElementApiService,
        IndicatorApiService indicatorApiService,
        ProgramApiService programApiService,
        DatasetApiService datasetApiService,
        ProjectService projectService,
        DataelementService dataelementService,
        IndicatorService indicatorService,
        IndicatortypeService indicatortypeService,
        DHISUserService dhisUserService,
        OptionsetService optionsetService,
        CategorycomboService categorycomboService,
        DatasetService datasetService,
        ProgramService programService
    ) {
        this.dataElementApiService = dataElementApiService;
        this.indicatorApiService = indicatorApiService;
        this.programApiService = programApiService;
        this.datasetApiService = datasetApiService;
        this.projectService = projectService;
        this.dataelementService = dataelementService;
        this.indicatorService = indicatorService;
        this.indicatortypeService = indicatortypeService;
        this.categorycomboService = categorycomboService;
        this.optionsetService = optionsetService;
        this.dhisUserService = dhisUserService;
        this.datasetService = datasetService;
        this.programService = programService;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void script() throws IOException {
        log.info("Calling dataElements API...");

        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            performDataElements(dataElementApiService.getDataElements(project));
            performIndicators(indicatorApiService.getIndicators(project));
            //performDataSets(datasetApiService.getDataSets(project));
            performPrograms(programApiService.getPrograms(project));
        }
    }

    private void performDataElements(List<Dataelement> dataelements) {
        dataelements
            .stream()
            .filter(e -> !dataelementService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy());
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy());
                }

                if (e.getOptionSet() != null && Boolean.FALSE.equals(optionsetService.exist(e.getOptionSet().getId()))) {
                    optionsetService.save(e.getOptionSet());
                }
                if (e.getCategoryCombo() != null && Boolean.FALSE.equals(categorycomboService.exist(e.getCategoryCombo().getId()))) {
                    categorycomboService.save(e.getCategoryCombo());
                }

                dataelementService.save(e);
            });
        log.info("Fetched data elements: {}", dataelements.size());
    }

    private void performIndicators(List<Indicator> indicators) {
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
}
