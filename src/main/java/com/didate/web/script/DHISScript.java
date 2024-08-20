package com.didate.web.script;

import com.didate.domain.Dataelement;
import com.didate.domain.Indicator;
import com.didate.domain.Project;
import com.didate.service.CategorycomboService;
import com.didate.service.DHISUserService;
import com.didate.service.DataelementService;
import com.didate.service.IndicatorService;
import com.didate.service.IndicatortypeService;
import com.didate.service.OptionsetService;
import com.didate.service.ProjectService;
import com.didate.service.dhis2.DataElementApiService;
import com.didate.service.dhis2.IndicatorApiService;
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
    private final ProjectService projectService;
    private final DataelementService dataelementService;
    private final IndicatorService indicatorService;
    private final IndicatortypeService indicatortypeService;
    private final DHISUserService dhisUserService;
    private final OptionsetService optionsetService;
    private final CategorycomboService categorycomboService;

    public DHISScript(
        DataElementApiService dataElementApiService,
        IndicatorApiService indicatorApiService,
        ProjectService projectService,
        DataelementService dataelementService,
        IndicatorService indicatorService,
        IndicatortypeService indicatortypeService,
        DHISUserService dhisUserService,
        OptionsetService optionsetService,
        CategorycomboService categorycomboService
    ) {
        this.dataElementApiService = dataElementApiService;
        this.indicatorApiService = indicatorApiService;
        this.projectService = projectService;
        this.dataelementService = dataelementService;
        this.indicatorService = indicatorService;
        this.indicatortypeService = indicatortypeService;
        this.categorycomboService = categorycomboService;
        this.optionsetService = optionsetService;
        this.dhisUserService = dhisUserService;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void script() throws IOException {
        log.info("Calling dataElements API...");

        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            performDataElements(dataElementApiService.getDataElements(project));
            performIndicators(indicatorApiService.getIndicators(project));
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
}
