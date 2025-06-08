package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobPost;

import java.util.List;

public class ListJobPostResponse {
    String message;

    List<JobPost> jobPosts;

    public ListJobPostResponse() {
    }

    public ListJobPostResponse(String message,  List<JobPost> jobPosts) {
        this.message = message;
        this.jobPosts = jobPosts;
    }

    public String getMessage() {
        return message;
    }

    public List<JobPost> getAllJobPost() {
        return jobPosts;
    }
}
