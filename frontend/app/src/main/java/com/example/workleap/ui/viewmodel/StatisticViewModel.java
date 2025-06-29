package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.DailyStatistic;
import com.example.workleap.data.model.response.FieldStat;
import com.example.workleap.data.model.response.ListFieldStatResponse;
import com.example.workleap.data.model.response.ListMonthlyStatResponse;
import com.example.workleap.data.model.response.ListTopCompanyResponse;
import com.example.workleap.data.model.response.ListTopJobPostResponse;
import com.example.workleap.data.model.response.OverviewResponse;
import com.example.workleap.data.model.response.TopCompanyResponse;
import com.example.workleap.data.model.response.TopJobPostResponse;
import com.example.workleap.data.repository.StatisticRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticViewModel extends ViewModel {
    private StatisticRepository overviewRepository;
    private MutableLiveData<String> getOverviewResult = new MutableLiveData<>();
    private MutableLiveData<DailyStatistic> getOverviewData = new MutableLiveData<>();
    private MutableLiveData<List<TopCompanyResponse>> getTopCompanyData = new MutableLiveData<>();
    private MutableLiveData<List<TopJobPostResponse>> topJobPostData = new MutableLiveData<>();
    private MutableLiveData<ListMonthlyStatResponse> listMonthlyStatData = new MutableLiveData<>();
    private MutableLiveData<List<FieldStat>> listFieldStatData = new MutableLiveData<>();
    private MutableLiveData<String> listMonthlyStatResult = new MutableLiveData<>();
    private MutableLiveData<String> listFieldStatResult = new MutableLiveData<>();
    private MutableLiveData<String> getTopCompanyResult = new MutableLiveData<>();
    private MutableLiveData<String> topJobPostResult = new MutableLiveData<>();

    public StatisticViewModel(){}
    public void InitiateRepository(Context context) {
        overviewRepository = new StatisticRepository(context);
    }

    // Getter cho LiveData
    public LiveData<String> getGetOverviewResult() { return getOverviewResult; }
    public LiveData<DailyStatistic> getGetOverviewData() { return getOverviewData; }
    public LiveData<String> getTopCompanyResult() { return getTopCompanyResult; }
    public LiveData<String> getTopJobPostResult() { return topJobPostResult; }
    public LiveData<List<TopCompanyResponse>> getTopCompanyData() { return getTopCompanyData; }
    public LiveData<List<TopJobPostResponse>> getTopJobPostData() { return topJobPostData; }
    public LiveData<ListMonthlyStatResponse> getListMonthlyStatData() { return listMonthlyStatData; }
    public LiveData<List<FieldStat>> getListFieldStatData() { return listFieldStatData; }
    public LiveData<String> getListMonthlyStatResult() { return listMonthlyStatResult; }
    public LiveData<String> getListFieldStatResult() { return listFieldStatResult; }
    // Get company
    public void getOverview() {
        Call<OverviewResponse> call = overviewRepository.getOverview();
        call.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                if (response.isSuccessful()) {
                    OverviewResponse getResponse = response.body();
                    getOverviewResult.setValue(getResponse.getMessage());
                    getOverviewData.setValue(getResponse.getData());
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
    public void getTopCompany(int page, int pageSize) {
        Call<ListTopCompanyResponse> call = overviewRepository.getTopCompany(page, pageSize);
        call.enqueue(new Callback<ListTopCompanyResponse>() {
            @Override
            public void onResponse(Call<ListTopCompanyResponse> call, Response<ListTopCompanyResponse> response) {
                if (response.isSuccessful()) {
                    ListTopCompanyResponse getResponse = response.body();
                    getTopCompanyData.setValue(getResponse.getListCompany());
                    getTopCompanyResult.setValue("Success");
                } else {
                    try {
                        ListTopCompanyResponse error = new Gson().fromJson(response.errorBody().string(), ListTopCompanyResponse.class);
                        getOverviewResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getOverviewResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<ListTopCompanyResponse> call, Throwable t) {
                getOverviewResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void getTopJobPost(int page, int pageSize) {
        Call<ListTopJobPostResponse> call = overviewRepository.getTopJobPost(page, pageSize);
        call.enqueue(new Callback<ListTopJobPostResponse>() {
            @Override
            public void onResponse(Call<ListTopJobPostResponse> call, Response<ListTopJobPostResponse> response) {
                if (response.isSuccessful()) {
                    ListTopJobPostResponse getResponse = response.body();
                    topJobPostData.setValue(getResponse.getListJobPost());
                    topJobPostResult.setValue("Success");
                } else {
                    try {
                        ListTopJobPostResponse error = new Gson().fromJson(response.errorBody().string(), ListTopJobPostResponse.class);
                        topJobPostResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        topJobPostResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<ListTopJobPostResponse> call, Throwable t) {
                topJobPostResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void getMonthlyGrowth() {
        Call<ListMonthlyStatResponse> call = overviewRepository.getMonthlyGrowth();
        call.enqueue(new Callback<ListMonthlyStatResponse>() {
            @Override
            public void onResponse(Call<ListMonthlyStatResponse> call, Response<ListMonthlyStatResponse> response) {
                if (response.isSuccessful()) {
                    ListMonthlyStatResponse getResponse = response.body();
                    listMonthlyStatResult.setValue("Success");
                    listMonthlyStatData.setValue(getResponse);
                } else {
                    try {
                        ListMonthlyStatResponse error = new Gson().fromJson(response.errorBody().string(), ListMonthlyStatResponse.class);
                        listMonthlyStatResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        listMonthlyStatResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<ListMonthlyStatResponse> call, Throwable t) {
                listMonthlyStatResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void getByField(int page, int pageSize) {
        Call<ListFieldStatResponse> call = overviewRepository.getByField(page, pageSize);
        call.enqueue(new Callback<ListFieldStatResponse>() {
            @Override
            public void onResponse(Call<ListFieldStatResponse> call, Response<ListFieldStatResponse> response) {
                if (response.isSuccessful()) {
                    ListFieldStatResponse getResponse = response.body();
                    listFieldStatData.setValue(getResponse.getListFieldStat());
                    listFieldStatResult.setValue("Success");
                } else {
                    try {
                        ListFieldStatResponse error = new Gson().fromJson(response.errorBody().string(), ListFieldStatResponse.class);
                        listFieldStatResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        listFieldStatResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<ListFieldStatResponse> call, Throwable t) {
                listFieldStatResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}
