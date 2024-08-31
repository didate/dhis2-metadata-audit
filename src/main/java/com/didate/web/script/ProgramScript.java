package com.didate.web.script;

import com.didate.domain.Program;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.ProgramService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.didate.web.rest.util.RemoveCommonWords;
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
public class ProgramScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<Program> programApiService;
    private final ProgramService programService;

    public ProgramScript(DhisApiService<Program> programApiService, ProgramService programService) {
        this.programApiService = programApiService;
        this.programService = programService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programs API...");

        boolean hasExistingPrograms = programService.count() > 0;
        String lastUpdated = hasExistingPrograms ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<Program> programs = programApiService.getData(
            project,
            "programs",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<Program>>() {}
        );

        for (Program program : programs) {
            program.setProgramIndicatorsCount(program.getProgramIndicators().size());
            program.setOrganisationUnitsCount(program.getOrganisationUnits().size());
            program.setProgramTrackedEntityAttributesCount(program.getProgramTrackedEntityAttributes().size());
            program.setProgramStagesCount(program.getProgramStages().size());

            program.setProgramIndicatorsContent(
                program.getProgramIndicators().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            program.setProgramIndicators(null);

            program.setOrganisationUnitsContent(
                program.getOrganisationUnits().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            program.setProgramTrackedEntityAttributesContent(
                program
                    .getProgramTrackedEntityAttributes()
                    .stream()
                    .map(ds -> ds.getId())
                    .collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            program.setProgramTrackedEntityAttributes(null);

            program.setProgramStagesContent(
                program.getProgramStages().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            program.setProgramStages(null);

            TypeTrack typeTrack = determineTypeTrack(program.getId(), hasExistingPrograms);
            program = program.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                programService.partialUpdate(program);
            } else {
                programService.save(program);
            }
        }

        log.info("Fetched programs: {}", programs.size());
    }

    private TypeTrack determineTypeTrack(String programId, boolean hasExistingPrograms) {
        if (!hasExistingPrograms) {
            return TypeTrack.NONE;
        }
        return programService.exist(programId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
