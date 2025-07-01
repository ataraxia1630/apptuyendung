package com.example.workleap.data.api;

import android.content.Context;

import com.example.workleap.data.repository.PreferencesManager;
import com.example.workleap.ui.view.auth.TokenProvider;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8081/"; // Thay bằng URL thật của server

    public static Retrofit getClient(Context context) {

        PreferencesManager preferencesManager = new PreferencesManager(context);

        TokenProvider tokenProvider = () -> preferencesManager.getToken(); // lambda

        // Tạo OkHttpClient với AuthInterceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(tokenProvider))
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển JSON thành đối tượng Java
                    .build();
        }
        return retrofit;
    }

}