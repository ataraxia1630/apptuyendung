package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Field;
import com.example.workleap.data.model.entity.InterestedField;

import java.util.List;

public class ListInterestedFieldResponse {
    String message;

    List<InterestedField> fields;

    public ListInterestedFieldResponse() {
    }

    public ListInterestedFieldResponse(String message, List<InterestedField> interestedFields) {
        this.message = message;
        this.fields = fields;
    }

    public String getMessage() {
        return message;
    }

    public List<InterestedField> getAllApplicantFields() {
        return fields;
    }
}
