package com.example.workleap.data.model;

public class UpdateApplicantEducationResponse {
    String message;
    ApplicantEducation applicantEducation;

    public UpdateApplicantEducationResponse() {};

    public UpdateApplicantEducationResponse(String message, ApplicantEducation applicantEducation) {
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
