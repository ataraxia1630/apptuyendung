package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobApplied;

import java.util.List;

public class ListJobAppliedResponse {
    String message;

    List<JobApplied> appliedJobposts;

    public ListJobAppliedResponse() {
    }

    public ListJobAppliedResponse(String message, List<JobApplied> appliedJobposts) {
        this.message = message;
        this.appliedJobposts = appliedJobposts;
    }

    public String getMessage() {
        return message;
    }

    public List<JobApplied> getAllJobApplies() {
        return appliedJobposts;
    }
}
