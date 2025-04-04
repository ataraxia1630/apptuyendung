package com.example.workleap.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.LoginResponse;
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

    // Phương thức để đăng ký người dùng
    public Call<Void> registerUser(User user) {
        return apiService.registerUser(user);
    }

    // Phương thức để đăng nhập người dùng
    public Call<LoginResponse> loginUser(User user) {
        return apiService.loginUser(user);
    }

    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> users = new MutableLiveData<>();
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users.setValue(response.body());
            }

            @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
                users.setValue(null);
            }
        });
        return users;
    }
}

