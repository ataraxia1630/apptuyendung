package com.example.workleap.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.AppCompatRadioButton$InspectionCompanion;

import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.request.EmailOtpRequest;
import com.example.workleap.data.model.request.EmailRequest;
import com.example.workleap.data.model.request.FCMRequest;
import com.example.workleap.data.model.request.StatusRequest;
import com.example.workleap.data.model.response.FCMResponse;
import com.example.workleap.data.model.entity.Message;
import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.LoginRequest;
import com.example.workleap.data.model.response.ImageUrlResponse;
import com.example.workleap.data.model.response.ListFollowerResponse;
import com.example.workleap.data.model.response.ListUserResponse;
import com.example.workleap.data.model.response.LoginResponse;
import com.example.workleap.data.model.request.LogoutRequest;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.request.RegisterRequest;
import com.example.workleap.data.model.response.RegisterResponse;
import com.example.workleap.data.model.request.UpdateUserRequest;
import com.example.workleap.data.model.response.UpdateUserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class UserRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;
    private Context context;
    public UserRepository(Context context) {
        this.context = context;
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(context).create(ApiService.class);
    }


    //Xac thuc
    public Call<MessageResponse> checkUserExist(RegisterRequest request) {
        return apiService.checkUserExist(request);
    }
    public Call<MessageResponse> sendOtp(EmailRequest request) {
        return apiService.sendOtp(request);
    }
    public Call<MessageResponse> resendOtp(EmailRequest request) {
        return apiService.resendOtp(request);
        }
    public Call<MessageResponse> verifyOtp(EmailOtpRequest request) {
        return apiService.verifyOtp(request);
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
                    apiService = RetrofitClient.getClient(context).create(ApiService.class);

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
    public Call<MessageResponse> logoutUser() {
        String currentToken = preferencesManager.getToken();
        LogoutRequest logoutRequest = new LogoutRequest(currentToken);
        preferencesManager.clearSession();
        apiService = RetrofitClient.getClient(context).create(ApiService.class);
        return apiService.logoutUser(logoutRequest);
    }

    //Get
    public Call<GetUserResponse> getUser(String id) {
        return apiService.getUser(id);
    }
    public Call<ListUserResponse> getAllUser() {
        return apiService.getAllUser();
    }

    //Update
    public Call<UpdateUserResponse> updateUser(String id, UpdateUserRequest request) {
        return apiService.updateUser(id, request);
    }

    //Update Setting
    public Call<UpdateUserResponse> updateUserSetting(String id, UpdateUserRequest request) {
        return apiService.updateUserSetting(id, request);
    }

    //Follower
    public Call<MessageResponse> toggleFollow(String followedId) {
        return apiService.toggleFollow(followedId);
    }
    public Call<ListFollowerResponse> getFollowing(String userId) {
        return apiService.getFollowing(userId);
    }
    public Call<ListFollowerResponse> getFollowers(String userId) {
        return apiService.getFollowers(userId);
    }

    public Call<FCMResponse> createFCM(FCMRequest request)
    {
        return apiService.createFCM(request);
    }

    //avatar
    public Call<GetUserResponse> loadAvatar(MultipartBody.Part file) {
        return apiService.uploadAvatar(file);
    }
    public Call<ImageUrlResponse> getAvatarUrl(String path) {
        return apiService.getAvatarUrl(path);
    }
    public Call<GetUserResponse> toggleUserAccountStatus(String id, StatusRequest request) {
        return apiService.toggleUserAccountStatus(id, request);
    }
}

