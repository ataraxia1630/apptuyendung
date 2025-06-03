package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.CV;

import java.util.List;

public class ListCVResponse {
    String message;

    List<CV> cvs;

    public ListCVResponse() {
    }

    public ListCVResponse(String message, List<CV> Cvs) {
        this.message = message;
        this.cvs = Cvs;
    }

    public String getMessage() {
        return message;
    }

    public List<CV> getAllCvs() {
        return cvs;
    }
}
