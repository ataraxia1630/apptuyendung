package com.example.workleap.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.workleap.data.model.CreateApplicantSkillRequest;
import com.example.workleap.data.model.CreateApplicantSkillResponse;
import com.example.workleap.data.model.GetApplicantSkillResponse;
import com.example.workleap.data.model.MessageResponse;
import com.example.workleap.data.model.UpdateApplicantRequest;
import com.example.workleap.data.model.UpdateApplicantResponse;
import com.example.workleap.data.model.GetApplicantResponse;
import com.example.workleap.data.model.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.UpdateApplicantSkillResponse;
import com.example.workleap.data.repository.ApplicantRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantViewModel {
    private ApplicantRepository applicantRepository;
    private MutableLiveData<String> updateApplicantResult = new MutableLiveData<>();
    private MutableLiveData<String> getApplicantResult = new MutableLiveData<>();
    private MutableLiveData<String> getApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> createApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> updateApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteApplicantSkillResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllApplicantSkillResult = new MutableLiveData<>();

    public ApplicantViewModel() {
        applicantRepository = new ApplicantRepository();
    }

    // Getter cho LiveData
    public LiveData<String> getUpdateApplicantResult() { return updateApplicantResult; }
    public LiveData<String> getGetApplicantResult() { return getApplicantResult; }
    public LiveData<String> getGetApplicantSkillResult() { return getApplicantSkillResult; }
    public LiveData<String> getCreateApplicantSkillResult() { return createApplicantSkillResult; }
    public LiveData<String> getUpdateApplicantSkillResult() { return updateApplicantSkillResult; }
    public LiveData<String> getDeleteApplicantSkillResult() { return deleteApplicantSkillResult; }

    // Get applicant
    public void getApplicant(String id) {
        Call<GetApplicantResponse> call = applicantRepository.getApplicant(id);
        call.enqueue(new Callback<GetApplicantResponse>() {
            @Override
            public void onResponse(Call<GetApplicantResponse> call, Response<GetApplicantResponse> response) {
                if (response.isSuccessful()) {
                    GetApplicantResponse getResponse = response.body();
                    getApplicantResult.setValue(getResponse.getMessage());
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

    //Get Skill
    public void getApplicantSkill(String applicantId) {
        Call<GetApplicantSkillResponse> call = applicantRepository.getApplicantSkill(applicantId);
        call.enqueue(new Callback<GetApplicantSkillResponse>() {
            @Override
            public void onResponse(Call<GetApplicantSkillResponse> call, Response<GetApplicantSkillResponse> response) {
                if (response.isSuccessful()) {
                    GetApplicantSkillResponse getResponse = response.body();
                    getApplicantSkillResult.setValue(getResponse.getMessage());
                } else {
                    try {
                        GetApplicantSkillResponse error = new Gson().fromJson(response.errorBody().string(), GetApplicantSkillResponse.class);
                        getApplicantSkillResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getApplicantSkillResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<GetApplicantSkillResponse> call, Throwable t) {
                getApplicantSkillResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create Skill
    public void createApplicantSkill(String skillId, String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp) {
        CreateApplicantSkillRequest request = new CreateApplicantSkillRequest(skillName, description, certificate, expertiseLevel, yearOfExp, monthOfExp);
        Call<CreateApplicantSkillResponse> call = applicantRepository.createApplicantSkill(skillId, request);
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
    public void updateApplicantSkill(String applicantId, String skillName, String description, String certificate, String expertiseLevel, Integer yearOfExp, Integer monthOfExp) {
        UpdateApplicantSkillRequest request = new UpdateApplicantSkillRequest(skillName, description, certificate, expertiseLevel, yearOfExp, monthOfExp);
        Call<UpdateApplicantSkillResponse> call = applicantRepository.updateApplicantSkill(applicantId, request);
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
    public void deleteAllApplicantSkill(String skillId) {
        Call<MessageResponse> call = applicantRepository.deleteAllApplicantSkill(skillId);
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
}
