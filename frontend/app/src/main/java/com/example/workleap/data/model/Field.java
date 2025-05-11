package com.example.workleap.data.model;

import java.util.Date;
import java.util.List;

public class Field {
    private String id;
    private String name;
    private Date created_at;
    private Date updated_at;
    private List<InterestedField> interested_Field;
    private List<JobCategory> jobCategory;

    // Constructor mặc định
    public Field() {}

    // Constructor đầy đủ
    public Field(String id, String name, Date created_at, Date updated_at, List<InterestedField> interested_Field, List<JobCategory> jobCategory) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.interested_Field = interested_Field;
        this.jobCategory = jobCategory;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<InterestedField> getInterested_Field() {
        return interested_Field;
    }

    public void setInterested_Field(List<InterestedField> interested_Field) {
        this.interested_Field = interested_Field;
    }

    public List<JobCategory> getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(List<JobCategory> jobCategory) {
        this.jobCategory = jobCategory;
    }
}
