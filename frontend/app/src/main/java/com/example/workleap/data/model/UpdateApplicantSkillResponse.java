package com.example.workleap.data.model;

public class UpdateApplicantSkillResponse {
    String message;
    Skill skill;
    public UpdateApplicantSkillResponse() {};

    public UpdateApplicantSkillResponse(String message, Skill skill) {
        this.message = message;
        this.skill = skill;
    }

    public String getMessage() {
        return message;
    }
    public Skill getSkill() {
        return skill;
    }
}
