package com.example.workleap.data.model.entity;

import java.util.Date;

public class InterestedField {
    private String fieldId;
    private String applicantId;
    private Date createdAt;
    private Date updatedAt;

    public InterestedField() {}

    public InterestedField(String fieldId, String applicantId, Date createdAt, Date updatedAt) {
        this.fieldId = fieldId;
        this.applicantId = applicantId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter và Setter
    public String getFieldId() { return fieldId; }
    public void setFieldId(String fieldId) { this.fieldId = fieldId; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
