package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.request.ListFieldIdRequest;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.request.UpdateApplicantEducationRequest;
import com.example.workleap.data.model.request.UpdateApplicantExperienceRequest;
import com.example.workleap.data.model.response.FieldResponse;
import com.example.workleap.data.model.response.ListApplicantResponse;
import com.example.workleap.data.model.response.ListExperienceResponse;
import com.example.workleap.data.model.response.ListFieldResponse;
import com.example.workleap.data.model.response.ListInterestedFieldResponse;
import com.example.workleap.data.model.response.ListSkillResponse;
import com.example.workleap.data.model.response.ListApplicantEducationResponse;
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
        apiService = RetrofitClient.getClient(context).create(ApiService.class);
    }

    //Get
    public Call<GetApplicantResponse> getApplicant(String id) {
        return apiService.getApplicant(id);
    }
    public Call<ListApplicantResponse> getAllApplicant()
    {
        return apiService.getAllApplicant();
    }

    //Update
    public Call<UpdateApplicantResponse> updateApplicant(String id, UpdateApplicantRequest request) {
        return apiService.updateApplicant(id, request);
    }

    //Get skill
    public Call<ListSkillResponse> getApplicantSkill(String applicantId) {
        return apiService.getApplicantSkill(applicantId);
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

    //Get all applicant education
    public Call<ListApplicantEducationResponse> getAllApplicantEducation(String applicantId) {
        return apiService.getAllApplicantEducation(applicantId);
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

    //Get applicant experience
    public Call<ListExperienceResponse> getApplicantExperience(String applicantId) {
        return apiService.getApplicantExperience(applicantId);
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

    //Get all field
    public Call<ListFieldResponse> getAllFields() {
        return apiService.getAllFields();
    }

    //Get applicant field
    public Call<ListFieldResponse> getInterestedField(String applicantId) {
        return apiService.getInterestedField(applicantId);
    }

    //Get field by name or id
    public Call<FieldResponse> getFieldByName(String name) {
        return apiService.getFieldByName(name);
    }

    //Create interested field
    public Call<CreateInterestedFieldResponse> createInterestedField(String applicantId, ListFieldIdRequest request) {
        return apiService.createInterestedField(applicantId, request);
    }

    //Delete interested field
    public Call<MessageResponse> deleteInterestedField(String applicantId, String fieldId) {
        return apiService.deleteInterestedField(applicantId, fieldId);
    }

    //Delete all interested field
    public Call<MessageResponse> deleteAllInterestedField(String id) {
        return apiService.deleteAllInterestedField(id);
    }
}
