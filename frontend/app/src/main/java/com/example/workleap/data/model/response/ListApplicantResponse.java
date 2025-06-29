package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Applicant;

import java.util.List;

public class ListApplicantResponse {
    private String message;

    private List<Applicant> applicants;

    public ListApplicantResponse() {}

    public ListApplicantResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }
}
