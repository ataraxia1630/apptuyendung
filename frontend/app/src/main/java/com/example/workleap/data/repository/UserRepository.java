package com.example.workleap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.workleap.data.PreferencesManager;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.GetUserResponse;
import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.UpdateUserRequest;
import com.example.workleap.data.model.UpdateUserResponse;
import com.example.workleap.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public UserRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    //Đăng ký
    public Call<RegisterResponse> registerUser(RegisterRequest request) {
        return apiService.registerUser(request);
    }

    // Đăng nhập và lưu token
    public void loginUser(LoginRequest request, Callback<LoginResponse> callback) {
        Call<LoginResponse> call = apiService.loginUser(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu token
                    preferencesManager.saveToken(response.body().getToken());
                    //Luu user id
                    preferencesManager.saveUserId(response.body().getUser().getId());

                    // Cập nhật lại ApiService với token mới (nếu cần dùng ngay)
                    apiService = RetrofitClient.getClient(response.body().getToken()).create(ApiService.class);

                    // Gọi callback cho ViewModel/Activity
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Login failed: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }


    //Đăng xuất
    public Call<MessageResponse> logoutUser(LogoutRequest request) {
        preferencesManager.clearSession();
        return apiService.logoutUser(request);
    }

    //Get
    public Call<GetUserResponse> getUser(String id) {
        return apiService.getUser(id);
    }

    //Update
    public Call<UpdateUserResponse> updateUser(String id, UpdateUserRequest request) {
        return apiService.updateUser(id, request);
    }
}

