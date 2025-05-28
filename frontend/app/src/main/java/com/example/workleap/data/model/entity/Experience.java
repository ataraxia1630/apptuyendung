package com.example.workleap.data.model.entity;

import java.util.Date;
import java.util.List;

public class Experience {
    private String id;
    private String applicantId;
    private String companyName;
    private String companyLink;
    private String position;
    private Date work_start;
    private Date work_end;
    private String jobResponsibility;
    private String moreInfo;
    private List<String> achievement;
    private Date created_at;
    private Date updated_at;

    public Experience() {}

    public Experience(String id, String applicantId, String companyName, String companyLink, String position, Date workStart, Date workEnd, String jobResponsibility, String moreInfo, List<String> achievement, Date created_at, Date updated_at) {
        this.id = id;
        this.applicantId = applicantId;
        this.companyName = companyName;
        this.companyLink = companyLink;
        this.position = position;
        this.work_start = workStart;
        this.work_end = workEnd;
        this.jobResponsibility = jobResponsibility;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
        this.created_at = created_at;
        this.updated_at = updated_at;
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
    public Date getWorkStart() { return work_start; }
    public void setWorkStart(Date workStart) { this.work_start = workStart; }
    public Date getWorkEnd() { return work_end; }
    public void setWorkEnd(Date workEnd) { this.work_end = workEnd; }
    public String getJobResponsibility() { return jobResponsibility; }
    public void setJobResponsibility(String jobResponsibility) { this.jobResponsibility = jobResponsibility; }
    public String getMoreInfo() { return moreInfo; }
    public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }
    public List<String> getAchievement() { return achievement; }
    public void setAchievement(List<String> achievement) { this.achievement = achievement; }
    public Date getCreatedAt() { return created_at; }
    public void setCreatedAt(Date created_at) { this.created_at = created_at; }
    public Date getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(Date updated_at) { this.updated_at = updated_at; }
}
