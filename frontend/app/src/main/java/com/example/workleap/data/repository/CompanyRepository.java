package com.example.workleap.data.repository;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetCompanyResponse;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateCompanyRequest;

import retrofit2.Call;

public class CompanyRepository {
    private ApiService apiService;
    public CompanyRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    //Get
    public Call<GetCompanyResponse> getCompany(String id) {
        return apiService.getCompany(id);
    }

    //Update
    public Call<MessageResponse> updateCompany(String id, UpdateCompanyRequest request) {
        return apiService.updateCompany(id, request);
    }
}
