package com.didate.web.script;

import com.didate.domain.ProgramStage;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramStageService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramStageScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramStageScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<ProgramStage> programStageApiService;
    private final ProgramStageService programStageService;

    public ProgramStageScript(DhisApiService<ProgramStage> programStageApiService, ProgramStageService programStageService) {
        this.programStageApiService = programStageApiService;
        this.programStageService = programStageService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling program stages API...");

        boolean hasExistingStages = programStageService.count() > 0;
        String lastUpdated = hasExistingStages ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<ProgramStage> programStages = programStageApiService.getData(
            project,
            "programStages",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<ProgramStage>>() {}
        );

        for (ProgramStage stage : programStages) {
            if (stage.getProgram() != null) {
                stage.setProgramStageDataElementsCount(stage.getProgramStageDataElements().size());

                stage.setProgramStageDataElementsContent(
                    stage.getProgramStageDataElements().stream().map(ds -> ds.getId()).collect(Collectors.joining("|"))
                );

                TypeTrack typeTrack = determineTypeTrack(stage.getId(), hasExistingStages);
                stage = stage.track(typeTrack).project(project);
                if (typeTrack == TypeTrack.UPDATE) {
                    programStageService.partialUpdate(stage);
                } else {
                    programStageService.save(stage);
                }
            }
        }

        log.info("Fetched program stages: {}", programStages.size());
    }

    private TypeTrack determineTypeTrack(String stageId, boolean hasExistingStages) {
        if (!hasExistingStages) {
            return TypeTrack.NONE;
        }
        return programStageService.exist(stageId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
