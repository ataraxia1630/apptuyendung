package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.workleap.data.model.entity.Report;
import com.example.workleap.data.model.request.ReportJobPostRequest;
import com.example.workleap.data.model.request.ReportPostRequest;
import com.example.workleap.data.model.request.ReportUserRequest;
import com.example.workleap.data.model.response.ListReportResponse;
import com.example.workleap.data.model.response.ReportResponse;
import com.example.workleap.data.repository.ReportRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewModel extends ViewModel {
    private ReportRepository reportRepository;

    private MutableLiveData<String> getReportResult = new MutableLiveData<>();
    private MutableLiveData<String> createReportResult = new MutableLiveData<>();
    private MutableLiveData<List<Report>> getReportData = new MutableLiveData<>();
    private MutableLiveData<Report> createReportData = new MutableLiveData<>();

    public void initiateRepository(Context context) {
        reportRepository = new ReportRepository(context);
    }

    public LiveData<String> getGetReportResult() {
        return getReportResult;
    }
    public LiveData<String> getCreateReportData() {
        return createReportResult;
    }

    public LiveData<List<Report>> getGetReportData() {
        return getReportData;
    }
    public LiveData<Report> getCreateReport() {
        return createReportData;
    }

    public void getAllReports() {
        Call<ListReportResponse> call = reportRepository.getAllPosts();
        call.enqueue(new Callback<ListReportResponse>() {
            @Override
            public void onResponse(Call<ListReportResponse> call, Response<ListReportResponse> response) {
                if (response.isSuccessful()) {
                    ListReportResponse PostResponse = response.body();
                    getReportData.setValue(PostResponse.getReports());
                    getReportResult.setValue("Success");
                } else {
                    try {
                        ListReportResponse error = new Gson().fromJson(response.errorBody().string(), ListReportResponse.class);
                        getReportResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getReportResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListReportResponse> call, Throwable t) {
                getReportResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void createReportUser(String reason, String id) {
        //type: user, jobpost, post
        ReportUserRequest request = new ReportUserRequest(reason, id);
        Call<ReportResponse> call = reportRepository.createReportUser(request);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse reportResponse = response.body();
                    createReportData.setValue(reportResponse.getReport());
                    createReportResult.setValue("Success");
                    Log.e("reportviewmodel", "hehe");
                } else {
                    try {
                        ReportResponse error = new Gson().fromJson(response.errorBody().string(), ReportResponse.class);
                        createReportResult.setValue("Lỗi: " + error.getMessage());
                        Log.e("reportviewmodel", error.getMessage());
                    } catch (Exception e) {
                        Log.e("reportviewmodel", "khong xac dinh");
                        createReportResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                createReportResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void createReportJobPost(String reason, String id) {
        ReportJobPostRequest request = new ReportJobPostRequest(reason, id);
        Call<ReportResponse> call = reportRepository.createReportJobPost(request);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse reportResponse = response.body();
                    createReportData.setValue(reportResponse.getReport());
                    createReportResult.setValue("Success");
                    Log.e("reportviewmodel", "hehe");
                } else {
                    try {
                        ReportResponse error = new Gson().fromJson(response.errorBody().string(), ReportResponse.class);
                        createReportResult.setValue("Lỗi: " + error.getMessage());
                        Log.e("reportviewmodel", error.getMessage());
                    } catch (Exception e) {
                        Log.e("reportviewmodel", "khong xac dinh");
                        createReportResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                createReportResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void createReportPost(String reason, String id) {
        ReportPostRequest request = new ReportPostRequest(reason, id);
        Call<ReportResponse> call = reportRepository.createReportPost(request);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse reportResponse = response.body();
                    createReportData.setValue(reportResponse.getReport());
                    createReportResult.setValue("Success");
                    Log.e("reportviewmodel", "hehe");
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        Log.e("reportviewmodel", "Error raw body: " + errorJson);

                        ReportResponse error = new Gson().fromJson(errorJson, ReportResponse.class);
                        createReportResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        Log.e("reportviewmodel", "Lỗi không xác định: " + e.getMessage(), e);
                        createReportResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                createReportResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
