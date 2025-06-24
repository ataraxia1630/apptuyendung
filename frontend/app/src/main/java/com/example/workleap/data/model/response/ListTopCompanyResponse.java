package com.example.workleap.data.model.response;

import java.util.List;

public class ListTopCompanyResponse {
    private String message;
    private List<TopCompanyResponse> data;
    public ListTopCompanyResponse() {
    }

    public ListTopCompanyResponse(String message, List<TopCompanyResponse> data) {
        this.message = message;
        this.data = data;
    }

    public List<TopCompanyResponse> getListCompany() {
        return data;
    }
    public String getMessage(){ return message;}
}
