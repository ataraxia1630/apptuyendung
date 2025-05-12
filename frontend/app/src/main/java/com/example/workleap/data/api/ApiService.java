package com.example.workleap.data.api;

import com.example.workleap.data.model.CreateApplicantEducationRequest;
import com.example.workleap.data.model.CreateApplicantEducationResponse;
import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetCompanyResponse;
import com.example.workleap.data.model.GetUserResponse;
import com.example.workleap.data.model.LoginRequest;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.data.model.LogoutRequest;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.RegisterRequest;
import com.example.workleap.data.model.RegisterResponse;
import com.example.workleap.data.model.UpdateApplicantEducationResponse;
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
    @GET("api/users/{id}")
    Call<GetUserResponse> getUser(@Path("id") String id);
    @PUT("api/users/{id}")
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

    //Applicant Skill
    @POST("api/applicantSkill/{applicantId}")
    Call<CreateApplicantSkillResponse> createApplicantSkill(@Path("applicantId") String applicantId, @Body CreateApplicantSkillRequest request);

    @PUT("api/applicantSkill/{id}")
    Call<UpdateApplicantSkillResponse> updateApplicantSkill(@Path("id") String id, @Body UpdateApplicantSkillRequest request);

    @DELETE("api/applicantSkill/{id}")
    Call<MessageResponse> deleteApplicantSkill(@Path("id") String id);

    @DELETE("api/applicantSkill/all/{applicantId}")
    Call<MessageResponse> deleteAllApplicantSkill(@Path("applicantId") String id);


    //Applicant Education
    @POST("api/applicantEducation/{applicantId}")
    Call<CreateApplicantEducationResponse> createApplicantEducation(@Path("applicantId") String applicantId, @Body CreateApplicantEducationRequest request);

    @PUT("api/applicantEducation/{id}")
    Call<UpdateApplicantEducationResponse> updateApplicantEducation(@Path("id") String id, @Body UpdateApplicantEducationRequest request);

    @DELETE("api/applicantEducation/{id}")
    Call<MessageResponse> deleteApplicantEducation(@Path("id") String id);

    @DELETE("api/applicantEducation/all/{applicantId}")
    Call<MessageResponse> deleteAllApplicantEducation(@Path("applicantId") String id);

    //Experience
    @POST("api/exp/{applicantId}")
    Call<CreateApplicantExperienceResponse> createApplicantExperience(@Path("applicantId") String applicantId, @Body CreateApplicantExperienceRequest request);

    @PUT("api/exp/{id}")
    Call<UpdateApplicantExperienceResponse> updateApplicantExperience(@Path("id") String id, @Body UpdateApplicantExperienceRequest request);

    @DELETE("api/exp/{id}")
    Call<MessageResponse> deleteApplicantExperience(@Path("id") String id);

    //InterestedField
    @POST("api/interestedField/{applicantId}")
    Call<CreateInterestedFieldResponse> createInterestedField(@Path("fieldId") String applicantId);

    @DELETE("api/interestedField/{id}")
    Call<MessageResponse> deleteInterestedField(@Path("id") String id);

    @DELETE("api/interestedField/all/{applicantId}")
    Call<MessageResponse> deleteAllInterestedField(@Path("fieldId") String id);
}
