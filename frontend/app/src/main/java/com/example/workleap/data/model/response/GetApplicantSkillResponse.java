package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Skill;

import java.util.List;

public class GetApplicantSkillResponse {
    String message;
    List<Skill> skills;

    public GetApplicantSkillResponse() {};

    public GetApplicantSkillResponse(String message, List<Skill> skills) {
        this.message = message;
        this.skills = skills;
    }
    public String getMessage() {
        return message;
    }
    public List<Skill> getSkills() {
        return skills;
    }
}
