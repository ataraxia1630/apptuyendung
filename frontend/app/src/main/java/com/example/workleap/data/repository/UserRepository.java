package com.example.workleap.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.UpdateUserRequest;
import com.example.workleap.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;

    public UserRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    //Đăng ký
    public Call<RegisterResponse> registerUser(RegisterRequest request) {
        return apiService.registerUser(request);
    }

    //Đăng nhập
    public Call<LoginResponse> loginUser(LoginRequest request) {
        return apiService.loginUser(request);
    }

    //Đăng xuất
    public Call<MessageResponse> logoutUser(LogoutRequest request) {
        return apiService.logoutUser(request);
    }

    //Update
    public Call<MessageResponse> updateUser(String id, UpdateUserRequest request) {
        return apiService.updateUser(id, request);
    }
}

