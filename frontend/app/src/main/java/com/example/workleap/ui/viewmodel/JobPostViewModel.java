package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.repository.JobPostRepository;
import com.example.workleap.data.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobPostViewModel {
    private JobPostRepository jobPostRepository;

    public JobPostViewModel() { }

    public void initiateRepository(Context context) {
        jobPostRepository = new JobPostRepository(context);
    }

    private MutableLiveData<List<JobPost>> getAllJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobPost> getJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobPost> createJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobPost> updateJobPostData = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> searchJobPostData = new MutableLiveData<>();

    private MutableLiveData<String> getAllJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> getJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> updateJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> searchJobPostResult = new MutableLiveData<>();

    //Getter live data
    public LiveData<List<JobPost>> getAllJobPostData() { return getAllJobPostData; }
    public LiveData<JobPost> getJobPostData() { return getJobPostData; }

    public LiveData<String> getAllJobPostResult() { return getAllJobPostResult; }
    public LiveData<String> getJobPostResult() { return getJobPostResult; }
    public LiveData<String> getUpdateJobPostResult() { return updateJobPostResult; }

    public LiveData<String> getCreateJobPostResult() { return createJobPostResult; }
    public LiveData<String> getDeleteJobPostResult() { return deleteJobPostResult; }
    public LiveData<String> getSearchJobPostResult() { return searchJobPostResult; }
    public LiveData<List<JobPost>> getSearchJobPostData() { return searchJobPostData; }
    public LiveData<JobPost> getCreateJobPostData() { return createJobPostData; }
    public LiveData<JobPost> getUpdateJobPostData() { return updateJobPostData; }

    // API Calls
    public void getAllJobPosts() {
        jobPostRepository.getAllJobPosts().enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getAllJobPostData.postValue(response.body().getAllJobPost());
                    getAllJobPostResult.postValue("Success");
                } else {
                    getAllJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                getAllJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void getJobPostById(String id) {
        jobPostRepository.getJobPostById(id).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getJobPostData.postValue(response.body().getJobPost());
                    getJobPostResult.postValue("Success");
                } else {
                    getJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                getJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void createJobPost(JobPost request) {
        jobPostRepository.createJobPost(request).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createJobPostData.postValue(response.body().getJobPost());
                    createJobPostResult.postValue("Success");
                } else {
                    createJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                createJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void updateJobPost(String id, JobPost request) {
        jobPostRepository.updateJobPost(id, request).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateJobPostData.postValue(response.body().getJobPost());
                    updateJobPostResult.postValue("Success");
                } else {
                    updateJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                updateJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void deleteJobPost(String id) {
        jobPostRepository.deleteJobPost(id).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    deleteJobPostResult.postValue("Success");
                } else {
                    deleteJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void searchJobPosts() {
        jobPostRepository.searchJobPosts().enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchJobPostData.postValue(response.body().getAllJobPost());
                    searchJobPostResult.postValue("Success");
                } else {
                    searchJobPostResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                searchJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }
}
