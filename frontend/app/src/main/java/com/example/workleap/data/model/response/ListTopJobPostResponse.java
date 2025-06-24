package com.example.workleap.data.model.response;

import java.util.List;

public class ListTopJobPostResponse {
    private String message;
    private List<TopJobPostResponse> data;
    public ListTopJobPostResponse() {
    }

    public ListTopJobPostResponse(String message, List<TopJobPostResponse> data) {
        this.message = message;
        this.data = data;
    }

    public List<TopJobPostResponse> getListJobPost() {
        return data;
    }
    public String getMessage(){ return message;}
}
