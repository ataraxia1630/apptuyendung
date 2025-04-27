package com.example.workleap.data.model;

import java.util.Date;
import java.util.List;

public class ApplicantEducation {
    private String id;
    private String eduId;
    private String applicantId;
    private Date eduStart;
    private Date eduEnd;
    private String major;
    private String eduLevel; // Enum: HIGH_SCHOOL, BACHELOR, MASTER, DOCTORATE
    private String moreInfo;
    private List<String> achievement; // Giả định là danh sách String
    private Date createdAt;
    private Date updatedAt;

    public ApplicantEducation() {}

    public ApplicantEducation(String id, String eduId, String applicantId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement, Date createdAt, Date updatedAt) {
        this.id = id;
        this.eduId = eduId;
        this.applicantId = applicantId;
        this.eduStart = eduStart;
        this.eduEnd = eduEnd;
        this.major = major;
        this.eduLevel = eduLevel;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEduId() { return eduId; }
    public void setEduId(String eduId) { this.eduId = eduId; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public Date getEduStart() { return eduStart; }
    public void setEduStart(Date eduStart) { this.eduStart = eduStart; }
    public Date getEduEnd() { return eduEnd; }
    public void setEduEnd(Date eduEnd) { this.eduEnd = eduEnd; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getEduLevel() { return eduLevel; }
    public void setEduLevel(String eduLevel) { this.eduLevel = eduLevel; }
    public String getMoreInfo() { return moreInfo; }
    public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }
    public List<String> getAchievement() { return achievement; }
    public void setAchievement(List<String> achievement) { this.achievement = achievement; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

