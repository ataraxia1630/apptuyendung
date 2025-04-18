package com.example.workleap.data.model;

public class UpdateCompanyRequest {
    private String name;
    private String description;
    private int establishedYear;
    private String taxcode;

    /*Post            Post[]
    User            User[]*/

    public UpdateCompanyRequest() {};
    public UpdateCompanyRequest(String name, String description, int establishedYear, String taxcode)
    {
        this.name = name;
        this.description = description;
        this.establishedYear = establishedYear;
        this.taxcode = taxcode;

    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public int getEstablishedYear() {
        return establishedYear;
    }
    public String getTaxcode() {
        return taxcode;
    }
}
