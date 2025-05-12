package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.UpdateUserRequest;
import com.example.workleap.data.model.response.UpdateUserResponse;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.repository.UserRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> getUserData = new MutableLiveData<>();
    private MutableLiveData<String> getUserResult = new MutableLiveData<>();
    private MutableLiveData<String> updateUserResult = new MutableLiveData<>();

    public UserViewModel() {}
    public void InitiateRepository(Context context) {
        userRepository = new UserRepository(context);
    }

    // Getter cho LiveData
    public LiveData<User> getGetUserData() {
        return getUserData;
    }
    public LiveData<String> getGetUserResult() {
        return getUserResult;
    }
    public LiveData<String> getUpdateUserResult() {
        return updateUserResult;
    }

    //Get user
    public void getUser(String id) {
        Log.e("getuser", "getuser");
        Call<GetUserResponse> call = userRepository.getUser(id);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("success", "succes");
                    GetUserResponse getResponse = response.body();
                    getUserResult.setValue(getResponse.getMessage());
                    getUserData.setValue(getResponse.getUser());
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        Log.e("ErrorBody", errorJson);
                        GetUserResponse error = new Gson().fromJson(response.errorBody().string(), GetUserResponse.class);
                        getUserResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getUserResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                getUserResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Update người dùng
    public void updateUser(String id, String username, String password, String email, String phoneNumber, String avatar, String background) {
        UpdateUserRequest request = new UpdateUserRequest(username, password, email, phoneNumber, avatar, background);
        Call<UpdateUserResponse> call = userRepository.updateUser(id, request);
        call.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if (response.isSuccessful()) {
                    UpdateUserResponse updateResponse = response.body();
                    updateUserResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateUserResponse error = new Gson().fromJson(response.errorBody().string(), UpdateUserResponse.class);
                        updateUserResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateUserResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                updateUserResult.setValue("Lỗi kết nối: " + t.getMessage());

            }
        });
    }
}