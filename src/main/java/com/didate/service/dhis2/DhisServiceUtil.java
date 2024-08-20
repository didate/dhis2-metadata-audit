package com.didate.service.dhis2;

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

    public static <T> T fetchPage(OkHttpClient client, String url, Class<T> responseType, ObjectMapper objectMapper) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Failed to fetch page: HTTP {}", response.code());
                throw new IOException("Unexpected code " + response);
            }
            String jsonResponse = response.body().string();
            return objectMapper.readValue(jsonResponse, responseType);
        } catch (IOException e) {
            log.error("Error during API call to {}", url, e);
            throw e;
        }
    }

    public static boolean hasNextPage(Pager pager) {
        return pager != null && pager.getNextPage() != null && !"".equals(pager.getNextPage());
    }
}
