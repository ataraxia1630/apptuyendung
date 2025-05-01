package com.example.workleap.data.repository;

import com.example.workleap.data.api.CreateApplicantExperienceRequest;
import com.example.workleap.data.api.CreateApplicantExperienceResponse;
import com.example.workleap.data.api.CreateInterestedFieldResponse;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.UpdateApplicantEducationRequest;
import com.example.workleap.data.api.UpdateApplicantExperienceRequest;
import com.example.workleap.data.api.UpdateApplicantExperienceResponse;
import com.example.workleap.data.model.CreateApplicantEducationRequest;
import com.example.workleap.data.model.CreateApplicantEducationResponse;
import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetApplicantSkillResponse;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.UpdateApplicantEducationResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.UpdateApplicantSkillResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantRepository {
    private ApiService apiService;
    public ApplicantRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    //Get
    public Call<GetApplicantResponse> getApplicant(String id) {
        return apiService.getApplicant(id);
    }

    //Update
    public Call<UpdateApplicantResponse> updateApplicant(String id, UpdateApplicantRequest request) {
        return apiService.updateApplicant(id, request);
    }

    //Create skill
    public Call<CreateApplicantSkillResponse> createApplicantSkill(String applicantId, CreateApplicantSkillRequest request) {
        return apiService.createApplicantSkill(applicantId, request);
    }

    //Update skill
    public Call<UpdateApplicantSkillResponse> updateApplicantSkill(String skillId, UpdateApplicantSkillRequest request) {
        return apiService.updateApplicantSkill(skillId, request);
    }

    //Delete skill
    public Call<MessageResponse> deleteApplicantSkill(String id) {
        return apiService.deleteApplicantSkill(id);
    }

    //Delete all skill
    public Call<MessageResponse> deleteAllApplicantSkill(String id) {
        return apiService.deleteAllApplicantSkill(id);
    }

    //Create education
    public Call<CreateApplicantEducationResponse> createApplicantEducation(String applicantId, CreateApplicantEducationRequest request) {
        return apiService.createApplicantEducation(applicantId, request);
    }

    //Update education
    public Call<UpdateApplicantEducationResponse> updateApplicantEducation(String educationId, UpdateApplicantEducationRequest request) {
        return apiService.updateApplicantEducation(educationId, request);
    }

    //Delete education
    public Call<MessageResponse> deleteApplicantEducation(String id) {
        return apiService.deleteApplicantEducation(id);
    }

    //Delete all education
    public Call<MessageResponse> deleteAllApplicantEducation(String id) {
        return apiService.deleteAllApplicantEducation(id);
    }

    //Create experience
    public Call<CreateApplicantExperienceResponse> createApplicantExperience(String applicantId, CreateApplicantExperienceRequest request) {
        return apiService.createApplicantExperience(applicantId, request);
    }

    //Update experience
    public Call<UpdateApplicantExperienceResponse> updateApplicantExperience(String experienceId, UpdateApplicantExperienceRequest request) {
        return apiService.updateApplicantExperience(experienceId, request);
    }

    //Delete experience
    public Call<MessageResponse> deleteApplicantExperience(String id) {
        return apiService.deleteApplicantExperience(id);
    }

    //Create interested field
    public Call<CreateInterestedFieldResponse> createInterestedField(String applicantId) {
        return apiService.createInterestedField(applicantId);
    }

    //Delete interested field
    public Call<MessageResponse> deleteInterestedField(String id) {
        return apiService.deleteInterestedField(id);
    }

    //Delete all interested field
    public Call<MessageResponse> deleteAllInterestedField(String id) {
        return apiService.deleteAllInterestedField(id);
    }
}
