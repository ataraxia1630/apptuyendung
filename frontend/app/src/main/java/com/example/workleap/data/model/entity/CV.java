package com.example.workleap.data.model.entity;

import java.util.Date;
import java.util.List;

public class CV {
    private String id;
    private String applicantId;
    private String title;
    private String filePath;
    private int viewCount;
    private Date created_at;
    private Date updated_at;
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

    public List<JobApplied> getAppliedJob() {
        return appliedJob;
    }

    public void setAppliedJob(List<JobApplied> appliedJob) {
        this.appliedJob = appliedJob;
    }
}
