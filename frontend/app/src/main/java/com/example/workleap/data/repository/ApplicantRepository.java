package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.request.UpdateApplicantEducationRequest;
import com.example.workleap.data.model.request.UpdateApplicantExperienceRequest;
import com.example.workleap.data.model.response.ListEducationResponse;
import com.example.workleap.data.model.response.UpdateApplicantExperienceResponse;
import com.example.workleap.data.model.request.CreateApplicantEducationRequest;
import com.example.workleap.data.model.response.CreateApplicantEducationResponse;
import com.example.workleap.data.model.request.CreateApplicantSkillRequest;
import com.example.workleap.data.model.response.CreateApplicantSkillResponse;
import com.example.workleap.data.model.response.GetApplicantResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.UpdateApplicantEducationResponse;
import com.example.workleap.data.model.request.UpdateApplicantRequest;
import com.example.workleap.data.model.response.UpdateApplicantResponse;
import com.example.workleap.data.model.request.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.response.UpdateApplicantSkillResponse;

import java.util.List;

import retrofit2.Call;

public class ApplicantRepository {
    private ApiService apiService;

    private PreferencesManager preferencesManager;

    public ApplicantRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
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

    //Get all education
    public Call<ListEducationResponse> getAllEducation() {
        return apiService.getAllEducation();
    }

    //Create education
    public Call<MessageResponse> createEducation(Education education) {
        return apiService.createEducation(education);
    }

    //Create applicant education
    public Call<CreateApplicantEducationResponse> createApplicantEducation(String applicantId, CreateApplicantEducationRequest request) {
        return apiService.createApplicantEducation(applicantId, request);
    }

    //Update applicant education
    public Call<UpdateApplicantEducationResponse> updateApplicantEducation(String educationId, UpdateApplicantEducationRequest request) {
        return apiService.updateApplicantEducation(educationId, request);
    }

    //Delete applicant education
    public Call<MessageResponse> deleteApplicantEducation(String id) {
        return apiService.deleteApplicantEducation(id);
    }

    //Delete all applicant education
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
