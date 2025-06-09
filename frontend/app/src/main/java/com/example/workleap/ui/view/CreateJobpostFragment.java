package com.example.workleap.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.utils.Utils;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateJobpostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJobpostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtDescription, edtLocation, edtPosition, edtWorkingAddress;
    private EditText edtEducation, edtSkillRequirement, edtResponsibility;
    private EditText edtSalaryStart, edtSalaryEnd, edtCurrency, edtStatus, edtApplyUntil;
    private Button btnSaveJob, btnCancel;

    public CreateJobpostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateJobpostFragment newInstance(String param1, String param2) {
        CreateJobpostFragment fragment = new CreateJobpostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_jobpost, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        // Ánh xạ các thành phần trong layout
        autoJobCategory = view.findViewById(R.id.autoJobCategory);
        autoJobType = view.findViewById(R.id.autoJobType);
        edtTitle = view.findViewById(R.id.edtTitle);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtLocation = view.findViewById(R.id.edtLocation);
        edtPosition = view.findViewById(R.id.edtPosition);
        edtWorkingAddress = view.findViewById(R.id.edtWorkingAddress);
        edtEducation = view.findViewById(R.id.edtEducation);
        edtSkillRequirement = view.findViewById(R.id.edtSkillRequirement);
        edtResponsibility = view.findViewById(R.id.edtResponsibility);
        edtSalaryStart = view.findViewById(R.id.edtSalaryStart);
        edtSalaryEnd = view.findViewById(R.id.edtSalaryEnd);
        edtCurrency = view.findViewById(R.id.edtCurrency);
        edtStatus = view.findViewById(R.id.edtStatus);
        edtApplyUntil = view.findViewById(R.id.edtApplyUntil);
        btnSaveJob = view.findViewById(R.id.btnSaveJob);
        btnCancel = view.findViewById(R.id.btnCancel);

        //Nhan ket qua
        jobPostViewModel.getCreateJobPostResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Create jobpost result", result);
            else
                Log.e("Create jobpost result", "Create jobpost result null");

            // Hien lai bottom navigation va quay ve
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        // TODO: Add listeners or bind ViewModel here
        btnSaveJob.setOnClickListener(v -> {
            Log.e("click", "click");
            Log.e("click", getArguments().getString("companyId"));
            Toast.makeText(this.getActivity(), "Company ID: " + getArguments().getString("companyId"), Toast.LENGTH_SHORT).show();
            BigDecimal a = BigDecimal.valueOf(1000);
            // Handle save logic here
            JobPost jobPost = new JobPost(
                    getArguments().getString("companyId"),
                    autoJobCategory.getText().toString(),
                    autoJobType.getText().toString(),
                    edtTitle.getText().toString(),
                    edtDescription.getText().toString(),
                    edtLocation.getText().toString(),
                    edtPosition.getText().toString(),
                    edtWorkingAddress.getText().toString(),
                    edtEducation.getText().toString(),
                    edtSkillRequirement.getText().toString(),
                    edtResponsibility.getText().toString(),
                    edtSalaryStart.getText().toString(),
                    edtSalaryEnd.getText().toString(),
                    edtCurrency.getText().toString(),
                    edtStatus.getText().toString(), //Enum
                    edtApplyUntil.getText().toString()
                    /*"aa2a80cb-e710-4df3-b9db-724919ee3393",
                    "5773bc80-417f-4356-b144-4749d8528fe5",
                    "24fc4e66-d391-4cc8-beca-41110bf612e1",
                    "Khoa tao android studio ne",
                    "Tham gia phát triển các ứng dụng web cho doanh nghiệp.",
                    "HCM",
                    "Lập trình viên",
                    "Tầng 3, tòa nhà ABC, Quận 1, TP.HCM",
                    "Đại học",
                    "Thành thạo Java, Spring Boot, Git",
                    "Viết code, sửa lỗi, làm việc nhóm",
                    "1500",
                    "2500",
                    "USD",
                    "OPENING",
                    "30-06-2024"*/
            );

            Log.d("new jobpost", new Gson().toJson(jobPost));
            jobPostViewModel.createJobPost(jobPost);

        });

        btnCancel.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
}