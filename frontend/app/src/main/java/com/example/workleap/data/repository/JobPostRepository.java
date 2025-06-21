package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.data.model.request.ProcessCvAppliedRequest;
import com.example.workleap.data.model.response.JobAppliedResponse;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobAppliedResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.MessageResponse;

import java.util.List;

import retrofit2.Call;

public class JobPostRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public JobPostRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    public Call<ListJobPostResponse> getAllJobPosts(int page, int pageSize) {
        return apiService.getAllJobPosts(page, pageSize);
    }

    public Call<ListJobPostResponse> getJobPostsByCompany(String id) {
        return apiService.getJobPostsByCompany(id);
    }

    public Call<JobPostResponse> getJobPostById(String id) {
        return apiService.getJobPostById(id);
    }

    public Call<JobPostResponse> createJobPost(JobPost request) {
        return apiService.createJobPost(request);
    }

    public Call<JobPostResponse> updateJobPost(String id, JobPost request) {
        return apiService.updateJobPost(id, request);
    }

    public Call<MessageResponse> deleteJobPost(String id) {
        return apiService.deleteJobPost(id);
    }

    public Call<ListJobPostResponse> searchJobPosts(String title, String location, String position, String educationRequirement, String companyName) {
        return apiService.searchJobPosts(title, location, position, educationRequirement, companyName);
    }

    public Call<JobPostResponse> getMyJobPostById(String id) {return apiService.getMyJobPostById(id);}

    //JobType
    public Call<ListJobTypeResponse> getAllJobTypes() {
        return apiService.getAllJobTypes();
    }
    public Call<ListJobTypeResponse> createJobType(List<JobType> request) {
        return apiService.createJobType(request);
    }

    //Job Category
    public Call<ListJobCategoryResponse> getAllJobCategories() {
        return apiService.getAllJobCategories();
    }
    public Call<ListJobCategoryResponse> createJobCategory(List<JobCategory> request) {
        return apiService.createJobCategory(request);
    }

    //Job saved
    public Call<ListJobPostResponse> createJobSaved(String applicantId) {
        return apiService.createJobSaved(applicantId);
    }
    public Call<ListJobPostResponse> createJobSaved(JobPost request) {
        return apiService.createJobSaved(request);
    }
    public Call<MessageResponse> deleteJobSaved(String applicantId, String jobpostId) {
        return apiService.deleteJobSaved(applicantId, jobpostId);
    }

    //Job Applied
    public Call<ListJobAppliedResponse> getCvsJobApplied(String jobpostId) {
        return apiService.getCvsJobApplied(jobpostId);
    }
    public Call<ListJobAppliedResponse> getApplicantsJobApplied(String jobpostId) {
        return apiService.getApplicantsJobApplied(jobpostId);
    }
    public Call<ListJobAppliedResponse> getJobApplied(String applicantId) {
        return apiService.getJobApplied(applicantId);
    }
    public Call<MessageResponse> applyAJob(ApplyAJobRequest request) {
        return apiService.applyAJob(request);
    }
    public Call<JobAppliedResponse> processCvApplied(ProcessCvAppliedRequest request) {
        return apiService.processCvApplied(request);
    }
}
