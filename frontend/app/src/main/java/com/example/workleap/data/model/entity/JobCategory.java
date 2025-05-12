package com.example.workleap.data.model.entity;

import java.util.Date;
import java.util.List;

public class JobCategory {
    private String id;
    private String fieldId;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private Field field; // Assuming you define the Field model
    private List<JobPost> jobPosts;

    // Getters and Setters
    String getId() { return id; }
    void setId(String id) { this.id = id; }
    String getFieldId() { return fieldId; }
    void setFieldId(String fieldId) { this.fieldId = fieldId; }
    String getName() { return name; }
    void setName(String name) { this.name = name; }

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

    public List<JobPost> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(List<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }
}
