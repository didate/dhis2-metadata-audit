package com.didate.service.dhis2;

import com.didate.domain.Dataelement;
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
public class Dhis2Service {

    private static final Logger log = LoggerFactory.getLogger(Dhis2Service.class);

    private final ObjectMapper objectMapper;

    public Dhis2Service(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Dataelement> getDataElements() throws IOException {
        OkHttpClient client = OkHttpClientConfig.createClient("");
        String url = OkHttpClientConfig.BASE_URL + "dataElements?fields=*";

        List<Dataelement> dataElements = new ArrayList<>();
        DataElementResponse dataElementResponse = new DataElementResponse();
        dataElementResponse.setPager(new Pager().nextPage(url));

        // Fetch and process pages while any
        while (
            dataElementResponse.getPager() != null &&
            dataElementResponse.getPager().getNextPage() != null &&
            !"".equals(dataElementResponse.getPager().getNextPage())
        ) {
            dataElementResponse = fetchDataElementsPage(client, dataElementResponse.getPager().getNextPage());
            dataElements.addAll(dataElementResponse.getDataElements());
        }

        return dataElements;
    }

    private DataElementResponse fetchDataElementsPage(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Failed to fetch data elements: HTTP {}", response.code());
                throw new IOException("Unexpected code " + response);
            }

            String jsonResponse = response.body().string();
            return objectMapper.readValue(jsonResponse, DataElementResponse.class);
        } catch (IOException e) {
            log.error("Error during API call to {}", url, e);
            throw e;
        }
    }
}
