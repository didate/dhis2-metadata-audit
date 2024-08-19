package com.didate.service.dhis2;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientConfig {

    private OkHttpClientConfig() {}

    static final String BASE_URL = "";

    public static OkHttpClient createClient(String base64Token) {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request
                    .newBuilder()
                    .header("Authorization", "Basic " + base64Token)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");
                return chain.proceed(builder.build());
            }
        };

        return new OkHttpClient.Builder().addInterceptor(headerInterceptor).build();
    }
}
