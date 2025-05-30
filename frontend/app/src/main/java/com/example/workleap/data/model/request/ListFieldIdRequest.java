package com.example.workleap.data.model.request;

import java.util.List;

public class ListFieldIdRequest {
    List<String> fieldIds;

    public ListFieldIdRequest() {}

    public ListFieldIdRequest(List<String> fieldIds) {
        this.fieldIds = fieldIds;
    }

    // Getter
    public List<String> getFieldIds() { return fieldIds; }
    public void setFieldIds(List<String> fieldIds) { this.fieldIds = fieldIds; }
}
