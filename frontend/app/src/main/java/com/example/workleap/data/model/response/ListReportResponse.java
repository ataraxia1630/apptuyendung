package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Report;

import java.util.ArrayList;
import java.util.List;

public class ListReportResponse {
    String message;

    List<Report> reports;

    public ListReportResponse() {
    }

    public ListReportResponse(String message, ArrayList<Report> reports) {
        this.message = message;
        this.reports = reports;
    }

    public String getMessage() {
        return message;
    }

    public List<Report> getReports() {
        return reports;
    }
}
