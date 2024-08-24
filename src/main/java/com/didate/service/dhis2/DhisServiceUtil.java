package com.didate.service.dhis2;

import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DhisServiceUtil {

    private static final Logger log = LoggerFactory.getLogger(DhisServiceUtil.class);

    private DhisServiceUtil() {}

    public static <T> Dhis2ApiResponse<T> fetchPage(
        OkHttpClient client,
        String url,
        TypeReference<Dhis2ApiResponse<T>> typeReference,
        ObjectMapper objectMapper
    ) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, typeReference);
        }
    }

    public static boolean hasNextPage(Pager pager) {
        return pager != null && pager.getNextPage() != null && !"".equals(pager.getNextPage());
    }
}
