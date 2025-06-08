package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.CVRequest;
import com.example.workleap.data.model.request.UpdateCompanyRequest;
import com.example.workleap.data.model.response.CVResponse;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.response.ListCVResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.UpdateCompanyResponse;
import com.example.workleap.data.repository.CvRepository;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CVViewModel extends ViewModel {
    private CvRepository cvRepository;
    private MutableLiveData<List<CV>> getAllCvData = new MutableLiveData<>();
    private MutableLiveData<CV> getCvByIdData = new MutableLiveData<>();
    private MutableLiveData<String> getAllCvResult = new MutableLiveData<>();
    private MutableLiveData<String> createCvResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllCvResult = new MutableLiveData<>();
    private MutableLiveData<String> deleteCvByIdResult = new MutableLiveData<>();
    private MutableLiveData<String> getCvByIdResult = new MutableLiveData<>();
    private MutableLiveData<String> updateCvByIdResult = new MutableLiveData<>();

    public CVViewModel(){}
    public void InitiateRepository(Context context) {
        cvRepository = new CvRepository(context);
    }

    // Getter cho LiveData
    public LiveData<List<CV>> getAllCvData() { return getAllCvData; }
    public LiveData<CV> getCvByIdData() { return getCvByIdData; }
    public LiveData<String> getAllCvResult() { return getAllCvResult; }
    public LiveData<String> createCvResult() { return createCvResult; }
    public LiveData<String> deleteAllCvResult() { return deleteAllCvResult; }
    public LiveData<String> deleteCvByIdResult() { return deleteCvByIdResult; }
    public LiveData<String> getCvByIdResult() { return getCvByIdResult; }
    public LiveData<String> updateCvByIdResult() { return updateCvByIdResult; }

    // Get all cv
    public void getAllCv(String applicantId) {
        Call<ListCVResponse> call = cvRepository.getAllCv(applicantId);
        call.enqueue(new Callback<ListCVResponse>() {
            @Override
            public void onResponse(Call<ListCVResponse> call, Response<ListCVResponse> response) {
                if (response.isSuccessful()) {
                    ListCVResponse listCVResponse = response.body();
                    getAllCvData.setValue(listCVResponse.getAllCvs());
                    getAllCvResult.setValue(listCVResponse.getMessage());
                    getAllCvResult.setValue("Lấy danh sách CV thành công");
                } else {
                    try {
                        ListCVResponse error = new Gson().fromJson(response.errorBody().string(), ListCVResponse.class);
                        getAllCvResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getAllCvResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListCVResponse> call, Throwable t) {
                getAllCvResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create cv
    public void createCv(String applicantId, File file, String title) {
        //CVRequest request = new CVRequest(file, title);

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);

        Call<MessageResponse> call = cvRepository.createCv(applicantId, filePart, titlePart);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call,
                                   Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    createCvResult.setValue(messageResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        createCvResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        createCvResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                createCvResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete all cv
    public void deleteAllCv(String applicantId) {
        Call<MessageResponse> call = cvRepository.deleteAllCv(applicantId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call,
                                   Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    deleteAllCvResult.setValue(messageResponse.getMessage());
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteAllCvResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteAllCvResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteAllCvResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //get cv by id
    public void getCvById(String id) {
        Call<CVResponse> call = cvRepository.getCvById(id);
        call.enqueue(new Callback<CVResponse>() {
            @Override
            public void onResponse(Call<CVResponse> call, Response<CVResponse> response) {
                if (response.isSuccessful()) {
                    CVResponse cvResponse = response.body();
                    getCvByIdData.setValue(cvResponse.getCv());
                    getCvByIdResult.setValue(cvResponse.getMessage());
                } else {
                    try {
                        CVResponse error = new Gson().fromJson(response.errorBody().string(), CVResponse.class);
                        getCvByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getCvByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<CVResponse> call, Throwable t) {
                getCvByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });

    }

    //update cv by id
    public void updateCvById(String id) {
        Call<MessageResponse> call = cvRepository.updateCvById(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse cvResponse = response.body();
                    updateCvByIdResult.setValue(cvResponse.getMessage());
                } else {
                    try {
                        CVResponse error = new Gson().fromJson(response.errorBody().string(), CVResponse.class);
                        updateCvByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateCvByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                updateCvByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });

    }

    //delete by id
    public void deleteCvById(String id) {
        Call<MessageResponse> call = cvRepository.deleteCvById(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse cvResponse = response.body();
                    deleteCvByIdResult.setValue(cvResponse.getMessage());
                } else {
                    try {
                        CVResponse error = new Gson().fromJson(response.errorBody().string(), CVResponse.class);
                        deleteCvByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteCvByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteCvByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
