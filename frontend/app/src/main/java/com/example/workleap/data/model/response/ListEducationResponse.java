package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.JobPost;

import java.util.List;

public class ListEducationResponse {
    String message;

    List<Education> educations;

    public ListEducationResponse() {
    }

    public ListEducationResponse(String message,  List<Education> educations) {
        this.message = message;
        this.educations = educations;
    }

    public String getMessage() {
        return message;
    }

    public List<Education> getAllEducation() {
        return educations;
    }
}
