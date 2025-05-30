package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Company;

public class UpdateCompanyResponse {
    private String message;
    private Company company;

    public UpdateCompanyResponse() {}
    public UpdateCompanyResponse(String message, Company company) {
        this.message = message;
        this.company = company;
    }

    public String getMessage() { return message; }
    public Company getCompany() { return company; }
}
