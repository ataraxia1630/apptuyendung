package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.MessageResponse;

import retrofit2.Call;

public class JobPostRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public JobPostRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    public Call<ListJobPostResponse> getAllJobPosts() {
        return apiService.getAllJobPosts();
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

    public Call<ListJobPostResponse> searchJobPosts() {
        return apiService.searchJobPosts();
    }
}
