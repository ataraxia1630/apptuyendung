package com.example.workleap.data.api;

import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // API để tạo tài khoản (Register)
    @POST("register")
    Call<Void> registerUser(@Body User user);

    // API để đăng nhập (Login), tra ve token
    @POST("login")
    Call<LoginResponse> loginUser(@Body User user);

    // API lay danh sach user
    @GET("api/users")
    Call<List<User>> getUsers();
}
