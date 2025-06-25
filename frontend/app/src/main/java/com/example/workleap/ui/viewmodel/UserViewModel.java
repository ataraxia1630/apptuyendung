package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.UserToken;
import com.example.workleap.data.model.request.FCMRequest;
import com.example.workleap.data.model.response.FCMResponse;
import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.UpdateUserRequest;
import com.example.workleap.data.model.response.ListFollowerResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.UpdateUserResponse;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.repository.UserRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> getUserData = new MutableLiveData<>();
    private MutableLiveData<String> getUserResult = new MutableLiveData<>();
    private MutableLiveData<String> updateUserResult = new MutableLiveData<>();
    private MutableLiveData<String> toggleFollowResult = new MutableLiveData<>();
    private MutableLiveData<String> getFollowingResult = new MutableLiveData<>();
    private MutableLiveData<String> getFollowerResult = new MutableLiveData<>();
    private MutableLiveData<List<Follower>> getFollowingData = new MutableLiveData<>();
    private MutableLiveData<List<Follower>> getFollowerData = new MutableLiveData<>();
    public MutableLiveData<UserToken> createFCMData = new MutableLiveData<>();
    public MutableLiveData<String> createFCMResult = new MutableLiveData<>();

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
    public LiveData<String> getToggleFollowResult() { return toggleFollowResult; }
    public LiveData<String> getGetFollowingResult() { return getFollowingResult; }
    public LiveData<String> getGetFollowerResult() { return getFollowerResult; }
    public LiveData<List<Follower>> getGetFollowingData() { return getFollowingData; }
    public LiveData<List<Follower>> getGetFollowerData() { return getFollowerData; }
    public LiveData<UserToken> createFCMData() { return createFCMData; }

    public LiveData<String> createFCMResult(){ return createFCMResult; }


    //Get user
    public void getUser(String id) {
        Log.e("getuser", "getuser");
        Call<GetUserResponse> call = userRepository.getUser(id);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("userviewmodel", "succes");
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

    // Update Setting người dùng
    public void updateUserSetting(String id, String username, String password, String email, String phoneNumber, String avatar, String background) {
        UpdateUserRequest request = new UpdateUserRequest(username, password, email, phoneNumber, avatar, background);
        Call<UpdateUserResponse> call = userRepository.updateUserSetting(id, request);
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

    //Follower
    public void toggleFollow(String userId) {
        Call<MessageResponse> call = userRepository.toggleFollow(userId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    toggleFollowResult.setValue("Toggle follow success");
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        toggleFollowResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        toggleFollowResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                toggleFollowResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Get following
    public void getFollowing(String userId) {
        Call<ListFollowerResponse> call = userRepository.getFollowing(userId);
        call.enqueue(new Callback<ListFollowerResponse>() {
            @Override
            public void onResponse(Call<ListFollowerResponse> call, Response<ListFollowerResponse> response) {
                if (response.isSuccessful()) {
                    ListFollowerResponse getFollowing = response.body();
                    getFollowingData.setValue(getFollowing.getAllFollower());
                    getFollowingResult.setValue("Get Following Success");
                } else {
                    try {
                        ListFollowerResponse error = new Gson().fromJson(response.errorBody().string(), ListFollowerResponse.class);
                        getFollowingResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getFollowingResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListFollowerResponse> call, Throwable t) {
                getFollowingResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Get follower
    public void getFollower(String userId) {
        Call<ListFollowerResponse> call = userRepository.getFollowers(userId);
        call.enqueue(new Callback<ListFollowerResponse>() {
            @Override
            public void onResponse(Call<ListFollowerResponse> call, Response<ListFollowerResponse> response) {
                if (response.isSuccessful()) {
                    ListFollowerResponse getFollower = response.body();
                    getFollowerData.setValue(getFollower.getAllFollower());
                    getFollowerResult.setValue("Get Follower Success");
                } else {
                    try {
                        ListFollowerResponse error = new Gson().fromJson(response.errorBody().string(), ListFollowerResponse.class);
                        getFollowerResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getFollowerResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListFollowerResponse> call, Throwable t) {
                getFollowerResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void createFCM(String userId, String fcm_token) {
        FCMRequest request = new FCMRequest(userId, fcm_token);
        Call<FCMResponse> call = userRepository.createFCM(request);
        call.enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                if (response.isSuccessful()) {
                    FCMResponse getFollower = response.body();
                    createFCMData.setValue(getFollower.getUserToken());
                    createFCMResult.setValue("Success");
                } else {
                    try {
                        FCMResponse error = new Gson().fromJson(response.errorBody().string(), FCMResponse.class);
                        createFCMResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createFCMResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
                createFCMResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}