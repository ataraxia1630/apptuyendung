package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobApplied;

public class JobAppliedResponse {
    String message;

    JobApplied jobApplied;

    public JobAppliedResponse() {
    }

    public JobAppliedResponse(String message, JobApplied jobApplied) {
        this.message = message;
        this.jobApplied = jobApplied;
    }

    public String getMessage() {
        return message;
    }

    public JobApplied getJobApplied() {
        return jobApplied;
    }
}
