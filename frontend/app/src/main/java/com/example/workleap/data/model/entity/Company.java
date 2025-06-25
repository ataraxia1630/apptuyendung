package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Company implements Serializable {
    private String id;
    private String name;
    private String description;
    private int establishedYear;
    private String taxcode;
    private ArrayList<User> User;

    /*Post            Post[]
    User            User[]*/

    public Company() {}

    public Company(String id, String email, String name, String description, String address, int establishedYear, String taxcode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishedYear = establishedYear;
        this.taxcode = taxcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public ArrayList<User> getUser() { return User; }

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
