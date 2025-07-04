package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobType;

import java.util.List;

public class ListJobTypeResponse {
    String message;

    List<JobType> types;

    public ListJobTypeResponse() {
    }

    public ListJobTypeResponse(String message, List<JobType> jobTypes) {
        this.message = message;
        this.types = jobTypes;
    }

    public String getMessage() {
        return message;
    }

    public List<JobType> getAllJobType() {
        return types;
    }
}
