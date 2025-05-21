package com.example.workleap.data.model.entity;

import java.util.Date;
import java.util.List;

public class JobCategory {
    private String id;
    private String fieldId;
    private String name;
    private Date created_at;
    private Date updated_at;
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

    public List<JobPost> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(List<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }
}
