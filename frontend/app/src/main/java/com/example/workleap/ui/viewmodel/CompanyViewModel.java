package com.example.workleap.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.response.GetCompanyResponse;
import com.example.workleap.data.model.request.UpdateCompanyRequest;
import com.example.workleap.data.model.response.UpdateCompanyResponse;
import com.example.workleap.data.repository.CompanyRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyViewModel extends ViewModel {
    private CompanyRepository companyRepository;
    private MutableLiveData<String> updateCompanyResult = new MutableLiveData<>();
    private MutableLiveData<String> getCompanyResult = new MutableLiveData<>();
    private MutableLiveData<Company> getCompanyData = new MutableLiveData<>();

    public CompanyViewModel(){}
    public void InitiateRepository(Context context) {
        companyRepository = new CompanyRepository(context);
    }

    // Getter cho LiveData
    public LiveData<String> getUpdateCompanyResult() { return updateCompanyResult; }
    public LiveData<String> getGetCompanyResult() { return getCompanyResult; }
    public LiveData<Company> getGetCompanyData() { return getCompanyData; }

    // Get company
    public void getCompany(String id) {
        Call<GetCompanyResponse> call = companyRepository.getCompany(id);
        call.enqueue(new Callback<GetCompanyResponse>() {
            @Override
            public void onResponse(Call<GetCompanyResponse> call, Response<GetCompanyResponse> response) {
                if (response.isSuccessful()) {
                    GetCompanyResponse getResponse = response.body();
                    getCompanyResult.setValue(getResponse.getMessage());
                    getCompanyData.setValue(getResponse.getCompany());
                } else {
                    try {
                        GetCompanyResponse error = new Gson().fromJson(response.errorBody().string(), GetCompanyResponse.class);
                        getCompanyResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getCompanyResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCompanyResponse> call, Throwable t) {
                getCompanyResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Update company
    public void updateCompany(String id, String name, String description, int establishedYear, String taxcode) {
        UpdateCompanyRequest request = new UpdateCompanyRequest(name, description, establishedYear, taxcode);
        Call<UpdateCompanyResponse> call = companyRepository.updateCompany(id, request);
        call.enqueue(new Callback<UpdateCompanyResponse>() {
            @Override
            public void onResponse(Call<UpdateCompanyResponse> call, Response<UpdateCompanyResponse> response) {
                if (response.isSuccessful()) {
                    UpdateCompanyResponse updateResponse = response.body();
                    updateCompanyResult.setValue(updateResponse.getMessage());
                } else {
                    try {
                        UpdateCompanyResponse error = new Gson().fromJson(response.errorBody().string(), UpdateCompanyResponse.class);
                        updateCompanyResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updateCompanyResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateCompanyResponse> call, Throwable t) {
                updateCompanyResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
