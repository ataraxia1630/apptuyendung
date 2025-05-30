package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Skill;

public class CreateApplicantSkillResponse {
    String message;
    Skill skill;
    public CreateApplicantSkillResponse() {};
    public CreateApplicantSkillResponse(String message, Skill skill) {
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
