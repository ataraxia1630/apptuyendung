package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobPost;

import java.util.List;

public class ListJobPostResponse {
    String message;

    List<JobPost> data;

    public ListJobPostResponse() {
    }

    public ListJobPostResponse(String message,  List<JobPost> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public List<JobPost> getAllJobPost() {
        return data;
    }
}
