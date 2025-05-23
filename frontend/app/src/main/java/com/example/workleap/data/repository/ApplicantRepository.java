package com.example.workleap.data.repository;

import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantRepository {
    private ApiService apiService;
    public ApplicantRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    //Get
    public Call<GetApplicantResponse> getApplicant(String id) {
        return apiService.getApplicant(id);
    }

    //Update
    public Call<UpdateApplicantResponse> updateApplicant(String id, UpdateApplicantRequest request) {
        return apiService.updateApplicant(id, request);
    }
}
