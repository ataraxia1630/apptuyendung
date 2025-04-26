package com.example.workleap.data.api;

import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetApplicantSkillResponse;
import com.example.workleap.data.model.GetCompanyResponse;
import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.UpdateApplicantSkillResponse;
import com.example.workleap.data.model.UpdateCompanyRequest;
import com.example.workleap.data.model.UpdateCompanyResponse;
import com.example.workleap.data.model.UpdateUserRequest;
import com.example.workleap.data.model.UpdateUserResponse;

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

    //Skill
    @GET("api/applicantskill/{skillId}")
    Call<GetApplicantSkillResponse> getApplicantSkill(@Path("applicantId") String applicantId);

    @POST("api/applicantskill/{skillId}")
    Call<CreateApplicantSkillResponse> createApplicantSkill(@Path("skillId") String skillId, @Body CreateApplicantSkillRequest request);

    @PUT("api/applicantskill/{skillid}")
    Call<UpdateApplicantSkillResponse> updateApplicantSkill(@Path("skillId") String skillId, @Body UpdateApplicantSkillRequest request);

    @DELETE("api/applicantskill/{skillid}")
    Call<MessageResponse> deleteApplicantSkill(@Path("id") String id);

    @DELETE("api/applicantskill/all/{skillid}")
    Call<MessageResponse> deleteAllApplicantSkill(@Path("id") String id);

}
