package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.CV;

import java.util.List;

public class CVResponse {
    String message;

    CV cv;

    public CVResponse() {
    }

    public CVResponse(String message, CV cv) {
        this.message = message;
        this.cv = cv;
    }

    public String getMessage() {
        return message;
    }

    public CV getCv() {
        return cv;
    }
}
