package com.didate.service.dhis2.request;

import com.didate.domain.Dataset;
import com.didate.domain.Project;
import com.didate.service.dhis2.DhisServiceUtil;
import com.didate.service.dhis2.OkHttpClientConfig;
import com.didate.service.dhis2.response.DataSetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class DatasetApiService {

    private final ObjectMapper objectMapper;

    public DatasetApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Dataset> getDataSets(Project project) throws IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());
        String url = project.getDhis2URL() + "/api/dataSets?fields=*";
        List<Dataset> dataSets = new ArrayList<>();

        // Fetch and process pages while any
        DataSetResponse dataSetResponse = DhisServiceUtil.fetchPage(client, url, DataSetResponse.class, objectMapper);
        dataSets.addAll(dataSetResponse.getDataSets());

        while (DhisServiceUtil.hasNextPage(dataSetResponse.getPager())) {
            dataSetResponse = DhisServiceUtil.fetchPage(
                client,
                dataSetResponse.getPager().getNextPage(),
                DataSetResponse.class,
                objectMapper
            );
            dataSets.addAll(dataSetResponse.getDataSets());
        }

        return dataSets;
    }
}
