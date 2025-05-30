package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.ApplicantEducation;

import java.util.List;

public class ListApplicantEducationResponse {
    String message;

    List<ApplicantEducation> educations;

    public ListApplicantEducationResponse() {
    }

    public ListApplicantEducationResponse(String message, List<ApplicantEducation> applicantEducations) {
        this.message = message;
        this.educations = applicantEducations;
    }

    public String getMessage() {
        return message;
    }

    public List<ApplicantEducation> getAllApplicantEducation() {
        return educations;
    }
}
