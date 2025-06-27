package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.response.ListReportResponse;
import com.example.workleap.data.model.response.ReportResponse;

import retrofit2.Call;

public class ReportRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public ReportRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

}
