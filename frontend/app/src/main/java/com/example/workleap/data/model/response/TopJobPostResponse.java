package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobPost;

public class TopJobPostResponse {
    private JobPost job;

    private int applicationCount;

    public TopJobPostResponse() {
    }

    public TopJobPostResponse(JobPost job, int applicationCount) {
        this.job = job;
        this.applicationCount = applicationCount;
    }

    public JobPost getJob() {
        return job;
    }

    public int getApplicationCount() {
        return applicationCount;
    }
}
