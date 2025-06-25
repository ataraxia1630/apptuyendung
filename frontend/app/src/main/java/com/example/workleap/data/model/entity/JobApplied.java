package com.example.workleap.data.model.entity;

// JobApplied.java

import java.util.Date;

public class JobApplied {
    private String id;
    private String jobpostId;
    private String applicantId;
    private String cvId;
    private String status;  // PENDING or other status
    private Date applyAt;
    private Date updated_at;
    private JobPost JobPost;
    private Applicant applicant;
    private CV CV;

    public JobApplied(){}

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplyAt() {
        return applyAt;
    }

    public void setApplyAt(Date applyAt) {
        this.applyAt = applyAt;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public JobPost getJobPost() {
        return JobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.JobPost = jobPost;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public CV getCV() {
        return CV;
    }

    public void setCV(CV CV) {
        this.CV = CV;
    }
}
