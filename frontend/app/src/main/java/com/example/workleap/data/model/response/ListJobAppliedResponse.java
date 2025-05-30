package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobType;

import java.util.List;

public class ListJobAppliedResponse {
    String message;

    List<JobApplied> jobApplieds;

    public ListJobAppliedResponse() {
    }

    public ListJobAppliedResponse(String message, List<JobApplied> jobApplieds) {
        this.message = message;
        this.jobApplieds = jobApplieds;
    }

    public String getMessage() {
        return message;
    }

    public List<JobApplied> getAllJobApplies() {
        return jobApplieds;
    }
}
