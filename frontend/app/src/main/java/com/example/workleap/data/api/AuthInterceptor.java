package com.example.workleap.data.api;

import android.util.Log;

import com.example.workleap.ui.view.auth.TokenProvider;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenProvider tokenProvider;

    public AuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = tokenProvider.getToken(); // lấy token mới nhất mỗi lần gửi

        //Gui request ko token neu token null
        Request originalRequest = chain.request();
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
