package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Applicant;

public class UpdateApplicantResponse {
    private String message;
    private Applicant applicant;

    public UpdateApplicantResponse() {}
    public UpdateApplicantResponse(String message, Applicant applicant) {
        this.message = message;
        this.applicant = applicant;
    }

    public String getMessage() { return message; }
    public Applicant getApplicant() { return applicant; }
}
