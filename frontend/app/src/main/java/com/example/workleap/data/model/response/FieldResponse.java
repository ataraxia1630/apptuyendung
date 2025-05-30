package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Field;

import java.util.List;

public class FieldResponse {
    String message;

    Field field;

    public FieldResponse() {
    }

    public FieldResponse(String message, Field field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public Field getAllField() {
        return field;
    }
}
