package com.example.workleap.data.model.request;

public class ProcessCvAppliedRequest {
    private String jobpostId;
    private String applicantId;
    private String status;

    public ProcessCvAppliedRequest() {}

    public ProcessCvAppliedRequest(String jobpostId, String applicantId, String status) {
        this.jobpostId = jobpostId;
        this.applicantId = applicantId;
        this.status = status;
    }

    // Getter
    public String getJobpostId() { return jobpostId; }
    public String getApplicantId() { return applicantId; }
    public String getStatus() { return status; }
}
