package com.didate.service.dhis2;

import com.didate.domain.Project;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

@Service
public class DhisApiService<T> {

    private final ObjectMapper objectMapper;

    public DhisApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<T> getData(Project project, String endpoint, String date, TypeReference<Dhis2ApiResponse<T>> typeReference)
        throws java.io.IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());

        String url = project.getDhis2URL() + "/api/" + endpoint + "?fields=*";
        if (!"".equals(date)) {
            url = url + "&filter=lastUpdated:gt:" + date;
        }

        List<T> data = new ArrayList<>();
        Dhis2ApiResponse<T> response = DhisServiceUtil.fetchPage(client, url, typeReference, objectMapper);

        data.addAll(response.getItems());

        while (DhisServiceUtil.hasNextPage(response.getPager())) {
            response = DhisServiceUtil.fetchPage(client, response.getPager().getNextPage(), typeReference, objectMapper);
            data.addAll(response.getItems());
        }

        return data;
    }

    public void disableUser(Project project, String userUID) throws java.io.IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());
        String url = project.getDhis2URL() + "/api/users/" + userUID + "/disabled";
        DhisServiceUtil.post(client, url, null);
    }
}
