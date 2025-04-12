package com.example.workleap.data.api;

import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    //Đăng ký
    @POST("api/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    //Đăng nhập
    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    //Đăng xuất
    @POST("api/auth/logout")
    Call<MessageResponse> logoutUser(@Body LogoutRequest request);
}
