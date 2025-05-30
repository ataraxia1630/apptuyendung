package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.JobApplied;

import java.util.List;

public class ListCVResponse {
    String message;

    List<CV> Cvs;

    public ListCVResponse() {
    }

    public ListCVResponse(String message, List<CV> Cvs) {
        this.message = message;
        this.Cvs = Cvs;
    }

    public String getMessage() {
        return message;
    }

    public List<CV> getAllCvs() {
        return Cvs;
    }
}
