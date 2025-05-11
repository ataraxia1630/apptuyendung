package com.example.workleap.data.model;

// JobSaved.java

import java.util.Date;

public class JobSaved {
    private String jobpostId;
    private String applicantId;
    private Date createdAt;
    private Date updatedAt;
    private JobPost jobPost;
    private Applicant applicant;

    // Getters and Setters

    public String getJobpostId() {
        return jobpostId;
    }

    public void setJobpostId(String jobpostId) {
        this.jobpostId = jobpostId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
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

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
