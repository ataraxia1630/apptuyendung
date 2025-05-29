package com.example.workleap.data.model.request;

import java.util.Date;
import java.util.List;

public class UpdateApplicantEducationRequest {
    private String eduId;
    private Date eduStart;
    private Date eduEnd;
    private String major;
    private String eduLevel; // Enum: HIGH_SCHOOL, BACHELOR, MASTER, DOCTORATE
    private String moreInfo;
    private List<String> achievement; // Giả định là danh sách String

    public UpdateApplicantEducationRequest() {}

    public UpdateApplicantEducationRequest(String eduId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement) {
        this.eduId = eduId;
        this.eduStart = eduStart;
        this.eduEnd = eduEnd;
        this.major = major;
        this.eduLevel = eduLevel;
        this.moreInfo = moreInfo;
        this.achievement = achievement;
    }

    // Getter và Setter
    public String getEduId () {return eduId;}
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
}
