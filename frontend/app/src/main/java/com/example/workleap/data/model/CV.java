package com.example.workleap.data.model;

import java.util.Date;
import java.util.List;

public class CV {
    private String id;
    private String applicantId;
    private String title;
    private String filePath;
    private int viewCount;
    private Date createdAt;
    private Date updatedAt;
    private List<JobApplied> appliedJob;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

    public List<JobApplied> getAppliedJob() {
        return appliedJob;
    }

    public void setAppliedJob(List<JobApplied> appliedJob) {
        this.appliedJob = appliedJob;
    }
}
