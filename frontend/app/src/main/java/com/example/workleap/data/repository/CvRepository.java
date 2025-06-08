package com.example.workleap.data.repository;

import android.content.Context;
import android.view.PixelCopy;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.request.CVRequest;
import com.example.workleap.data.model.request.CreateApplicantEducationRequest;
import com.example.workleap.data.model.request.CreateApplicantExperienceRequest;
import com.example.workleap.data.model.request.CreateApplicantSkillRequest;
import com.example.workleap.data.model.request.UpdateApplicantEducationRequest;
import com.example.workleap.data.model.request.UpdateApplicantExperienceRequest;
import com.example.workleap.data.model.request.UpdateApplicantRequest;
import com.example.workleap.data.model.request.UpdateApplicantSkillRequest;
import com.example.workleap.data.model.response.CVResponse;
import com.example.workleap.data.model.response.CreateApplicantEducationResponse;
import com.example.workleap.data.model.response.CreateApplicantExperienceResponse;
import com.example.workleap.data.model.response.CreateApplicantSkillResponse;
import com.example.workleap.data.model.response.CreateInterestedFieldResponse;
import com.example.workleap.data.model.response.GetApplicantResponse;
import com.example.workleap.data.model.response.ListCVResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.UpdateApplicantEducationResponse;
import com.example.workleap.data.model.response.UpdateApplicantExperienceResponse;
import com.example.workleap.data.model.response.UpdateApplicantResponse;
import com.example.workleap.data.model.response.UpdateApplicantSkillResponse;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class CvRepository {
    private ApiService apiService;

    private PreferencesManager preferencesManager;

    public CvRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    public Call<MessageResponse> createCv(String applicantId, MultipartBody.Part file, RequestBody title) {
        return apiService.createCv(applicantId, file, title);
    }

    public Call<ListCVResponse> getAllCv(String applicantId) {
        return apiService.getAllCv(applicantId);
    }

    public Call<MessageResponse> deleteAllCv(String applicantId) {
        return apiService.deleteAllCv(applicantId);
    }

    public Call<CVResponse> getCvById(String id) {
        return apiService.getCvById(id);
    }

    public Call<MessageResponse> updateCvById(String id) {
        return apiService.updateCvById(id);
    }

    public Call<MessageResponse> deleteCvById(String id) {
        return apiService.deleteCvById(id);
    }
}
