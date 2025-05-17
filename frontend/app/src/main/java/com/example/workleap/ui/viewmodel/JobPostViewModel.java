package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.repository.JobPostRepository;
import com.example.workleap.data.repository.UserRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobPostViewModel  extends ViewModel {
    private JobPostRepository jobPostRepository;

    public JobPostViewModel() { }

    public void InitiateRepository(Context context) {
        jobPostRepository = new JobPostRepository(context);
    }

    //job post
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

    //jobcategory, jobcategory, jobsave
    private MutableLiveData<List<JobType>> getAllJobTypeData = new MutableLiveData<>();
    private MutableLiveData<List<JobCategory>> getAllJobCategoryData = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> getAllJobSaved = new MutableLiveData<>();

    private MutableLiveData<String> getAllJobTypeResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobTypeResult = new MutableLiveData<>();
    private MutableLiveData<String> getAllJobCategoryResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobCategoryResult = new MutableLiveData<>();
    private MutableLiveData<String> getAllJobSavedResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobSavedResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteJobSavedResult = new MutableLiveData<>();


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
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
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

    //Job type
    public void getAllJobType() {
        jobPostRepository.getAllJobTypes().enqueue(new Callback<ListJobTypeResponse>() {

            @Override
            public void onResponse(Call<ListJobTypeResponse> call, Response<ListJobTypeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getAllJobTypeData.postValue(response.body().getAllJobType());
                    getAllJobTypeResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getAllJobTypeResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobTypeResponse> call, Throwable t) {
                getAllJobTypeResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void createJobType(List<JobType> request) {
        jobPostRepository.createJobType(request).enqueue(new Callback<ListJobTypeResponse>() {
            @Override
            public void onResponse(Call<ListJobTypeResponse> call, Response<ListJobTypeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createJobTypeResult.postValue("Success");
                } else {
                    createJobTypeResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobTypeResponse> call, Throwable t) {
                createJobTypeResult.postValue("Error: " + t.getMessage());
            }
                });
    }

    //Job category
    public void getAllJobCategory() {
        jobPostRepository.getAllJobCategories().enqueue(new Callback<ListJobCategoryResponse>() {
            @Override
            public void onResponse(Call<ListJobCategoryResponse> call, Response<ListJobCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getAllJobCategoryData.postValue(response.body().getAllJobCategory());
                    getAllJobCategoryResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getAllJobCategoryResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobCategoryResponse> call, Throwable t) {
                getAllJobCategoryResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void createJobCategory(List<JobCategory> request) {
        jobPostRepository.createJobCategory(request).enqueue(new Callback<ListJobCategoryResponse>() {
            @Override
            public void onResponse(Call<ListJobCategoryResponse> call, Response<ListJobCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createJobCategoryResult.postValue("Success");
                } else {
                    createJobCategoryResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobCategoryResponse> call, Throwable t) {
                createJobCategoryResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    //Job saved
    public void getAllJobSaved(String applicantId) {
        jobPostRepository.createJobSaved(applicantId).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response){
                if (response.isSuccessful() && response.body() != null) {
                    getAllJobSaved.postValue(response.body().getAllJobPost());
                    getAllJobSavedResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                    } else {
                    getAllJobSavedResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                getAllJobSavedResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void createJobSaved(JobPost request) {
        jobPostRepository.createJobSaved(request).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createJobSavedResult.postValue("Success");
                } else {
                    createJobSavedResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                createJobSavedResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void deleteJobSaved(String applicantId, String jobpostId) {
        jobPostRepository.deleteJobSaved(applicantId, jobpostId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deleteJobSavedResult.postValue("Success");
                } else {
                    deleteJobSavedResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteJobPostResult.postValue("Error: " + t.getMessage());
            }
        });
    }

}
