package com.quanghuy.busmap.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestAPIClient {
    OkHttpClient client = new OkHttpClient();

    public RestAPIClient() {
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
