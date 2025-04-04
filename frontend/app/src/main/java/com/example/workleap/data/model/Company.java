package com.example.workleap.data.model;

public class Company {
    private int id;
    private String email;
    private String name;
    private String description;
    private String address;
    private int establishedYear;
    private String taxcode;

    public Company(int id, String email, String name, String description, String address, int establishedYear, String taxcode) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.description = description;
        this.address = address;
        this.establishedYear = establishedYear;
        this.taxcode = taxcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", establishedYear=" + establishedYear +
                ", taxcode='" + taxcode + '\'' +
                '}';
    }
}
