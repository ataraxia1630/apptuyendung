package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Experience;

public class UpdateApplicantExperienceResponse {
    String message;
    Experience experience;

    public UpdateApplicantExperienceResponse() {}

    public UpdateApplicantExperienceResponse(String message, Experience experience) {
        this.message = message;
        this.experience = experience;
    }

    public String getMessage() { return message; }
    public Experience getExperience() { return experience; }
}
