package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.request.UpdateCompanyRequest;
import com.example.workleap.data.model.response.UpdateCompanyResponse;

import retrofit2.Call;

public class CompanyRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public CompanyRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    //Get
    public Call<GetCompanyResponse> getCompany(String id) {
        return apiService.getCompany(id);
    }

    //Update
    public Call<UpdateCompanyResponse> updateCompany(String id, UpdateCompanyRequest request) {
        return apiService.updateCompany(id, request);
    }
}
