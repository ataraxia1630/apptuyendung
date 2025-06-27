package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.workleap.data.model.entity.Report;
import com.example.workleap.data.repository.ReportRepository;

import java.util.List;

public class ReportViewModel extends ViewModel {
    private ReportRepository reportRepository;

    private MutableLiveData<String> reportResult = new MutableLiveData<>();
    private MutableLiveData<List<Report>> reportListData = new MutableLiveData<>();

    public void initiateRepository(Context context) {
        reportRepository = new ReportRepository(context);
    }

    public LiveData<String> getReportResult() {
        return reportResult;
    }

    public LiveData<List<Report>> getReportListData() {
        return reportListData;
    }


}
