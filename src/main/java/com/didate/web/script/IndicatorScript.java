package com.didate.web.script;

import com.didate.domain.Indicator;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.DHISUserService;
import com.didate.service.IndicatorService;
import com.didate.service.IndicatortypeService;
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
public class IndicatorScript {

    private static final Logger log = LoggerFactory.getLogger(IndicatorScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<Indicator> indicatorApiService;
    private final IndicatorService indicatorService;

    public IndicatorScript(DhisApiService<Indicator> indicatorApiService, IndicatorService indicatorService) {
        this.indicatorApiService = indicatorApiService;
        this.indicatorService = indicatorService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling indicators API...");

        boolean hasExistingIndicators = indicatorService.count() > 0;
        String lastUpdated = hasExistingIndicators ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<Indicator> indicators = indicatorApiService.getData(
            project,
            "indicators",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<Indicator>>() {}
        );

        for (Indicator indicator : indicators) {
            TypeTrack typeTrack = determineTypeTrack(indicator.getId(), hasExistingIndicators);
            indicator = indicator.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                indicatorService.partialUpdate(indicator);
            } else {
                indicatorService.save(indicator);
            }
        }

        log.info("Fetched indicators: {}", indicators.size());
    }

    private TypeTrack determineTypeTrack(String indicatorId, boolean hasExistingIndicators) {
        if (!hasExistingIndicators) {
            return TypeTrack.NONE;
        }
        return indicatorService.exist(indicatorId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
