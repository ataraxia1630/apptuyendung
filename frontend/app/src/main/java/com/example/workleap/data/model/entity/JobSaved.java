package com.example.workleap.data.model.entity;

// JobSaved.java

import java.util.Date;

public class JobSaved {
    private String jobpostId;
    private String applicantId;
    private Date created_at;
    private Date updated_at;
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
