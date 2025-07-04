package com.example.workleap.ui.viewmodel;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.request.EmailOtpRequest;
import com.example.workleap.data.model.request.EmailRequest;
import com.example.workleap.data.model.request.LoginRequest;
import com.example.workleap.data.model.response.LoginResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.request.RegisterRequest;
import com.example.workleap.data.model.response.RegisterResponse;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.repository.UserRepository;
import com.example.workleap.utils.ToastUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<String> checkExistResult = new MutableLiveData<>();
    private MutableLiveData<Integer> checkExistData = new MutableLiveData<>();
    private MutableLiveData<String> sendOtpResult = new MutableLiveData<>();
    private MutableLiveData<String> reSendOtpResult = new MutableLiveData<>();
    private MutableLiveData<String> verifyOtpResult = new MutableLiveData<>();
    private MutableLiveData<Integer> verifyOtpData = new MutableLiveData<>();
    private MutableLiveData<String> registerResult = new MutableLiveData<>();
    private MutableLiveData<String> loginResult = new MutableLiveData<>();
    private MutableLiveData<User> loginUser = new MutableLiveData<>();
    private MutableLiveData<String> logoutResult = new MutableLiveData<>();

    public AuthViewModel() {
    }

    // Getter cho LiveData
    public LiveData<String> getRegisterResult() {
        return registerResult;
    }
    public void ResetRegisterResult(){registerResult.setValue(null);}
    public LiveData<String> getCheckExistResult() { return checkExistResult;}
    public LiveData<Integer> getCheckExistData() { return checkExistData;}
    public void ResetGetCheckExistData() { checkExistData.setValue(null);}
    public LiveData<String> getSendOtpResult() { return sendOtpResult;}
    public void ResetSendOtpResult(){sendOtpResult.setValue(null);}
    public LiveData<String> getReSendOtpResult() { return reSendOtpResult;}
    public LiveData<String> getVerifyOtpResult() { return verifyOtpResult; }
    public LiveData<Integer> getVerifyOtpData() { return verifyOtpData; }
    public void ResetVerifyOtpData(){verifyOtpData.setValue(null);}

    public LiveData<String> getLoginResult() {
        return loginResult;
    }
    public LiveData<User> getLoginUser() { return loginUser; }
    public LiveData<String> getLogoutResult() {
        return logoutResult;
    }

    public void InitiateRepository(Context context) {
        userRepository = new UserRepository(context);
    }

    //Xác thực người dùng
    public void checkUserExist(String username, String password, String confirmPassword, String email, String phoneNumber, String role)
    {   RegisterRequest request = new RegisterRequest(username, password, confirmPassword, email, phoneNumber, role);
        Call<MessageResponse> call = userRepository.checkUserExist(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse checkResponse = response.body();
                    checkExistResult.setValue(checkResponse.getMessage());
                    checkExistData.setValue(1);
                } else {
                    checkExistData.setValue(0);
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        Log.d("authviewmodel", "error: " + error.getMessage());
                    } catch (Exception e) {
                        Log.d("authviewmodel", "Loi khong xac dinh");
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d("authviewmodel","Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Send
    public void sendOtp(String email) {
        EmailRequest request = new EmailRequest(email);
        Call<MessageResponse> call = userRepository.sendOtp(request);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse sendResponse = response.body();
                    sendOtpResult.setValue(sendResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        sendOtpResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        sendOtpResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
                //reset sau khoang thoi gian
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    sendOtpResult.setValue(null);
                }, 300);
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                sendOtpResult.setValue("Lỗi kết nối: " + t.getMessage());

                //reset
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    sendOtpResult.setValue(null);
                }, 300);
            }
        });
    }


    //Resend
    public void reSendOtp(String email)
    {   EmailRequest request = new EmailRequest(email);
        Call<MessageResponse> call = userRepository.resendOtp(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse sendResponse = response.body();
                    reSendOtpResult.setValue(sendResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        reSendOtpResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        reSendOtpResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                reSendOtpResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Verify
    public void verifyOtp(String email, String otp)
    {   EmailOtpRequest request = new EmailOtpRequest(email, otp);
        Call<MessageResponse> call = userRepository.verifyOtp(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse sendResponse = response.body();
                    verifyOtpResult.setValue(sendResponse.getMessage());
                    verifyOtpData.setValue(1);
                } else {
                    verifyOtpData.setValue(0);
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        Log.d("authviewmodel", "error: " + error.getMessage());
                    } catch (Exception e) {
                        Log.d("authviewmodel", "Loi khong xac dinh");
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                verifyOtpResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Đăng ký người dùng
    public void register(String username, String password, String confirmPassword, String email, String phoneNumber, String role) {
        RegisterRequest request = new RegisterRequest(username, password, confirmPassword, email, phoneNumber, role);
        Call<RegisterResponse> call = userRepository.registerUser(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    registerResult.setValue(registerResponse.getMessage() + " - Username: " + registerResponse.getUser().getUsername());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBodyString = response.errorBody().string();
                            Log.d("API_ERROR", "Lỗi từ server: " + errorBodyString);
                        } else {
                            Log.d("API_ERROR", "Không có errorBody");
                        }
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        Log.d("authviewmodel", "error: " + error.getMessage());
                    } catch (Exception e) {
                        Log.d("authviewmodel", "Loi khong xac dinh");
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("authviewmodel", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Đăng nhập người dùng
    public void login(String username, String email, String password) {
        LoginRequest request = new LoginRequest(username, email, password);
       userRepository.loginUser(request, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();

                    if(loginResponse.getUser()==null) Log.e("authviewmodel", "user null");

                    loginResult.setValue("Log in successfull, welcome " + loginResponse.getUser().getUsername());
                    loginUser.setValue(loginResponse.getUser());

                } else {
                    loginUser.setValue(null);
                    try {
                        loginResult.setValue("Wrong user name or password");
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        //loginResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        loginResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.setValue("Wrong user name or password");
            }
        });
    }

    // Đăng xuất người dùng
    public void logout() {
        Call<MessageResponse> call = userRepository.logoutUser();
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