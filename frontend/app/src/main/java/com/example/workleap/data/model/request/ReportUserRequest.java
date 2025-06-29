package com.example.workleap.data.model.request;

public class ReportUserRequest {
    private String reason;
    private String reportedUserId;

    public ReportUserRequest() {}

    public ReportUserRequest(String reason, String reportedUserId) {
        this.reason = reason;
        this.reportedUserId = reportedUserId;
    }
    // Getter
    public String getReason() { return reason; }
    public String getReportedUserId() { return reportedUserId; }
}
