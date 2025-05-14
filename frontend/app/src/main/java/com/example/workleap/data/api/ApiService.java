package com.example.workleap.data.api;

import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.request.CreateApplicantEducationRequest;
import com.example.workleap.data.model.response.CreateApplicantEducationResponse;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.request.CreateApplicantSkillRequest;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateApplicantSkillResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.model.response.GetApplicantResponse;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.LoginRequest;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.LoginResponse;
import com.example.workleap.data.model.request.LogoutRequest;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.request.RegisterRequest;
import com.example.workleap.data.model.response.RegisterResponse;
import com.example.workleap.data.model.response.UpdateApplicantEducationResponse;
import com.example.workleap.data.model.request.UpdateApplicantEducationRequest;
import com.example.workleap.data.model.request.UpdateApplicantExperienceRequest;
import com.example.workleap.data.model.request.UpdateApplicantRequest;
import com.example.workleap.data.model.response.UpdateApplicantExperienceResponse;
import com.example.workleap.data.model.response.UpdateApplicantResponse;
import com.example.workleap.data.model.request.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.response.UpdateApplicantSkillResponse;
import com.example.workleap.data.model.request.UpdateCompanyRequest;
import com.example.workleap.data.model.response.UpdateCompanyResponse;
import com.example.workleap.data.model.request.UpdateUserRequest;
import com.example.workleap.data.model.response.UpdateUserResponse;

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
    @GET("api/users/applicant/{id}")
    Call<GetApplicantResponse> getApplicant(@Path("id") String id);
    @PUT("api/users/applicant/{id}")
    Call<UpdateApplicantResponse> updateApplicant(@Path("id") String id, @Body UpdateApplicantRequest request);


    //Company
    @GET("api/users/company/{id}")
    Call<GetCompanyResponse> getCompany(@Path("id") String id);
    @PUT("api/users/company/{id}")
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


    //JobPost
    @GET("api/jobPost/all")
    Call<ListJobPostResponse> getAllJobPosts();
    @GET("api/jobPost/{id}")
    Call<JobPostResponse> getJobPostById(@Path("id") String id);
    @POST("api/jobPost")
    Call<JobPostResponse> createJobPost(@Body JobPost request);
    @PUT("api/jobPost/{id}")
    Call<JobPostResponse> updateJobPost(@Path("id") String id, @Body JobPost request);
    @DELETE("api/jobPost/{id}")
    Call<MessageResponse> deleteJobPost(@Path("id") String id);
    @GET("api/jobPost/search/query")
    Call<ListJobPostResponse> searchJobPosts();
}
