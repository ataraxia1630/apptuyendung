package com.example.workleap.data.api;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Nếu không có token, gửi request không thêm header
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        // Thêm header Authorization
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }

    // Cập nhật token nếu cần (ví dụ: sau khi làm mới token)
    public void setToken(String token) {
        this.token = token;
    }
}