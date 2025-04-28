package com.example.workleap.data.model;

public class CreateApplicantEducationResponse {
    String message;
    ApplicantEducation applicantEducation;

    public CreateApplicantEducationResponse() {};

    public CreateApplicantEducationResponse(String message, ApplicantEducation applicantEducation) {
        this.message = message;
        this.applicantEducation = applicantEducation;
    }

    public String getMessage() {
        return message;
    }

    public ApplicantEducation getApplicantEducation() {
        return applicantEducation;
    }
}
