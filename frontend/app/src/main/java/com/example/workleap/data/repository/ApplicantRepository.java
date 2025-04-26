package com.example.workleap.data.repository;

import com.example.workleap.data.api.RetrofitClient;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.GetApplicantSkillResponse;
import com.example.workleap.data.model.MessageResponse;
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

    //Get Skill
    public Call<GetApplicantSkillResponse> getApplicantSkill(String applicantId) {
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
}
