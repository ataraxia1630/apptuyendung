package com.example.workleap.data.model.request;

public class ReportJobPostRequest {
    private String reason;
    private String jobPostId;

    public ReportJobPostRequest() {}

    public ReportJobPostRequest(String reason, String jobPostId) {
        this.reason = reason;
        this.jobPostId = jobPostId;
    }
    // Getter
    public String getReason() { return reason; }
    public String getJobPostId() { return jobPostId; }
}
