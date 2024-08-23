package com.didate.service.dhis2.request;

import com.didate.domain.Indicator;
import com.didate.domain.Project;
import com.didate.service.dhis2.DhisServiceUtil;
import com.didate.service.dhis2.OkHttpClientConfig;
import com.didate.service.dhis2.response.IndicatorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class IndicatorApiService {

    private final ObjectMapper objectMapper;

    public IndicatorApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Indicator> getIndicators(Project project) throws IOException {
        OkHttpClient client = OkHttpClientConfig.createClient(project.getToken());
        String url = project.getDhis2URL() + "/api/indicators?fields=*";
        List<Indicator> indicators = new ArrayList<>();

        // Fetch and process pages while any
        IndicatorResponse indicatorResponse = DhisServiceUtil.fetchPage(client, url, IndicatorResponse.class, objectMapper);
        indicators.addAll(indicatorResponse.getIndicators());

        while (DhisServiceUtil.hasNextPage(indicatorResponse.getPager())) {
            indicatorResponse =
                DhisServiceUtil.fetchPage(client, indicatorResponse.getPager().getNextPage(), IndicatorResponse.class, objectMapper);
            indicators.addAll(indicatorResponse.getIndicators());
        }

        return indicators;
    }
}
