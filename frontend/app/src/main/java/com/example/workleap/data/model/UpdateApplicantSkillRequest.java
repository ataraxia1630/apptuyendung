package com.example.workleap.data.model;

public class UpdateApplicantSkillRequest {
    private String skillName;
    private String description;
    private String certificate;
    private String expertiseLevel; // Enum: BEGINNER, INTERMEDIATE, ADVANCED
    private Integer yearOfExp;
    private Integer monthOfExp;
    public UpdateApplicantSkillRequest() {};

    public UpdateApplicantSkillRequest(String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp) {
        this.skillName = skillName;
        this.description = description;
        this.certificate = certificate;
        this.expertiseLevel = expertiseLevel;
        this.yearOfExp = yearOfExp;
        this.monthOfExp = monthOfExp;
    }
    public String getSkillName() { return skillName; }
    public String getDescription() { return description; }
    public String getCertificate() { return certificate; }
    public String getExpertiseLevel() { return expertiseLevel; }

    public Integer getYearOfExp() { return yearOfExp; }
    public Integer getMonthOfExp() { return monthOfExp; }
    public String setSkillName(String skillName) { this.skillName = skillName; return skillName; }
    public String setDescription(String description) { this.description = description; return description; }

    public String setCertificate(String certificate) { this.certificate = certificate; return certificate; }
    public String setExpertiseLevel(String expertiseLevel) { this.expertiseLevel = expertiseLevel; return expertiseLevel; }

    public Integer setYearOfExp(Integer yearOfExp) { this.yearOfExp = yearOfExp; return yearOfExp; }
    public Integer setMonthOfExp(Integer monthOfExp) { this.monthOfExp = monthOfExp; return monthOfExp; }
}
