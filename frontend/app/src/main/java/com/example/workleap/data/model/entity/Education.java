package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.List;

public class Education implements Serializable{
    private String id;
    private String uniName;
    private String description;
    private String uniLink;
    private String address;
    private List<ApplicantEducation> applicantEducation; // Quan hệ 1-n
    private String createdAt;
    private String updatedAt;

    // Default constructor
    public Education() {
    }

    // Full constructor
    public Education(String id, String uniName, String description, String uniLink, String address, List<ApplicantEducation> applicantEducation, String createdAt, String updatedAt) {
        this.id = id;
        this.uniName = uniName;
        this.description = description;
        this.uniLink = uniLink;
        this.address = address;
        this.applicantEducation = applicantEducation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniLink() {
        return uniLink;
    }

    public void setUniLink(String uniLink) {
        this.uniLink = uniLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ApplicantEducation> getApplicantEducation() {
        return applicantEducation;
    }

    public void setApplicantEducation(List<ApplicantEducation> applicantEducation) {
        this.applicantEducation = applicantEducation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
