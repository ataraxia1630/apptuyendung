package com.example.workleap.data.api;

import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.Reaction;
import com.example.workleap.data.model.request.CVRequest;
import com.example.workleap.data.model.request.CreateApplicantEducationRequest;
import com.example.workleap.data.model.request.ListFieldIdRequest;
import com.example.workleap.data.model.response.CVResponse;
import com.example.workleap.data.model.response.CommentResponse;
import com.example.workleap.data.model.response.CreateApplicantEducationResponse;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.request.CreateApplicantSkillRequest;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateApplicantSkillResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.model.response.FieldResponse;
import com.example.workleap.data.model.response.GetApplicantResponse;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.LoginRequest;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListApplicantEducationResponse;
import com.example.workleap.data.model.response.ListCommentResponse;
import com.example.workleap.data.model.response.ListEducationResponse;
import com.example.workleap.data.model.response.ListExperienceResponse;
import com.example.workleap.data.model.response.ListFieldResponse;
import com.example.workleap.data.model.response.ListInterestedFieldResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListPostResponse;
import com.example.workleap.data.model.response.ListSkillResponse;
import com.example.workleap.data.model.response.ListCVResponse;
import com.example.workleap.data.model.response.ListJobAppliedResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.LoginResponse;
import com.example.workleap.data.model.request.LogoutRequest;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.request.RegisterRequest;
import com.example.workleap.data.model.response.PostResponse;
import com.example.workleap.data.model.response.ReactionResponse;
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

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @PUT("api/users/setting/{id}")
    Call<UpdateUserResponse> updateUserSetting(@Path("id") String id, @Body UpdateUserRequest request);


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
    @GET("api/skill/{applicantId}")
    Call<ListSkillResponse> getApplicantSkill(@Path("applicantId") String applicantId);

    @POST("api/skill/{applicantId}")
    Call<CreateApplicantSkillResponse> createApplicantSkill(@Path("applicantId") String applicantId, @Body CreateApplicantSkillRequest request);
    @PUT("api/skill/{id}")
    Call<UpdateApplicantSkillResponse> updateApplicantSkill(@Path("id") String id, @Body UpdateApplicantSkillRequest request);
    @DELETE("api/skill/{id}")
    Call<MessageResponse> deleteApplicantSkill(@Path("id") String id);
    @DELETE("api/skill/all/{applicantId}")
    Call<MessageResponse> deleteAllApplicantSkill(@Path("applicantId") String id);


    //Applicant Education
    @GET("api/education/")
    Call<ListEducationResponse> getAllEducation();
    @POST("api/education/")
    Call<MessageResponse> createEducation(@Body Education education);
    @GET("api/education/{applicantId}")
    Call<ListApplicantEducationResponse> getAllApplicantEducation(@Path("applicantId") String applicantId);
    @POST("api/education/{applicantId}")
    Call<CreateApplicantEducationResponse> createApplicantEducation(@Path("applicantId") String applicantId, @Body CreateApplicantEducationRequest request);
    @PUT("api/education/{id}")
    Call<UpdateApplicantEducationResponse> updateApplicantEducation(@Path("id") String id, @Body UpdateApplicantEducationRequest request);
    @DELETE("api/education/{id}")
    Call<MessageResponse> deleteApplicantEducation(@Path("id") String id);
    @DELETE("api/education/all/{applicantId}")
    Call<MessageResponse> deleteAllApplicantEducation(@Path("applicantId") String id);


    //Experience
    @GET("api/experience/{applicantId}")
    Call<ListExperienceResponse> getApplicantExperience(@Path("applicantId") String applicantId);
    @POST("api/experience/{applicantId}")
    Call<CreateApplicantExperienceResponse> createApplicantExperience(@Path("applicantId") String applicantId, @Body CreateApplicantExperienceRequest request);
    @PUT("api/experience/{id}")
    Call<UpdateApplicantExperienceResponse> updateApplicantExperience(@Path("id") String id, @Body UpdateApplicantExperienceRequest request);
    @DELETE("api/experience/{id}")
    Call<MessageResponse> deleteApplicantExperience(@Path("id") String id);


    //InterestedField
    @GET("api/fields/all")
    Call<ListFieldResponse> getAllFields();
    @GET("api/fields/{name}")
    Call<FieldResponse> getFieldByName(@Path("name") String name);
    @GET("api/fields/interested/{applicantId}")
    Call<ListFieldResponse> getInterestedField(@Path("applicantId") String applicantId);
    @POST("api/fields/interested/{applicantId}")
    Call<CreateInterestedFieldResponse> createInterestedField(@Path("applicantId") String applicantId, @Body ListFieldIdRequest request);
    @DELETE("api/fields/interested/{applicantId}/{fieldId}")
    Call<MessageResponse> deleteInterestedField(@Path("applicantId") String applicantId, @Path("fieldId") String fieldId);
    @DELETE("api/fields/interested/{applicantId}")
    Call<MessageResponse> deleteAllInterestedField(@Path("applicantId") String id);


    //JobPost
    @GET("api/job-posts/all")
    Call<ListJobPostResponse> getAllJobPosts();
    @GET("api/job-posts/{id}")
    Call<JobPostResponse> getJobPostById(@Path("id") String id);
    @POST("api/job-posts")
    Call<JobPostResponse> createJobPost(@Body JobPost request);
    @PUT("api/job-posts/{id}")
    Call<JobPostResponse> updateJobPost(@Path("id") String id, @Body JobPost request);
    @DELETE("api/job-posts/{id}")
    Call<MessageResponse> deleteJobPost(@Path("id") String id);
    @GET("api/job-posts/search/query")
    Call<ListJobPostResponse> searchJobPosts();

    //JobType
    @GET("api/types/all")
    Call<ListJobTypeResponse> getAllJobTypes();
    @POST("api/types/")
    Call <ListJobTypeResponse> createJobType(@Body List<JobType> request);

    //JobCategory
    @GET("api/category")
    Call<ListJobCategoryResponse> getAllJobCategories();
    @POST("api/types/")
    Call <ListJobCategoryResponse> createJobCategory(@Body List<JobCategory> request);

    //JobSaved
    @GET("api/save/{applicantId}")
    Call<ListJobPostResponse> createJobSaved(@Path("applicantId") String applicantId);
    @POST("api/save/")
    Call <ListJobPostResponse> createJobSaved(@Body JobPost request);
    @DELETE("api/save/{applicantId}/{jobpostId}")
    Call <MessageResponse> deleteJobSaved(@Path("applicantId") String applicantId, @Path("jobpostId") String jobpostId);

    //Job Appied
    @GET("api/apply/{jobpostId}/cvs") //Get all cv in job applied
    Call<ListJobAppliedResponse> getCvsJobApplied(@Path("jobpostId") String jobpostId);
    @GET("api/apply/{jobpostId}/applicants") //Get all applicant in job applied
    Call<ListJobAppliedResponse> getApplicantsJobApplied(@Path("jobpostId") String jobpostId);
    @GET("api/apply/{applicantId}/cvs")
    Call<ListJobAppliedResponse> getJobApplied(@Path("applicantId") String applicantId);
    @POST("api/apply/")            //create a job applied
    Call<MessageResponse> applyAJob(@Body JobApplied request);


    //Cv
    //@POST("api/cv/upload/{applicantId}")
    //Call<MessageResponse> createCv(@Path("applicantId") String applicantId, @Body CVRequest request);
    @Multipart
    @POST("api/cv/upload/{applicantId}")
    Call<MessageResponse> createCv(
            @Path("applicantId") String applicantId,
            @Part MultipartBody.Part file,
            @Part("title") RequestBody title
    );
    @GET("api/cv/all/{applicantId}")
    Call<ListCVResponse> getAllCv(@Path("applicantId") String applicantId);
    @DELETE("api/cv/all/{applicantId}")
    Call<MessageResponse> deleteAllCv(@Path("applicantId") String applicantId);
    @GET("api/cv/{id}")
    Call<CVResponse> getCvById(@Path("id") String id);
    @PUT("api/cv/{id}")
    Call<MessageResponse> updateCvById(@Path("id") String id, @Body CVRequest request);
    @DELETE("api/cv/{id}")
    Call<MessageResponse> deleteCvById(@Path("id") String id);


    //Post
    @GET("api/posts/all")
    Call<ListPostResponse> getAllPosts();
    @GET("api/posts/{id}")
    Call<ListPostResponse> getPostById(@Path("id") String id);
    @GET("api/posts/company/{id}")
    Call<ListPostResponse> getPostsByCompany(@Path("id") String id);
    @POST("api/posts")
    Call<PostResponse> createPost(@Body Post request);
    @PUT("api/posts/{id}")
    Call<PostResponse> updatePost(@Path("id") String id, @Body Post request);
    @DELETE("api/posts/{id}")
    Call<MessageResponse> deletePost(@Path("id") String id);

    //Comment
    @POST("api/comments/")
    Call<CommentResponse> createComment(@Body Comment request);
    @GET("api/comments/post/{postId}")
    Call<ListCommentResponse> getCommentsByPost(@Path("postId") String postId);
    @DELETE("api/comments/{id}")
    Call<MessageResponse> deleteComment(@Path("id") String id);

    //Reactions
    @POST("api/reactions/toggle")
    Call<ReactionResponse> toggleReaction(@Body Reaction request);
    @DELETE("api/reactions/{postId}")
    Call<MessageResponse> removeReaction(@Path("postId") String postId);
}
