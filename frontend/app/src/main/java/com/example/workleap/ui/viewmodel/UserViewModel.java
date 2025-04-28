package com.example.workleap.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.UpdateUserRequest;
import com.example.workleap.data.model.UpdateUserResponse;
import com.example.workleap.data.repository.UserRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<String> updateResult = new MutableLiveData<>();

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    // Getter cho LiveData
    public LiveData<String> getUpdateResult() {
        return updateResult;
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
                    updateResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateUserResponse error = new Gson().fromJson(response.errorBody().string(), UpdateUserResponse.class);
                        updateResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                updateResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}