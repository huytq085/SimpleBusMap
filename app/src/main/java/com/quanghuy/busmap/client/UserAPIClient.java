package com.quanghuy.busmap.client;

import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.utils.JsonUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Huy on 5/2/2018.
 */

public class UserAPIClient extends RestAPIClient {
    private static final String HEADER_CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String TOKEN_TYPE = "bearer";

    private static final String DOMAIN_PATTERN = "http://busmap.somee.com/%s";
    private static final String LOGIN_URL = "/token";
    private static final String SIGNUP_URL = "/api/User/AddUser";
    private static final String UPDATE_URL = "/api/User/UpdateUser";

    public UserAPIClient() {
        super(HEADER_CONTENT_TYPE);
    }
    public UserAPIClient(String accessToken) {
        super(HEADER_CONTENT_TYPE, TOKEN_TYPE, accessToken);
    }

    public User login(String username, String password) {
        User user = null;
        String url = String.format(DOMAIN_PATTERN, LOGIN_URL);
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("grant_type", "password")
                    .build();
            Response res = post(url, formBody);
            if (res.code() == 200){
                user = JsonUtils.decode(res.body().string(), User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
