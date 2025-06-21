package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApplicantEducation implements Serializable {
    private String id;
    private String eduId;
    private Education education;
    private String applicantId;
    private Date edu_start;
    private Date edu_end;
    private String major;
    private String eduLevel; // Enum: HIGH_SCHOOL, BACHELOR, MASTER, DOCTORATE
    private String moreInfo;
    private List<String> achievement; // Giả định là danh sách String
    private Date created_at;
    private Date updated_at;

    public ApplicantEducation() {}


    public ApplicantEducation(String id, String eduId, String applicantId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement, Date created_at, Date updated_at) {
        this.id = id;
        this.eduId = eduId;
        this.applicantId = applicantId;
        this.edu_start = eduStart;
        this.edu_end = eduEnd;
        this.major = major;
        this.eduLevel = eduLevel;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEduId() { return eduId; }
    public void setEduId(String eduId) { this.eduId = eduId; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public Date getEduStart() { return edu_start; }
    public void setEduStart(Date eduStart) { this.edu_start = eduStart; }
    public Date getEduEnd() { return edu_end; }
    public void setEduEnd(Date eduEnd) { this.edu_end = eduEnd; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getEduLevel() { return eduLevel; }
    public void setEduLevel(String eduLevel) { this.eduLevel = eduLevel; }
    public String getMoreInfo() { return moreInfo; }
    public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }
    public List<String> getAchievement() { return achievement; }
    public void setAchievement(List<String> achievement) { this.achievement = achievement; }

    public Date getCreatedAt() { return created_at; }
    public void setCreatedAt(Date created_at) { this.created_at = created_at; }
    public Date getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(Date updated_at) { this.updated_at = updated_at; }
    public Education getEducation() {
        return education;
    }
}

