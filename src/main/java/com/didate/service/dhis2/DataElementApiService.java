package com.didate.service.dhis2;

import com.didate.domain.Dataelement;
import com.didate.domain.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataElementApiService {

    private static final Logger log = LoggerFactory.getLogger(DataElementApiService.class);

    private final ObjectMapper objectMapper;

    public DataElementApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Dataelement> getDataElements(Project project) throws IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());
        String url = project.getDhis2URL() + "/api/dataElements?fields=*";

        List<Dataelement> dataElements = new ArrayList<>();
        // Fetch and process pages while any
        DataElementResponse dataElementResponse = DhisServiceUtil.fetchPage(client, url, DataElementResponse.class, objectMapper);
        dataElements.addAll(dataElementResponse.getDataElements());

        while (DhisServiceUtil.hasNextPage(dataElementResponse.getPager())) {
            dataElementResponse = DhisServiceUtil.fetchPage(
                client,
                dataElementResponse.getPager().getNextPage(),
                DataElementResponse.class,
                objectMapper
            );
            dataElements.addAll(dataElementResponse.getDataElements());
        }

        return dataElements;
    }
}
