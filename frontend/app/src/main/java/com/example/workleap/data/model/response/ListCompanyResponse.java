package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Company;

import java.util.List;

public class ListCompanyResponse {
    private String message;
    private List<Company> companies;
    public ListCompanyResponse() {
    }

    public ListCompanyResponse(String message, List<Company> companies) {
        this.message = message;
        this.companies = companies;
    }

    public List<Company> getListCompany() {
        return companies;
    }
    public String getMessage(){ return message;}
}
