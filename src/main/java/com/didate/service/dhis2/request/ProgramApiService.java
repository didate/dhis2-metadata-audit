package com.didate.service.dhis2.request;

import com.didate.domain.Program;
import com.didate.domain.Project;
import com.didate.service.dhis2.DhisServiceUtil;
import com.didate.service.dhis2.OkHttpClientConfig;
import com.didate.service.dhis2.response.ProgramResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class ProgramApiService {

    private final ObjectMapper objectMapper;

    public ProgramApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Program> getPrograms(Project project) throws IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());
        String url = project.getDhis2URL() + "/api/programs?fields=*";
        List<Program> programs = new ArrayList<>();

        // Fetch and process pages while any
        ProgramResponse programResponse = DhisServiceUtil.fetchPage(client, url, ProgramResponse.class, objectMapper);
        programs.addAll(programResponse.getPrograms());

        while (DhisServiceUtil.hasNextPage(programResponse.getPager())) {
            programResponse = DhisServiceUtil.fetchPage(
                client,
                programResponse.getPager().getNextPage(),
                ProgramResponse.class,
                objectMapper
            );
            programs.addAll(programResponse.getPrograms());
        }

        return programs;
    }
}
