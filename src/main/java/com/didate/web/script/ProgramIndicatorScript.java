package com.didate.web.script;

import com.didate.domain.ProgramIndicator;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramIndicatorService;
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
public class ProgramIndicatorScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramIndicatorScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<ProgramIndicator> programIndicatorApiService;
    private final ProgramIndicatorService programIndicatorService;

    public ProgramIndicatorScript(
        DhisApiService<ProgramIndicator> programIndicatorApiService,
        ProgramIndicatorService programIndicatorService
    ) {
        this.programIndicatorApiService = programIndicatorApiService;
        this.programIndicatorService = programIndicatorService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling program indicators API...");

        boolean hasExistingIndicators = programIndicatorService.count() > 0;
        String lastUpdated = hasExistingIndicators ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<ProgramIndicator> programIndicators = programIndicatorApiService.getData(
            project,
            "programIndicators",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<ProgramIndicator>>() {}
        );

        for (ProgramIndicator indicator : programIndicators) {
            TypeTrack typeTrack = determineTypeTrack(indicator.getId(), hasExistingIndicators);
            indicator = indicator.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                programIndicatorService.partialUpdate(indicator);
            } else {
                programIndicatorService.save(indicator);
            }
        }

        log.info("Fetched program indicators: {}", programIndicators.size());
    }

    private TypeTrack determineTypeTrack(String indicatorId, boolean hasExistingIndicators) {
        if (!hasExistingIndicators) {
            return TypeTrack.NONE;
        }
        return programIndicatorService.exist(indicatorId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
