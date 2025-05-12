package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.InterestedField;

public class CreateInterestedFieldResponse {
    String message;
    InterestedField interestedField;

    public CreateInterestedFieldResponse(String message, InterestedField interestedField) {
        this.message = message;
        this.interestedField = interestedField;
    }

    public String getMessage() {
        return message;
    }

    public InterestedField getInterestedField() {
        return interestedField;
    }
}
