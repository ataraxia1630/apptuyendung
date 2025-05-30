package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar; // Có thể null
    private String background; // Có thể null
    private String role; // Role là enum trong backend, ánh xạ thành String hoặc enum trong Java
    private Date created_at;
    private Date updated_at;
    private String companyId; //Chỉ nhận id company hoặc id applicant
    private String applicantId; //Chỉ nhận id company hoặc id applicant

    //Constructor rong cho Gson
    public User() {}

    // Constructor đầy đủ
    public User(String id, String username, String password, String email, String phoneNumber,
                String avatar, String background, String role, Date created_at, Date updated_at,
                String company, String applicant) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.background = background;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.companyId = company;
        this.applicantId = applicant;
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
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String company) {
        this.companyId = company;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicant) {
        this.applicantId = applicant;
    }
}

