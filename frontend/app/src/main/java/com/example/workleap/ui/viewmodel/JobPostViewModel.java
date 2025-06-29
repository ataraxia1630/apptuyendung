package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.data.model.request.ProcessCvAppliedRequest;
import com.example.workleap.data.model.request.StatusRequest;
import com.example.workleap.data.model.response.JobAppliedResponse;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListJobAppliedResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.repository.JobPostRepository;
import com.google.gson.Gson;

import java.io.IOException;
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
    private MutableLiveData<List<JobPost>> getJobPostsRecommendData = new MutableLiveData<>();
    private MutableLiveData<String> getJobPostsRecommendResult = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> getJobPostByStatusData = new MutableLiveData<>();
    private MutableLiveData<JobPost> toggleJobPostStatusData = new MutableLiveData<>();
    private MutableLiveData<String> toggleJobPostStatusResult = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> getJobPostsByCompanyData = new MutableLiveData<>();
    private MutableLiveData<JobPost> getJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobPost> getMyJobPostByIdData = new MutableLiveData<>();
    private MutableLiveData<JobPost> createJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobPost> updateJobPostData = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> searchJobPostData = new MutableLiveData<>();
    private MutableLiveData<JobApplied> processCvAppliedData = new MutableLiveData<>();

    private MutableLiveData<String> getAllJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> getJobPostByStatusResult = new MutableLiveData<>();
    private MutableLiveData<String> getJobPostsByCompanyResult = new MutableLiveData<>();
    private MutableLiveData<String> getJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> updateJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> searchJobPostResult = new MutableLiveData<>();
    private MutableLiveData<String> getMyJobPostByIdResult = new MutableLiveData<>();
    private MutableLiveData<String> processCvAppliedResult = new MutableLiveData<>();
    private MutableLiveData<String> withDrawCvResult = new MutableLiveData<>();

    //jobcategory, jobcategory, jobsave
    private MutableLiveData<List<JobType>> getAllJobTypeData = new MutableLiveData<>();
    private MutableLiveData<List<JobCategory>> getAllJobCategoryData = new MutableLiveData<>();
    private MutableLiveData<List<JobPost>> getAllJobSavedData = new MutableLiveData<>();

    private MutableLiveData<String> getAllJobTypeResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobTypeResult = new MutableLiveData<>();
    private MutableLiveData<String> getAllJobCategoryResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobCategoryResult = new MutableLiveData<>();
    private MutableLiveData<String> getAllJobSavedResult = new MutableLiveData<>();
    private MutableLiveData<String> createJobSavedResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteJobSavedResult = new MutableLiveData<>();
    private MutableLiveData<String> toggleJobPostResult = new MutableLiveData<>();

    //Job applied
    private MutableLiveData<List<JobApplied>> getCvsJobAppliedData = new MutableLiveData<>();
    private MutableLiveData<List<JobApplied>> getApplicantsJobAppliedData = new MutableLiveData<>();
    private MutableLiveData<List<JobApplied>> getJobAppliedData = new MutableLiveData<>();
    public void resetGetJobAppliedResultData() {
        getJobAppliedResult.postValue(null);
        getJobAppliedData.postValue(null);
    }
    public void resetApplyAJobResult() {
        applyAJobResult.postValue(null);
    }
    private MutableLiveData<String> getCvsJobAppliedResult = new MutableLiveData<>();
    private MutableLiveData<String> getApplicantsJobAppliedResult = new MutableLiveData<>();
    private MutableLiveData<String> getJobAppliedResult = new MutableLiveData<>();

    private MutableLiveData<String> applyAJobResult = new MutableLiveData<>();

    //Getter live data
    public LiveData<List<JobPost>> getAllJobPostData() { return getAllJobPostData; }
    public LiveData<List<JobPost>> getJobPostsRecommendData() { return getJobPostsRecommendData; }
    public LiveData<String> getJobPostsRecommendResult() { return getJobPostsRecommendResult; }

    public LiveData<String> getJobPostRecommendResult() { return getJobPostsRecommendResult; }
    public LiveData<List<JobPost>> getJobPostByStatusData() { return getJobPostByStatusData; }
    public LiveData<JobPost> toggleJobPostData() { return toggleJobPostStatusData; }
    public LiveData<String> toggleJobPostResult() { return toggleJobPostResult; }
    public LiveData<List<JobPost>> getJobPostsByCompanyData() { return getJobPostsByCompanyData; }
    public LiveData<JobPost> getJobPostData() { return getJobPostData; }
    public LiveData<JobPost> getMyJobPostByIdData() { return getMyJobPostByIdData; }
    public LiveData<String> getMyJobPostByIdResult() { return getMyJobPostByIdResult; }
    public LiveData<String> getProcessCvAppliedResult() { return processCvAppliedResult; }
    public LiveData<JobApplied> getProcessCvAppliedData() { return processCvAppliedData; }
    public LiveData<String> getWithDrawCvResult() { return withDrawCvResult; }

    public LiveData<String> getAllJobPostResult() { return getAllJobPostResult; }
    public LiveData<String> getJobPostByStatusResult() { return getJobPostByStatusResult; }
    public LiveData<String> getJobPostsByCompanyResult() { return getJobPostsByCompanyResult; }
    public LiveData<String> getJobPostResult() { return getJobPostResult; }
    public LiveData<String> getUpdateJobPostResult() { return updateJobPostResult; }

    public LiveData<String> getCreateJobPostResult() { return createJobPostResult; }
    public LiveData<String> getDeleteJobPostResult() { return deleteJobPostResult; }
    public LiveData<String> getSearchJobPostResult() { return searchJobPostResult; }
    public LiveData<List<JobPost>> getSearchJobPostData() { return searchJobPostData; }
    public LiveData<JobPost> getCreateJobPostData() { return createJobPostData; }
    public LiveData<JobPost> getUpdateJobPostData() { return updateJobPostData; }

    public LiveData<List<JobType>> getAllJobTypeData() { return getAllJobTypeData; }
    public LiveData<List<JobCategory>> getAllJobCategoryData() { return getAllJobCategoryData; }
    public LiveData<List<JobPost>> getAllJobSaved() { return getAllJobSavedData; }
    public LiveData<String> getAllJobTypeResult() { return getAllJobTypeResult; }
    public LiveData<String> createJobTypeResult() { return createJobTypeResult; }
    public LiveData<String> getAllJobCategoryResult() { return getAllJobCategoryResult; }
    public LiveData<String> createJobCategoryResult() { return createJobCategoryResult; }
    public LiveData<String> getAllJobSavedResult() { return getAllJobSavedResult; }
    public LiveData<String> createJobSavedResult() { return createJobSavedResult; }
    public LiveData<String> deleteJobSavedResult() { return deleteJobSavedResult; }

    public LiveData<List<JobApplied>> getCvsJobApplied() { return getCvsJobAppliedData; }
    public LiveData<List<JobApplied>> getApplicantsJobApplied() { return getApplicantsJobAppliedData; }
    public LiveData<List<JobApplied>> getJobAppliedData() { return getJobAppliedData; }
    public LiveData<String> getJobAppliedResult() { return getJobAppliedResult; }
    public LiveData<String> getApplyAJobResult() { return applyAJobResult; }

    //current jobpost for update jobpost sycn
    private MutableLiveData<JobPost> currentJobPost = new MutableLiveData<>();
    public LiveData<JobPost> getCurrentJobPost() {
        return currentJobPost;
    }
    public void setCurrentJobPost(JobPost jobPost) {
        Log.d("JobPostViewModel", "Setting current job post: " + new Gson().toJson(jobPost));
        currentJobPost.setValue(jobPost);
    }

    // API Calls
    public void getAllJobPosts(int page, int pageSize) {
        jobPostRepository.getAllJobPosts(page, pageSize).enqueue(new Callback<ListJobPostResponse>() {
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

    public void getJobPostsRecommend(int page, int pageSize) {
        jobPostRepository.getJobPostRecommend(page, pageSize).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getJobPostsRecommendData.postValue(response.body().getAllJobPost());
                    getJobPostsRecommendResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getJobPostsRecommendResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                getJobPostsRecommendResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void getJobPostsByCompany(String companyId, int page, int pageSize) {
        jobPostRepository.getJobPostsByCompany(companyId, page, pageSize).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getJobPostsByCompanyData.postValue(response.body().getAllJobPost());
                    getJobPostsByCompanyResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getJobPostsByCompanyResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                getJobPostsByCompanyResult.postValue("Error: " + t.getMessage());
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

    public void getMyJobPostById(String id) {
        jobPostRepository.getMyJobPostById(id).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getMyJobPostByIdData.postValue(response.body().getJobPost());
                    getMyJobPostByIdResult.postValue("Success");

                    Log.d("JobPostViewModellll", new Gson().toJson(response.body()));
                } else {
                    getMyJobPostByIdResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                getMyJobPostByIdResult.postValue("Error: " + t.getMessage());
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
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                    try {
                        if (response.errorBody() != null) {
                            String errorBodyString = response.errorBody().string();
                            Log.d("API_ERROR", "Lỗi từ server: " + errorBodyString);
                        } else {
                            Log.d("API_ERROR", "Không có errorBody");
                        }
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Lỗi khi đọc errorBody: ", e);
                    }
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
                    Log.d("API_RESPONSE_UPDATE_JP", new Gson().toJson(response.body()));
                    updateJobPostData.postValue(response.body().getJobPost());
                    updateJobPostResult.postValue("Success");
                } else {
                    String errorMessage = "Unknown error";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        errorMessage = "Error parsing errorBody: " + e.getMessage();
                    }

                    Log.e("API_RESPONSE_ERROR", errorMessage);
                    updateJobPostResult.postValue("Failed: " + errorMessage);
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

    public void searchJobPosts(int page, int pageSize, String title, String location, String position, String educationRequirement, String companyName) {
        jobPostRepository.searchJobPosts(page, pageSize, title, location, position, educationRequirement, companyName).enqueue(new Callback<ListJobPostResponse>() {
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
        Log.d("JobPostViewModel", "getAllJobSaved: " + applicantId + "");
        jobPostRepository.getJobSaved(applicantId).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response){
                if (response.isSuccessful() && response.body() != null) {
                    getAllJobSavedData.postValue(response.body().getAllJobPost());
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

    public void createJobSaved(JobSavedRequest request) {
        jobPostRepository.createJobSaved(request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createJobSavedResult.postValue("Success");
                } else {
                    createJobSavedResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
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

    //Job Applied
    public void getCvsJobApplied(String jobpostId) {
        jobPostRepository.getCvsJobApplied(jobpostId).enqueue(new Callback<ListJobAppliedResponse>() {
            @Override
            public void onResponse(Call<ListJobAppliedResponse> call, Response<ListJobAppliedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getCvsJobAppliedData.postValue(response.body().getAllJobApplies());
                } else {
                    getCvsJobAppliedResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobAppliedResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    public void getApplicantsJobApplied(String jobpostId) {
        jobPostRepository.getApplicantsJobApplied(jobpostId).enqueue(new Callback<ListJobAppliedResponse>() {
            @Override
            public void onResponse(Call<ListJobAppliedResponse> call, Response<ListJobAppliedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getApplicantsJobAppliedData.postValue(response.body().getAllJobApplies());
                } else {
                    getApplicantsJobAppliedResult.postValue("Failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListJobAppliedResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    public void getJobApplied(String applicantId) { jobPostRepository.getJobApplied(applicantId).enqueue(new Callback<ListJobAppliedResponse>() {
        @Override
        public void onResponse(Call<ListJobAppliedResponse> call, Response<ListJobAppliedResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                getJobAppliedData.postValue(response.body().getAllJobApplies());
                getJobAppliedResult.setValue("Success");
            } else {
                getJobAppliedResult.postValue("Failed: " + response.message());
            }
        }
        @Override
        public void onFailure(Call<ListJobAppliedResponse> call, Throwable t) {
            Log.e("API_ERROR", t.getMessage());
        }
    });
    }

    public void applyAJob(String jobpostId, String applicantId, String cvId) {
        ApplyAJobRequest request = new ApplyAJobRequest(jobpostId, applicantId, cvId);
        jobPostRepository.applyAJob(request).enqueue(new Callback<MessageResponse>() {
        @Override
        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
            if(response.isSuccessful() && response.body() != null) {
                applyAJobResult.postValue("Success");
            } else {
                applyAJobResult.postValue("Failed: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<MessageResponse> call, Throwable t) {
            Log.e("API_ERROR", t.getMessage());
        }
    });
    }

    public void processCvApplied(String jobpostId, String applicantId, String status) {
        ProcessCvAppliedRequest request = new ProcessCvAppliedRequest(jobpostId, applicantId, status);
        jobPostRepository.processCvApplied(request).enqueue(new Callback<JobAppliedResponse>() {
            @Override
            public void onResponse(Call<JobAppliedResponse> call, Response<JobAppliedResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    processCvAppliedData.postValue(response.body().getJobApplied());
                    processCvAppliedResult.postValue("Success");
                } else {
                    processCvAppliedResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobAppliedResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
    public void withDrawCv(String applicantId, String jobpostId) {
        jobPostRepository.withDrawCv(applicantId, jobpostId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                Log.e("eeee", "keu quai di" + response.message());
                if (response.isSuccessful()) {
                    withDrawCvResult.postValue("Success");
                } else {
                    withDrawCvResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                withDrawCvResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void getJobPostByStatus(int page, int pageSize, String status) {
        jobPostRepository.getJobPostByStatus(page, pageSize, status).enqueue(new Callback<ListJobPostResponse>() {
            @Override
            public void onResponse(Call<ListJobPostResponse> call, Response<ListJobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getJobPostByStatusData.postValue(response.body().getAllJobPost());
                    getJobPostByStatusResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getJobPostByStatusResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListJobPostResponse> call, Throwable t) {
                getJobPostByStatusResult.postValue("Error: " + t.getMessage());
            }
        });
    }

    public void toggleJobPostStatus(String id, String status) {
        StatusRequest request = new StatusRequest(status);
        jobPostRepository.toggleJobPostStatus(id, request).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    toggleJobPostStatusData.postValue(response.body().getJobPost());
                    toggleJobPostStatusResult.postValue("Success");
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    getJobPostByStatusResult.postValue("Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                getJobPostByStatusResult.postValue("Error: " + t.getMessage());
            }
        });
    }
}
