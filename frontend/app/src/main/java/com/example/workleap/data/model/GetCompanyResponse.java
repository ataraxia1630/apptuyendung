package com.example.workleap.data.model;

public class GetCompanyResponse {
    private String message;

    private Company company;

    public GetCompanyResponse() {}

    public GetCompanyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Company getCompany() {
        return company;
    }
}
