package com.didate.service.dhis2.request;

import com.didate.domain.Dataelement;
import com.didate.domain.Project;
import com.didate.service.dhis2.DataElementResponse;
import com.didate.service.dhis2.DhisServiceUtil;
import com.didate.service.dhis2.OkHttpClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class DataElementApiService {

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
