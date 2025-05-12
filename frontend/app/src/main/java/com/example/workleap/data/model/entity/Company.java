package com.example.workleap.data.model.entity;

public class Company {
    private int id;
    private String name;
    private String description;
    private int establishedYear;
    private String taxcode;

    /*Post            Post[]
    User            User[]*/

    public Company() {}

    public Company(int id, String email, String name, String description, String address, int establishedYear, String taxcode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishedYear = establishedYear;
        this.taxcode = taxcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(int establishedYear) {
        this.establishedYear = establishedYear;
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    // Phương thức toString để in thông tin công ty
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", establishedYear=" + establishedYear +
                ", taxcode='" + taxcode + '\'' +
                '}';
    }
}
