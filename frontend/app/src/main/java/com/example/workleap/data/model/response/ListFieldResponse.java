package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Field;

import java.util.List;

public class ListFieldResponse {
    String message;

    List<Field> fields;

    public ListFieldResponse() {
    }

    public ListFieldResponse(String message, List<Field> fields) {
        this.message = message;
        this.fields = fields;
    }

    public String getMessage() {
        return message;
    }

    public List<Field> getAllField() {
        return fields;
    }
}
