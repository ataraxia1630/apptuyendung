package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Report implements Serializable {

    private String id;
    private String userId;
    private String reportedUserId;
    private String postId;
    private String jobPostId;
    private String reason;
    private Date created_at;

    // Constructors
    public Report() {}

    public Report(String id, String userId, String reportedUserId, String postId, String jobPostId,
                  String reason, Date created_at) {
        this.id = id;
        this.userId = userId;
        this.reportedUserId = reportedUserId;
        this.postId = postId;
        this.jobPostId = jobPostId;
        this.reason = reason;
        this.created_at = created_at;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(String jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
