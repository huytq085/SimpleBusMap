package com.quanghuy.busmap.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestAPIClient {
    OkHttpClient client;

    public RestAPIClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static final MediaType contentType
            = MediaType.parse("application/json; charset=utf-8");

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset=UTF-8")
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url, String data) throws IOException {
        RequestBody body = RequestBody.create(contentType, data);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset=UTF-8")
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String put(String url, String data) throws IOException {
        RequestBody body = RequestBody.create(contentType, data);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset=UTF-8")
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String delete(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset=UTF-8")
                .url(url)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
