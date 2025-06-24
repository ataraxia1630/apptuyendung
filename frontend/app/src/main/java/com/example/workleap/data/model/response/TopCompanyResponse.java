package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Company;

public class TopCompanyResponse {
    private Company company;

    private int applicationCount;

    public TopCompanyResponse() {
    }

    public TopCompanyResponse(Company company, int applicationCount) {
        this.company = company;
        this.applicationCount = applicationCount;
    }

    public Company getCompany() {
        return company;
    }

    public int getApplicationCount() {
        return applicationCount;
    }
}
