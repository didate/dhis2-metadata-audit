package com.didate.web.script;

import com.didate.domain.Indicator;
import com.didate.domain.Project;
import com.didate.service.DHISUserService;
import com.didate.service.IndicatorService;
import com.didate.service.IndicatortypeService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IndicatorScript {

    private static final Logger log = LoggerFactory.getLogger(IndicatorScript.class);

    private final DhisApiService<Indicator> indicatorApiService;
    private final IndicatorService indicatorService;
    private final DHISUserService dhisUserService;
    private final IndicatortypeService indicatortypeService;

    public IndicatorScript(
        DhisApiService<Indicator> indicatorApiService,
        IndicatorService indicatorService,
        DHISUserService dhisUserService,
        IndicatortypeService indicatortypeService
    ) {
        this.indicatorApiService = indicatorApiService;
        this.indicatorService = indicatorService;
        this.dhisUserService = dhisUserService;
        this.indicatortypeService = indicatortypeService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling indicators API...");

        List<Indicator> indicators = indicatorApiService.getData(
            project,
            "indicators",
            new TypeReference<Dhis2ApiResponse<Indicator>>() {}
        );

        for (Indicator indicator : indicators) {
            if (!indicatorService.exist(indicator.getId())) {
                if (!dhisUserService.exist(indicator.getCreatedBy().getId())) {
                    dhisUserService.save(indicator.getCreatedBy());
                }
                if (!dhisUserService.exist(indicator.getLastUpdatedBy().getId())) {
                    dhisUserService.save(indicator.getLastUpdatedBy());
                }

                if (indicator.getIndicatorType() != null && !indicatortypeService.exist(indicator.getIndicatorType().getId())) {
                    indicatortypeService.save(indicator.getIndicatorType());
                }
                indicatorService.save(indicator);
            }
        }

        log.info("Fetched indicators: {}", indicators.size());
    }
}
