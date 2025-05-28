package com.example.workleap.data.model.request;

import java.util.Date;
import java.util.List;

public class CreateApplicantEducationRequest {
    private String eduId;
    private Date edu_start;
    private Date edu_end;
    private String major;
    private String eduLevel; // Enum: HIGH_SCHOOL, BACHELOR, MASTER, DOCTORATE
    private String moreInfo;
    private List<String> achievement; // Giả định là danh sách String

    public CreateApplicantEducationRequest() {}

    public CreateApplicantEducationRequest(String educationId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement) {
        this.eduId = educationId;
        this.edu_start = eduStart;
        this.edu_end = eduEnd;
        this.major = major;
        this.eduLevel = eduLevel;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
    }

    // Getter và Setter
    public String getEducationId(){return eduId;}
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
}
