package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.DailyStatistic;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.OverviewResponse;
import com.example.workleap.data.repository.StatisticRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticViewModel extends ViewModel {
    private StatisticRepository overviewRepository;
    private MutableLiveData<String> getOverviewResult = new MutableLiveData<>();
    private MutableLiveData<DailyStatistic> getOverviewData = new MutableLiveData<>();

    public StatisticViewModel(){}
    public void InitiateRepository(Context context) {
        overviewRepository = new StatisticRepository(context);
    }

    // Getter cho LiveData
    public LiveData<String> getGetOverviewResult() { return getOverviewResult; }
    public LiveData<DailyStatistic> getGetOverviewData() { return getOverviewData; }
    // Get company
    public void getOverview() {
        Call<OverviewResponse> call = overviewRepository.getOverview();
        call.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                if (response.isSuccessful()) {
                    OverviewResponse getResponse = response.body();
                    getOverviewResult.setValue(getResponse.getMessage());
                    getOverviewData.setValue(getResponse.getDailyStatistic());
                } else {
                    try {
                        OverviewResponse error = new Gson().fromJson(response.errorBody().string(), OverviewResponse.class);
                        getOverviewResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getOverviewResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<OverviewResponse> call, Throwable t) {
                getOverviewResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
