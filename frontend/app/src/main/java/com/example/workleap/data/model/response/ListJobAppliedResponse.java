package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobApplied;

import java.util.List;

public class ListJobAppliedResponse {
    String message;

    List<JobApplied> appliedCVs;

    public ListJobAppliedResponse() {
    }

    public ListJobAppliedResponse(String message, List<JobApplied> appliedCVs) {
        this.message = message;
        this.appliedCVs = appliedCVs;
    }

    public String getMessage() {
        return message;
    }

    public List<JobApplied> getAllJobApplies() {
        return appliedCVs;
    }
}
