package com.example.workleap.data.model.entity;

import java.util.Date;
import java.util.List;

public class Experience {
    private String id;
    private String applicantId;
    private String companyName;
    private String companyLink;
    private String position;
    private Date workStart;
    private Date workEnd;
    private String jobResponsibility;
    private String moreInfo;
    private List<String> achievement;
    private Date createdAt;
    private Date updatedAt;

    public Experience() {}

    public Experience(String id, String applicantId, String companyName, String companyLink, String position, Date workStart, Date workEnd, String jobResponsibility, String moreInfo, List<String> achievement, Date createdAt, Date updatedAt) {
        this.id = id;
        this.applicantId = applicantId;
        this.companyName = companyName;
        this.companyLink = companyLink;
        this.position = position;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.jobResponsibility = jobResponsibility;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyLink() { return companyLink; }
    public void setCompanyLink(String companyLink) { this.companyLink = companyLink; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Date getWorkStart() { return workStart; }
    public void setWorkStart(Date workStart) { this.workStart = workStart; }
    public Date getWorkEnd() { return workEnd; }
    public void setWorkEnd(Date workEnd) { this.workEnd = workEnd; }
    public String getJobResponsibility() { return jobResponsibility; }
    public void setJobResponsibility(String jobResponsibility) { this.jobResponsibility = jobResponsibility; }
    public String getMoreInfo() { return moreInfo; }
    public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }
    public List<String> getAchievement() { return achievement; }
    public void setAchievement(List<String> achievement) { this.achievement = achievement; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
