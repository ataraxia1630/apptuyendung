package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.request.CVRequest;
import com.example.workleap.data.model.response.CVResponse;
import com.example.workleap.data.model.response.ListCVResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.OverviewResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class StatisticRepository {
    private ApiService apiService;

    private PreferencesManager preferencesManager;

    public StatisticRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    public Call<OverviewResponse> getOverview() {
        return apiService.getOverview();
    }
}
