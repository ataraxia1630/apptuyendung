package com.example.workleap.data.api;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobSaved;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.entity.Message;
import com.example.workleap.data.model.entity.Notification;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.Reaction;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.data.model.request.CVRequest;
import com.example.workleap.data.model.request.CreateApplicantEducationRequest;
import com.example.workleap.data.model.request.EmailOtpRequest;
import com.example.workleap.data.model.request.EmailRequest;
import com.example.workleap.data.model.request.FCMRequest;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.data.model.request.GroupChatRequest;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.data.model.request.ListFieldIdRequest;
import com.example.workleap.data.model.request.ProcessCvAppliedRequest;
import com.example.workleap.data.model.request.ListMemberIdRequest;
import com.example.workleap.data.model.request.StatusRequest;
import com.example.workleap.data.model.request.UserIdRequest;
import com.example.workleap.data.model.response.CVResponse;
import com.example.workleap.data.model.response.CommentResponse;
import com.example.workleap.data.model.response.ConversationResponse;
import com.example.workleap.data.model.response.CreateApplicantEducationResponse;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.request.CreateApplicantSkillRequest;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateApplicantSkillResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.model.response.FCMResponse;
import com.example.workleap.data.model.response.FieldResponse;
import com.example.workleap.data.model.response.GetApplicantResponse;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.response.GetUserResponse;
import com.example.workleap.data.model.request.LoginRequest;
import com.example.workleap.data.model.response.ImageUrlResponse;
import com.example.workleap.data.model.response.JobAppliedResponse;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListApplicantEducationResponse;
import com.example.workleap.data.model.response.ListCommentResponse;
import com.example.workleap.data.model.response.ListConversationUserResponse;
import com.example.workleap.data.model.response.ListEducationResponse;
import com.example.workleap.data.model.response.ListExperienceResponse;
import com.example.workleap.data.model.response.ListFieldResponse;
import com.example.workleap.data.model.response.ListFieldStatResponse;
import com.example.workleap.data.model.response.ListFollowerResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListMessageResponse;
import com.example.workleap.data.model.response.ListMonthlyStatResponse;
import com.example.workleap.data.model.response.ListNotificationResponse;
import com.example.workleap.data.model.response.ListPostResponse;
import com.example.workleap.data.model.response.ListReportResponse;
import com.example.workleap.data.model.response.ListSkillResponse;
import com.example.workleap.data.model.response.ListCVResponse;
import com.example.workleap.data.model.response.ListJobAppliedResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.ListTopCompanyResponse;
import com.example.workleap.data.model.response.ListTopJobPostResponse;
import com.example.workleap.data.model.response.LoginResponse;
import com.example.workleap.data.model.request.LogoutRequest;
import com.example.workleap.data.model.response.MessageChatResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.request.RegisterRequest;
import com.example.workleap.data.model.response.OverviewResponse;
import com.example.workleap.data.model.response.PostResponse;
import com.example.workleap.data.model.response.ReactionResponse;
import com.example.workleap.data.model.response.RegisterResponse;
import com.example.workleap.data.model.response.TopCompanyResponse;
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
import retrofit2.http.Query;

public interface ApiService {
    //Xac thuc
    @POST("api/auth/exist")
    Call<MessageResponse> checkUserExist(@Body RegisterRequest request);
    @POST("api/auth/send-otp")
    Call<MessageResponse> sendOtp(@Body EmailRequest request);
    @POST("api/auth/resend-otp")
    Call<MessageResponse> resendOtp(@Body EmailRequest request);
    @POST("api/auth/verify-otp")
    Call<MessageResponse> verifyOtp(@Body EmailOtpRequest request);

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
    //Follower
    @POST("api/followers/toggle/{followedId}")
    Call<MessageResponse> toggleFollow(@Path("followedId") String followedId);
    @GET("api/followers/list/following/{userId}")
    Call<ListFollowerResponse> getFollowing(@Path("userId") String userId);
    @GET("api/followers/list/followers/{userId}")
    Call<ListFollowerResponse> getFollowers(@Path("userId") String userId);
    //Avatar
    @Multipart
    @POST("api/avatar/upload")
    Call<GetUserResponse> uploadAvatar(@Part MultipartBody.Part file);
    @GET("api/avatar/url/{path}")
    Call<ImageUrlResponse> getAvatarUrl(@Path("path") String path);


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
    Call<ListJobPostResponse> getAllJobPosts(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/job-posts/company/{id}")
    Call<ListJobPostResponse> getJobPostsByCompany(@Path ("id") String id, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/job-posts/{id}")
    Call<JobPostResponse> getJobPostById(@Path("id") String id);
    @POST("api/job-posts")
    Call<JobPostResponse> createJobPost(@Body JobPost request);
    @PUT("api/job-posts/{id}")
    Call<JobPostResponse> updateJobPost(@Path("id") String id, @Body JobPost request);
    @DELETE("api/job-posts/{id}")
    Call<MessageResponse> deleteJobPost(@Path("id") String id);
    @GET("api/job-posts/search/query")
    Call<ListJobPostResponse> searchJobPosts(
            @Query("title") String query,
            @Query("location") String location,
            @Query("position") String position,
            @Query("educationRequirement") String educationRequirement,
            @Query("companyName") String companyName
    );
    @GET("api/job-posts/company/me/{id}")
    Call<JobPostResponse> getMyJobPostById( @Path("id") String id);
    @GET("api/job-posts/admin/by-status")
    Call<ListJobPostResponse> getJobPostByStatus(@Query("page") int page, @Query("pageSize") int pageSize, @Query("status") String status);
    @PUT("api/job-posts/admin/toggle/{id}")
    Call<JobPostResponse> toggleJobPostStatus(@Path("id") String id, @Body StatusRequest statusRequest);
    @GET("api/job-posts/recommend")
    Call<ListJobPostResponse> getJobPostRecommend(@Query("page") int page, @Query("pageSize") int pageSize);
    

    //JobType
    @GET("api/types/all")
    Call<ListJobTypeResponse> getAllJobTypes();
    @POST("api/types/")
    Call <ListJobTypeResponse> createJobType(@Body List<JobType> request);

    //JobCategory
    @GET("api/category/all")
    Call<ListJobCategoryResponse> getAllJobCategories();
    @POST("api/types/")
    Call <ListJobCategoryResponse> createJobCategory(@Body List<JobCategory> request);

    //JobSaved
    @GET("api/save/{applicantId}")
    Call<ListJobPostResponse> getJobSaved(@Path("applicantId") String applicantId);
    @POST("api/save/")
    Call <MessageResponse> createJobSaved(@Body JobSavedRequest request);
    @DELETE("api/save/{applicantId}/{jobpostId}")
    Call <MessageResponse> deleteJobSaved(@Path("applicantId") String applicantId, @Path("jobpostId") String jobpostId);

    //Job Appied
    @GET("api/apply/{jobpostId}/cvs") //Get all cv in job applied
    Call<ListJobAppliedResponse> getCvsJobApplied(@Path("jobpostId") String jobpostId);
    @GET("api/apply/{jobpostId}/applicants") //Get all applicant in job applied
    Call<ListJobAppliedResponse> getApplicantsJobApplied(@Path("jobpostId") String jobpostId);
    @GET("api/apply/{applicantId}")
    Call<ListJobAppliedResponse> getJobApplied(@Path("applicantId") String applicantId);
    @POST("api/apply/")            //create a job applied
    Call<MessageResponse> applyAJob(@Body ApplyAJobRequest request);
    @PUT("api/apply/process-cv")            //process cv applied
    Call<JobAppliedResponse> processCvApplied(@Body ProcessCvAppliedRequest request);

    @DELETE("api/apply/{applicantId}/{jobpostId}")
    Call<MessageResponse> withDrawCv(@Path("applicantId") String applicantId, @Path("jobpostId") String jobPostId);

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
    Call<ListPostResponse> getAllPosts(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/posts/{id}")
    Call<PostResponse> getPostById(@Path("id") String id);
    @GET("api/posts/company/{id}")
    Call<ListPostResponse> getPostsByCompany(@Path("id") String id, @Query("page") int page, @Query("pageSize") int pageSize);
    @POST("api/posts")
    Call<PostResponse> createPost(@Body Post request);
    @PUT("api/posts/{id}")
    Call<PostResponse> updatePost(@Path("id") String id, @Body Post request);
    @DELETE("api/posts/{id}")
    Call<MessageResponse> deletePost(@Path("id") String id);
    @Multipart
    @POST("api/posts/images/")
    Call<MessageResponse> uploadImage(
            @Part MultipartBody.Part file,
            @Part("postId") RequestBody postId,
            @Part("order") RequestBody order
    );
    @GET("api/posts/images/{filePath}")
    Call<ImageUrlResponse> getImageUrl(@Path("filePath") String filePath);
    @GET("api/posts/search/query")
    Call<ListPostResponse> searchPost(
            @Query("title") String query,
            @Query("companyName") String companyName);
    @GET("api/posts/status/{status}")
    Call<ListPostResponse> getPostByStatus(@Path("status") String status, @Query("page") int page, @Query("pageSize") int pageSize);
    @PUT("api/posts/status/{id}")
    Call<PostResponse> updatePostStatus(@Path("id") String id, @Body StatusRequest request);



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

    //Statistic
    @GET("api/statistic/overview")
    Call<OverviewResponse> getOverview();
    @GET("api/statistic/top-companies")
    Call<ListTopCompanyResponse> getTopCompany(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/statistic/top-jobposts")
    Call<ListTopJobPostResponse> getTopJobPost(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET("api/statistic/monthly-growth")
    Call<ListMonthlyStatResponse> getMonthlyGrowth();
    @GET("api/statistic/by-field")
    Call<ListFieldStatResponse> getByField(@Query("page") int page, @Query("pageSize") int pageSize);

    //Chat-Conversation
    // Lấy tất cả các cuộc trò chuyện của user
    @GET("api/chat/all")
    Call<ListConversationUserResponse> getAllChats();
    // Lấy tất cả đoạn chat có tin nhắn chưa đọc
    @GET("api/chat/unread")
    Call<ListConversationUserResponse> getAllUnreadChats();
    // Lấy tất cả các nhóm chat
    @GET("api/chat/group")
    Call<ListConversationUserResponse> getAllGroupChats();
    // Lấy thông tin một đoạn chat cụ thể theo ID
    @GET("api/chat/{chatId}")
    Call<ConversationResponse> getChatById(@Path("chatId") String chatId);
    // Lấy tất cả tin nhắn trong một cuộc trò chuyện
    @GET("api/chat/{chatId}/mess")
    Call<ListConversationUserResponse> getAllMessages(@Path("chatId") String chatId);

    // Tạo một cuộc trò chuyện mới (chat cá nhân)
    @POST("api/chat/")
    Call<ConversationResponse> createChat(@Body FriendIdRequest request);

    // Tạo một group chat mới
    @POST("api/chat/group")
    Call<ConversationResponse> createGroupChat(@Body GroupChatRequest request);

    // Cập nhật thông tin của một cuộc trò chuyện (đổi tên nhóm)
    @PUT("api/chat/{chatId}")
    Call<MessageResponse> updateChat(@Path("chatId") String chatId, @Body Conversation request);

    // Xoá một cuộc trò chuyện
    @DELETE("api/chat/{chatId}")
    Call<MessageResponse> deleteChat(@Path("chatId") String chatId);

    // Thêm thành viên vào nhóm (chỉ admin)
    @POST("api/chat/add-member/{chatId}")
    Call<MessageResponse> addMemberToGroupChat(@Path("chatId") String chatId, @Body ListMemberIdRequest members);

    // Xoá thành viên khỏi nhóm (chỉ admin)
    @PUT("api/chat/remove-member/{chatId}")
    Call<Void> removeMemberFromGroupChat(@Path("chatId") String chatId, @Body UserIdRequest request);

    // Tham gia vào nhóm chat
    @POST("api/chat/join")
    Call<Void> joinGroupChat(@Body UserIdRequest request);

    // Rời khỏi nhóm chat
    @PUT("api/chat/leave")
    Call<Void> leaveGroupChat(@Body UserIdRequest request);

    // Tắt thông báo nhóm chat
    @PUT("api/chat/mute")
    Call<Void> muteGroupChat(@Body UserIdRequest request);

    // Bật thông báo nhóm chat
    @PUT("api/chat/unmute")
    Call<Void> unmuteGroupChat(@Body UserIdRequest request);

    //Chat
    // Gửi tin nhắn mới
    @POST("api/mess/")
    Call<MessageChatResponse> sendMessage(@Body Message request);

    // Xoá tin nhắn theo ID
    @DELETE("api/mess/{id}")
    Call<MessageResponse> deleteMessage(@Path("id") String messageId);

    // Sửa tin nhắn
    @PUT("api/mess/{id}")
    Call<MessageResponse> editMessage(@Path("id") String messageId, @Body Message request);

    // Lấy danh sách tin nhắn của một cuộc trò chuyện
    @GET("api/mess/{chatId}")
    Call<ListMessageResponse> getMessagesByChatId(@Path("chatId") String chatId);


    //FCM
    @POST("api/fcm/")
    Call<FCMResponse> createFCM(@Body FCMRequest request);

    //Notification
    @GET("api/notification/")
    Call<ListNotificationResponse> getAllNotification();
    @DELETE("api/notification/{id}")
    Call<Void> deleteNotification(@Path("id") String id);

    //Report
    @GET("/api/reports/")
    Call<ListReportResponse> getAllReports();

}
