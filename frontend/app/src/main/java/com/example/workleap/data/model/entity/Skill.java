package com.example.workleap.data.model.entity;

import java.util.Date;

public class Skill {
    private String id;
    private String applicantId;
    private String skillName;
    private String description;
    private String certificate;
    private String expertiseLevel; // Enum: BEGINNER, INTERMEDIATE, ADVANCED
    private Integer yearOfExp;
    private Integer monthOfExp;
    private Date created_at;
    private Date updated_at;

    public Skill() {}

    public Skill(String id, String applicantId, String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp, Date created_at, Date updated_at) {
        this.id = id;
        this.applicantId = applicantId;
        this.skillName = skillName;
        this.description = description;
        this.certificate = certificate;
        this.expertiseLevel = expertiseLevel;
        this.yearOfExp = yearOfExp;
        this.monthOfExp = monthOfExp;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCertificate() { return certificate; }
    public void setCertificate(String certificate) { this.certificate = certificate; }
    public String getExpertiseLevel() { return expertiseLevel; }
    public void setExpertiseLevel(String expertiseLevel) { this.expertiseLevel = expertiseLevel; }
    public Integer getYearOfExp() { return yearOfExp; }
    public void setYearOfExp(Integer yearOfExp) { this.yearOfExp = yearOfExp; }
    public Integer getMonthOfExp() { return monthOfExp; }
    public void setMonthOfExp(Integer monthOfExp) { this.monthOfExp = monthOfExp; }
    public Date getCreatedAt() { return created_at; }
    public void setCreatedAt(Date created_at) { this.created_at = created_at; }
    public Date getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(Date updated_at) { this.updated_at = updated_at; }

    @Override
    public String toString() {
        return "Skill{" +
                "id='" + id + '\'' +
                ", applicantId='" + applicantId + '\'' +
                ", skillName='" + skillName + '\'' +
                ", description='" + description + '\'' +
                ", certificate='" + certificate + '\'' +
                ", expertiseLevel='" + expertiseLevel + '\'' +
                ", yearOfExp=" + yearOfExp +
                ", monthOfExp=" + monthOfExp +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
