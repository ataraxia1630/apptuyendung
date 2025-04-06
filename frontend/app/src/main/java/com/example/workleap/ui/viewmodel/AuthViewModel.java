package com.example.workleap.ui.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.repository.UserRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<String> registerResult = new MutableLiveData<>();
    private MutableLiveData<String> loginResult = new MutableLiveData<>();
    private MutableLiveData<String> logoutResult = new MutableLiveData<>();

    public AuthViewModel() {
        userRepository = new UserRepository();
    }

    // Getter cho LiveData
    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public LiveData<String> getLogoutResult() {
        return logoutResult;
    }

    // Đăng ký người dùng
    public void register(String username, String password, String email, String phoneNumber, String role) {
        RegisterRequest request = new RegisterRequest(username, password, email, phoneNumber, role);
        Call<RegisterResponse> call = userRepository.registerUser(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    registerResult.setValue(registerResponse.getMessage() + " - Username: " + registerResponse.getUser().getUsername());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        registerResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        registerResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registerResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Đăng nhập người dùng
    public void login(String username, String email, String password) {
        LoginRequest request = new LoginRequest(username, email, password);
        Call<LoginResponse> call = userRepository.loginUser(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    loginResult.setValue(loginResponse.getMessage() + " - Token: " + loginResponse.getToken());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        loginResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        loginResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Đăng xuất người dùng
    public void logout(String token) {
        LogoutRequest request = new LogoutRequest(token);
        Call<MessageResponse> call = userRepository.logoutUser(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse logoutResponse = response.body();
                    logoutResult.setValue(logoutResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        logoutResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        logoutResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                logoutResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}