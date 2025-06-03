package com.example.workleap.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateJobpostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJobpostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtDescription, edtPosition, edtWorkingAddress;
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

        // TODO: Add listeners or bind ViewModel here
        btnSaveJob.setOnClickListener(v -> {
            // Handle save logic here
        });

        btnCancel.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
}