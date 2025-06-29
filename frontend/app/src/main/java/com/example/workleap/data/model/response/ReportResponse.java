package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportResponse {
    String message;

    Report report;

    public ReportResponse() {
    }

    public ReportResponse(String message, Report report) {
        this.message = message;
        this.report = report;
    }

    public String getMessage() {
        return message;
    }

    public Report getReport() {
        return report;
    }
}
