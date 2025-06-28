package com.example.workleap.data.model.request;

import com.example.workleap.data.model.entity.JobSaved;

public class JobSavedRequest {
    String applicantId;
    String jobpostId;
    public JobSavedRequest() {}
    public JobSavedRequest(String applicantId, String jobpostId) {
        this.applicantId = applicantId;
        this.jobpostId = jobpostId;
    };
}
