package com.example.workleap.data.model.entity;

import java.util.Date;

public class InterestedField {
    private String id;
    Field Field;
    private String applicantId;
    Applicant Applicant;
    private Date created_at;
    private Date updated_at;

    public InterestedField() {}

    public InterestedField(String id, String applicantId, Date created_at, Date updated_at) {
        this.id = id;
        this.applicantId = applicantId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public Date getCreatedAt() { return created_at; }
    public void setCreatedAt(Date created_at) { this.created_at = created_at; }
    public Date getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(Date updated_at) { this.updated_at = updated_at; }
    public Field getField() {return Field;};
}
