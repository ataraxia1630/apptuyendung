package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Applicant;

public class GetApplicantResponse {
    private String message;

    private Applicant applicant;

    public GetApplicantResponse() {}

    public GetApplicantResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Applicant getApplicant() {
        return applicant;
    }
}
