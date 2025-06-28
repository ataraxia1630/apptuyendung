package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.workleap.data.model.entity.Report;
import com.example.workleap.data.model.response.ListReportResponse;
import com.example.workleap.data.repository.ApplicantRepository;
import com.example.workleap.data.repository.ReportRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewModel extends ViewModel {
    private ReportRepository reportRepository;

    private MutableLiveData<String> getReportResult = new MutableLiveData<>();
    private MutableLiveData<List<Report>> getReportData = new MutableLiveData<>();

    public void initiateRepository(Context context) {
        reportRepository = new ReportRepository(context);
    }

    public LiveData<String> getGetReportResult() {
        return getReportResult;
    }

    public LiveData<List<Report>> getGetReportData() {
        return getReportData;
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
}
