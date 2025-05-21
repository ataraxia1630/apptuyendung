package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Applicant;
import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Education;

import java.util.List;

public class ListApplicantEducationResponse {
    String message;

    List<ApplicantEducation> applicantEducations;

    public ListApplicantEducationResponse() {
    }

    public ListApplicantEducationResponse(String message, List<ApplicantEducation> applicantEducations) {
        this.message = message;
        this.applicantEducations = applicantEducations;
    }

    public String getMessage() {
        return message;
    }

    public List<ApplicantEducation> getAllApplicantEducation() {
        return applicantEducations;
    }
}
