package com.example.workleap.data.model.request;

public class ApplyAJobRequest {
    private String jobpostId;
    private String applicantId;
    private String cvId;

    public ApplyAJobRequest() {}

    public ApplyAJobRequest(String jobpostId, String applicantId, String cvId) {
        this.jobpostId = jobpostId;
        this.applicantId = applicantId;
        this.cvId = cvId;
    }

    // Getter
    public String getJobpostId() { return jobpostId; }
    public String getApplicantId() { return applicantId; }
    public String getCvId() { return cvId;}
}
