package com.example.workleap.data.api;

import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetCompanyResponse;
import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.UpdateCompanyRequest;
import com.example.workleap.data.model.UpdateCompanyResponse;
import com.example.workleap.data.model.UpdateUserRequest;
import com.example.workleap.data.model.UpdateUserResponse;
import com.example.workleap.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    //Đăng ký
    @POST("api/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    //Đăng nhập
    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    //Đăng xuất
    @POST("api/auth/logout")
    Call<MessageResponse> logoutUser(@Body LogoutRequest request);


    //User
    @PUT("api/user/{id}")
    Call<UpdateUserResponse> updateUser(@Path("id") String id, @Body UpdateUserRequest request);

    //Applicant
    @GET("api/applicant/{id}")
    Call<GetApplicantResponse> getApplicant(@Path("id") String id);
    @PUT("api/applicant/{id}")
    Call<UpdateApplicantResponse> updateApplicant(@Path("id") String id, @Body UpdateApplicantRequest request);

    //Company
    @GET("api/company/{id}")
    Call<GetCompanyResponse> getCompany(@Path("id") String id);
    @PUT("api/company/{id}")
    Call<UpdateCompanyResponse> updateCompany(@Path("id") String id, @Body UpdateCompanyRequest request);
}
