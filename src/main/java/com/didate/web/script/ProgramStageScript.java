package com.didate.web.script;

import com.didate.domain.ProgramStage;
import com.didate.domain.Project;
import com.didate.service.ProgramStageService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramStageScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramStageScript.class);

    private final DhisApiService<ProgramStage> programStageApiService;
    private final ProgramStageService programStageService;

    public ProgramStageScript(DhisApiService<ProgramStage> programStageApiService, ProgramStageService programStageService) {
        this.programStageApiService = programStageApiService;
        this.programStageService = programStageService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programStages API...");

        List<ProgramStage> programStages = programStageApiService.getData(
            project,
            "programStages",
            new TypeReference<Dhis2ApiResponse<ProgramStage>>() {}
        );

        for (ProgramStage stage : programStages) {
            if (!programStageService.exist(stage.getId())) {
                programStageService.save(stage);
            }
        }

        log.info("Fetched program stages: {}", programStages.size());
    }
}
