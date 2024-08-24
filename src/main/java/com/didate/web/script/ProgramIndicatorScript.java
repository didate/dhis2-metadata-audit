package com.didate.web.script;

import com.didate.domain.ProgramIndicator;
import com.didate.domain.Project;
import com.didate.service.ProgramIndicatorService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramIndicatorScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramIndicatorScript.class);

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
        log.info("Calling programIndicators API...");

        List<ProgramIndicator> programIndicators = programIndicatorApiService.getData(
            project,
            "programIndicators",
            new TypeReference<Dhis2ApiResponse<ProgramIndicator>>() {}
        );

        for (ProgramIndicator indicator : programIndicators) {
            if (!programIndicatorService.exist(indicator.getId())) {
                programIndicatorService.save(indicator);
            }
        }

        log.info("Fetched program indicators: {}", programIndicators.size());
    }
}
