package com.quanghuy.busmap.client;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestAPIClient {
    OkHttpClient client;
    final String REQUEST_HEADER_AUTH_NAME = "%s %s";
    String headerContentType;
    String accessToken;
    String tokenType;


    public RestAPIClient(String headerContentType) {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.headerContentType = headerContentType;
    }

    public RestAPIClient(String headerContentType, String tokenType, String accessToken) {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.headerContentType = headerContentType;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    public static final MediaType contentType
            = MediaType.parse("application/json; charset=utf-8");
//ok http
    public Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }


    public Response post(String url, String data) throws IOException {
        RequestBody body = RequestBody.create(contentType, data);
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response post(String url, RequestBody formBody) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response put(String url, String data) throws IOException {
        RequestBody body = RequestBody.create(contentType, data);
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public Response put(String url, RequestBody formBody) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .put(formBody)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("content-type", headerContentType)
                .addHeader("Authorization", String.format(REQUEST_HEADER_AUTH_NAME, tokenType, accessToken))
                .url(url)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
