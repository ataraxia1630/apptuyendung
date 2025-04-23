package com.example.workleap.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.repository.ApplicantRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantViewModel {
    private ApplicantRepository applicantRepository;
    private MutableLiveData<String> updateApplicantResult = new MutableLiveData<>();
    private MutableLiveData<String> getApplicantResult = new MutableLiveData<>();

    public ApplicantViewModel() {
        applicantRepository = new ApplicantRepository();
    }

    // Getter cho LiveData
    public LiveData<String> getUpdateApplicantResult() { return updateApplicantResult; }
    public LiveData<String> getGetApplicantResult() { return getApplicantResult; }


    // Get applicant
    public void getApplicant(String id) {
        Call<GetApplicantResponse> call = applicantRepository.getApplicant(id);
        call.enqueue(new Callback<GetApplicantResponse>() {
            @Override
            public void onResponse(Call<GetApplicantResponse> call, Response<GetApplicantResponse> response) {
                if (response.isSuccessful()) {
                    GetApplicantResponse getResponse = response.body();
                    getApplicantResult.setValue(getResponse.getMessage());
                } else {
                    try {
                        GetApplicantResponse error = new Gson().fromJson(response.errorBody().string(), GetApplicantResponse.class);
                        getApplicantResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getApplicantResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetApplicantResponse> call, Throwable t) {
                getApplicantResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Update applicant
    public void updateApplicant(String id,  String address, String firstName, String lastName, String profileSummary, byte[] cvFile) {
        UpdateApplicantRequest request = new UpdateApplicantRequest(address, firstName, lastName, profileSummary, cvFile);
        Call<UpdateApplicantResponse> call = applicantRepository.updateApplicant(id, request);
        call.enqueue(new Callback<UpdateApplicantResponse>() {
            @Override
            public void onResponse(Call<UpdateApplicantResponse> call, Response<UpdateApplicantResponse> response) {
                if (response.isSuccessful()) {
                    UpdateApplicantResponse updateResponse = response.body();
                    updateApplicantResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateApplicantResponse error = new Gson().fromJson(response.errorBody().string(), UpdateApplicantResponse.class);
                        updateApplicantResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateApplicantResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateApplicantResponse> call, Throwable t) {
                updateApplicantResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
