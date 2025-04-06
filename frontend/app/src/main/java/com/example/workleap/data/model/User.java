package com.example.workleap.data.model;

import java.util.Date;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar; // Có thể null
    private String background; // Có thể null
    private String role; // Role là enum trong backend, ánh xạ thành String hoặc enum trong Java
    private Date createdAt;
    private Date updatedAt;
    private List<Company> company; // Quan hệ 1-nhiều với Company
    private List<Applicant> applicant; // Quan hệ 1-nhiều với Applicant

    //Constructor rong cho Gson
    public User() {}

    // Constructor đầy đủ
    public User(String id, String username, String password, String email, String phoneNumber,
                String avatar, String background, String role, Date createdAt, Date updatedAt,
                List<Company> company, List<Applicant> applicant) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.background = background;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.company = company;
        this.applicant = applicant;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Company> getCompany() {
        return company;
    }

    public void setCompany(List<Company> company) {
        this.company = company;
    }

    public List<Applicant> getApplicant() {
        return applicant;
    }

    public void setApplicant(List<Applicant> applicant) {
        this.applicant = applicant;
    }
}

