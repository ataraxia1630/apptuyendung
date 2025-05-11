package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.api.CreateApplicantExperienceRequest;
import com.example.workleap.data.api.CreateApplicantExperienceResponse;
import com.example.workleap.data.api.CreateInterestedFieldResponse;
import com.example.workleap.data.api.UpdateApplicantEducationRequest;
import com.example.workleap.data.api.UpdateApplicantExperienceRequest;
import com.example.workleap.data.api.UpdateApplicantExperienceResponse;
import com.example.workleap.data.model.Applicant;
import com.example.workleap.data.model.CreateApplicantEducationRequest;
import com.example.workleap.data.model.CreateApplicantEducationResponse;
import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantSkillResponse;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.UpdateApplicantEducationResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.UpdateApplicantSkillResponse;
import com.example.workleap.data.repository.ApplicantRepository;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantViewModel extends ViewModel {
    private ApplicantRepository applicantRepository;

    private MutableLiveData<String> updateApplicantResult = new MutableLiveData<>();
    private MutableLiveData<String> getApplicantResult = new MutableLiveData<>();
    private MutableLiveData<Applicant> getApplicantData = new MutableLiveData<>();
    private MutableLiveData<String> createApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> updateApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllApplicantSkillResult = new MutableLiveData<>();

    private MutableLiveData<String> createApplicantEducationResult = new MutableLiveData<>();
    private MutableLiveData<String> updateApplicantEducationResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteApplicantEducationResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllApplicantEducationResult = new MutableLiveData<>();

    private MutableLiveData<String> createApplicantExperienceResult = new MutableLiveData<>();
    private MutableLiveData<String> updateApplicantExperienceResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteApplicantExperienceResult = new MutableLiveData<>();

    private MutableLiveData<String> createInterestedFieldResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteInterestedFieldResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllInterestedFieldResult = new MutableLiveData<>();

    public ApplicantViewModel(Context context) {
        applicantRepository = new ApplicantRepository(context);
    }

    // Getter cho LiveData
    public LiveData<String> getUpdateApplicantResult() { return updateApplicantResult; }
    public LiveData<String> getGetApplicantResult() { return getApplicantResult; }
    public LiveData<Applicant> getGetApplicantData() { return getApplicantData; }

    public LiveData<String> getCreateApplicantSkillResult() { return createApplicantSkillResult; }
    public LiveData<String> getUpdateApplicantSkillResult() { return updateApplicantSkillResult; }
    public LiveData<String> getDeleteApplicantSkillResult() { return deleteApplicantSkillResult; }
    public LiveData<String> getDeleteAllApplicantSkillResult() { return deleteAllApplicantSkillResult; }

    public LiveData<String> getCreateApplicantEducationResult() { return createApplicantSkillResult; }
    public LiveData<String> getUpdateApplicantEducationResult() { return updateApplicantSkillResult; }
    public LiveData<String> getDeleteApplicantEducationResult() { return deleteApplicantSkillResult; }
    public LiveData<String> getDeleteAllApplicantEducationResult() { return deleteAllApplicantSkillResult; }

    public LiveData<String> getCreateApplicantExperienceResult() { return createApplicantSkillResult; }
    public LiveData<String> getUpdateApplicantExperienceResult() { return updateApplicantSkillResult; }
    public LiveData<String> getDeleteApplicantExperienceResult() { return deleteApplicantSkillResult; }

    public LiveData<String> getCreateInterestedFieldResult() { return createApplicantSkillResult; }
    public LiveData<String> getDeleteInterestedFieldResult() { return deleteApplicantSkillResult; }
    public LiveData<String> getDeleteAllInterestedFieldResult() { return deleteAllApplicantSkillResult; }

    // Get applicant
    public void getApplicant(String id) {
        Call<GetApplicantResponse> call = applicantRepository.getApplicant(id);
        call.enqueue(new Callback<GetApplicantResponse>() {
            @Override
            public void onResponse(Call<GetApplicantResponse> call, Response<GetApplicantResponse> response) {
                if (response.isSuccessful()) {
                    GetApplicantResponse getResponse = response.body();
                    getApplicantResult.setValue(getResponse.getMessage());
                    getApplicantData.setValue(getResponse.getApplicant());
                } else {
                    try {
                        GetApplicantResponse error = new Gson().fromJson(response.errorBody().string(), GetApplicantResponse.class);
                        getApplicantResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getApplicantResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetApplicantResponse> call, Throwable t) {
                getApplicantResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Update applicant
    public void updateApplicant(String id,  String address, String firstName, String lastName, String profileSummary, byte[] cvFile) {
        UpdateApplicantRequest request = new UpdateApplicantRequest(address, firstName, lastName, profileSummary, cvFile);
        Call<UpdateApplicantResponse> call = applicantRepository.updateApplicant(id, request);
        call.enqueue(new Callback<UpdateApplicantResponse>() {
            @Override
            public void onResponse(Call<UpdateApplicantResponse> call, Response<UpdateApplicantResponse> response) {
                if (response.isSuccessful()) {
                    UpdateApplicantResponse updateResponse = response.body();
                    updateApplicantResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateApplicantResponse error = new Gson().fromJson(response.errorBody().string(), UpdateApplicantResponse.class);
                        updateApplicantResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateApplicantResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateApplicantResponse> call, Throwable t) {
                updateApplicantResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create Skill
    public void createApplicantSkill(String applicantId, String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp) {
        CreateApplicantSkillRequest request = new CreateApplicantSkillRequest(skillName, description, certificate, expertiseLevel, yearOfExp, monthOfExp);
        Call<CreateApplicantSkillResponse> call = applicantRepository.createApplicantSkill(applicantId, request);
        call.enqueue(new Callback<CreateApplicantSkillResponse>() {
            @Override
            public void onResponse(Call<CreateApplicantSkillResponse> call, Response<CreateApplicantSkillResponse> response) {
                if (response.isSuccessful()) {
                    CreateApplicantSkillResponse createResponse = response.body();
                    createApplicantSkillResult.setValue(createResponse.getMessage());
                } else {
                    try {
                        CreateApplicantSkillResponse error = new Gson().fromJson(response.errorBody().string(), CreateApplicantSkillResponse.class);
                        createApplicantSkillResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createApplicantSkillResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<CreateApplicantSkillResponse> call, Throwable t) {
                createApplicantSkillResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Update Skill
    public void updateApplicantSkill(String skillId, String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp) {
        UpdateApplicantSkillRequest request = new UpdateApplicantSkillRequest(skillName, description, certificate, expertiseLevel, yearOfExp, monthOfExp);
        Call<UpdateApplicantSkillResponse> call = applicantRepository.updateApplicantSkill(skillId, request);
        call.enqueue(new Callback<UpdateApplicantSkillResponse>() {
            @Override
            public void onResponse(Call<UpdateApplicantSkillResponse> call, Response<UpdateApplicantSkillResponse> response) {
                if (response.isSuccessful()) {
                    UpdateApplicantSkillResponse updateResponse = response.body();
                    updateApplicantSkillResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateApplicantSkillResponse error = new Gson().fromJson(response.errorBody().string(), UpdateApplicantSkillResponse.class);
                        updateApplicantSkillResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateApplicantSkillResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateApplicantSkillResponse> call, Throwable t) {
                updateApplicantSkillResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete Skill
    public void deleteApplicantSkill(String skillId) {
        Call<MessageResponse> call = applicantRepository.deleteApplicantSkill(skillId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteApplicantSkillResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteApplicantSkillResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteApplicantSkillResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteApplicantSkillResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete All Skill
    public void deleteAllApplicantSkill(String applicantId) {
        Call<MessageResponse> call = applicantRepository.deleteAllApplicantSkill(applicantId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteAllApplicantSkillResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteAllApplicantSkillResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteAllApplicantSkillResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteAllApplicantSkillResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create Education
    public void createApplicantEducation(String applicantId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement) {
        CreateApplicantEducationRequest request = new CreateApplicantEducationRequest(eduStart, eduEnd, major, eduLevel, moreInfo, achievement);
        Call<CreateApplicantEducationResponse> call = applicantRepository.createApplicantEducation(applicantId, request);
        call.enqueue(new Callback<CreateApplicantEducationResponse>() {
            @Override
            public void onResponse(Call<CreateApplicantEducationResponse> call, Response<CreateApplicantEducationResponse> response) {
                if (response.isSuccessful()) {
                    CreateApplicantEducationResponse createResponse = response.body();
                    createApplicantEducationResult.setValue(createResponse.getMessage());
                } else {
                    try {
                        CreateApplicantEducationResponse error = new Gson().fromJson(response.errorBody().string(), CreateApplicantEducationResponse.class);
                        createApplicantEducationResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createApplicantEducationResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<CreateApplicantEducationResponse> call, Throwable t) {
                createApplicantEducationResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Update Education
    public void updateApplicantEducation(String educationId, Date eduStart, Date eduEnd, String major, String eduLevel, String moreInfo, List<String> achievement) {
        UpdateApplicantEducationRequest request = new UpdateApplicantEducationRequest(eduStart, eduEnd, major, eduLevel, moreInfo, achievement);
        Call<UpdateApplicantEducationResponse> call = applicantRepository.updateApplicantEducation(educationId, request);
        call.enqueue(new Callback<UpdateApplicantEducationResponse>() {
            @Override
            public void onResponse(Call<UpdateApplicantEducationResponse> call, Response<UpdateApplicantEducationResponse> response) {
                if (response.isSuccessful()) {
                    UpdateApplicantEducationResponse updateResponse = response.body();
                    updateApplicantEducationResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateApplicantEducationResponse error = new Gson().fromJson(response.errorBody().string(), UpdateApplicantEducationResponse.class);
                        updateApplicantEducationResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateApplicantEducationResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateApplicantEducationResponse> call, Throwable t) {
                updateApplicantEducationResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete Education
    public void deleteApplicantEducation(String educationId) {
        Call<MessageResponse> call = applicantRepository.deleteApplicantEducation(educationId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteApplicantEducationResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteApplicantEducationResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteApplicantEducationResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteApplicantEducationResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete All Education
    public void deleteAllApplicantEducation(String applicantId) {
        Call<MessageResponse> call = applicantRepository.deleteAllApplicantEducation(applicantId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteAllApplicantEducationResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteAllApplicantEducationResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteAllApplicantEducationResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteAllApplicantEducationResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create Experience
    public void createApplicantExperience(String applicantId, String companyName, String companyLink, String position, Date workStart, Date workEnd, String jobResponsibility, String moreInfo, List<String> achievement) {
        CreateApplicantExperienceRequest request = new CreateApplicantExperienceRequest(companyName, companyLink, position, workStart, workEnd, jobResponsibility, moreInfo, achievement);
        Call<CreateApplicantExperienceResponse> call = applicantRepository.createApplicantExperience(applicantId, request);
        call.enqueue(new Callback<CreateApplicantExperienceResponse>() {
            @Override
            public void onResponse(Call<CreateApplicantExperienceResponse> call, Response<CreateApplicantExperienceResponse> response) {
                if (response.isSuccessful()) {
                    CreateApplicantExperienceResponse createResponse = response.body();
                    createApplicantExperienceResult.setValue(createResponse.getMessage());
                } else {
                    try {
                        CreateApplicantExperienceResponse error = new Gson().fromJson(response.errorBody().string(), CreateApplicantExperienceResponse.class);
                        createApplicantExperienceResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createApplicantExperienceResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<CreateApplicantExperienceResponse> call, Throwable t) {
                createApplicantExperienceResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Update Experience
    public void updateApplicantExperience(String experienceId, String companyName, String companyLink, String position, Date workStart, Date workEnd, String jobResponsibility, String moreInfo, List<String> achievement) {
        UpdateApplicantExperienceRequest request = new UpdateApplicantExperienceRequest(companyName, companyLink, position, workStart, workEnd, jobResponsibility, moreInfo, achievement);
        Call<UpdateApplicantExperienceResponse> call = applicantRepository.updateApplicantExperience(experienceId, request);
        call.enqueue(new Callback<UpdateApplicantExperienceResponse>() {
            @Override
            public void onResponse(Call<UpdateApplicantExperienceResponse> call, Response<UpdateApplicantExperienceResponse> response) {
                if (response.isSuccessful()) {
                    UpdateApplicantExperienceResponse updateResponse = response.body();
                    updateApplicantExperienceResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateApplicantExperienceResponse error = new Gson().fromJson(response.errorBody().string(), UpdateApplicantExperienceResponse.class);
                        updateApplicantExperienceResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateApplicantExperienceResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateApplicantExperienceResponse> call, Throwable t) {
                updateApplicantExperienceResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete Experience
    public void deleteApplicantExperience(String experienceId) {
        Call<MessageResponse> call = applicantRepository.deleteApplicantExperience(experienceId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteApplicantExperienceResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteApplicantExperienceResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteApplicantExperienceResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteApplicantExperienceResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create InterestedField
    public void createInterestedField(String applicantId) {
        Call<CreateInterestedFieldResponse> call = applicantRepository.createInterestedField(applicantId);
        call.enqueue(new Callback<CreateInterestedFieldResponse>() {
            @Override
            public void onResponse(Call<CreateInterestedFieldResponse> call, Response<CreateInterestedFieldResponse> response) {
                if (response.isSuccessful()) {
                    CreateInterestedFieldResponse createResponse = response.body();
                    createInterestedFieldResult.setValue(createResponse.getMessage());
                } else {
                    try {
                        CreateInterestedFieldResponse error = new Gson().fromJson(response.errorBody().string(), CreateInterestedFieldResponse.class);
                        createInterestedFieldResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createInterestedFieldResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<CreateInterestedFieldResponse> call, Throwable t) {
                createInterestedFieldResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete InterestedField
    public void deleteInterestedField(String fieldId) {
        Call<MessageResponse> call = applicantRepository.deleteInterestedField(fieldId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteInterestedFieldResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteInterestedFieldResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteInterestedFieldResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteInterestedFieldResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete All InterestedField
    public void deleteAllInterestedField(String applicantId) {
        Call<MessageResponse> call = applicantRepository.deleteAllInterestedField(applicantId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful())
                {
                    MessageResponse deleteResponse = response.body();
                    deleteAllInterestedFieldResult.setValue(deleteResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteAllInterestedFieldResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteAllInterestedFieldResult.setValue
                                ("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteAllInterestedFieldResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}