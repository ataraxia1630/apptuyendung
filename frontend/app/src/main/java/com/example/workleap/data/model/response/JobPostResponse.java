package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobPost;

public class JobPostResponse {
    String message;
    JobPost jobPost;

    public JobPostResponse() {
    }

    public JobPostResponse(String message, JobPost jobPost) {
        this.message = message;
        this.jobPost = jobPost;
    }

    public String getMessage() {
        return message;
    }

    public JobPost getJobPost() {
        return jobPost;
    }
}
