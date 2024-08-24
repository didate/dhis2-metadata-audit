package com.didate.web.script;

import com.didate.domain.Program;
import com.didate.domain.Project;
import com.didate.service.DHISUserService;
import com.didate.service.ProgramService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramScript {

    private static final Logger log = LoggerFactory.getLogger(ProgramScript.class);

    private final DhisApiService<Program> programApiService;
    private final ProgramService programService;
    private final DHISUserService dhisUserService;

    public ProgramScript(DhisApiService<Program> programApiService, ProgramService programService, DHISUserService dhisUserService) {
        this.programApiService = programApiService;
        this.programService = programService;
        this.dhisUserService = dhisUserService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling programs API...");

        List<Program> programs = programApiService.getData(project, "programs", new TypeReference<Dhis2ApiResponse<Program>>() {});

        for (Program program : programs) {
            if (!programService.exist(program.getId())) {
                if (!dhisUserService.exist(program.getCreatedBy().getId())) {
                    dhisUserService.save(program.getCreatedBy());
                }
                if (!dhisUserService.exist(program.getLastUpdatedBy().getId())) {
                    dhisUserService.save(program.getLastUpdatedBy());
                }

                programService.save(program);
            }
        }

        log.info("Fetched programs: {}", programs.size());
    }
}
