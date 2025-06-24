package com.example.workleap.data.model.response;

import java.util.List;

public class ListFieldStatResponse {
    private String message;
    private List<FieldStat> data;
    public ListFieldStatResponse() {
    }

    public ListFieldStatResponse(String message, List<FieldStat> data) {
        this.message = message;
        this.data = data;
    }

    public List<FieldStat> getListFieldStat() {
        return data;
    }
    public String getMessage(){ return message;}
}
