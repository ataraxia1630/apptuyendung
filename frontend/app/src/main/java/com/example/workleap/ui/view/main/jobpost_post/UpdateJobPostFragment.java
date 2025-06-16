package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateJobPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtDescription, edtLocation, edtPosition, edtWorkingAddress;
    private EditText edtEducation, edtSkillRequirement, edtResponsibility;
    private EditText edtSalaryStart, edtSalaryEnd, edtCurrency, edtApplyUntil;
    private Button btnSaveJob, btnCancel;

    private ArrayList<JobCategory> jobCategories = new ArrayList<>();
    private ArrayList<JobType> jobTypes = new ArrayList<>();
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công
    private JobPost curJobPost;


    public UpdateJobPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpdateJobPostFragment newInstance(String param1, String param2) {
        UpdateJobPostFragment fragment = new UpdateJobPostFragment();
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

        //Tim cac thanh phan component
        edtTitle = view.findViewById(R.id.edtTitle);
        autoJobCategory = view.findViewById(R.id.autoJobCategory);
        autoJobType = view.findViewById(R.id.autoJobType);
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
        edtApplyUntil = view.findViewById(R.id.edtApplyUntil);
        btnSaveJob = view.findViewById(R.id.btnSaveJob);
        btnCancel = view.findViewById(R.id.btnCancel);

        jobPostViewModel.getCurrentJobPost().observe(getViewLifecycleOwner(), currentJobPost -> {
            if (currentJobPost != null) {
                curJobPost = currentJobPost;
                //Gan gia tri hien tai cho cac truong
                edtTitle.setText(currentJobPost.getTitle());
                edtDescription.setText(currentJobPost.getDescription());
                edtLocation.setText(currentJobPost.getLocation());
                edtPosition.setText(currentJobPost.getPosition());
                edtWorkingAddress.setText(currentJobPost.getWorkingAddress());
                edtEducation.setText(currentJobPost.getEducationRequirement());
                edtSkillRequirement.setText(currentJobPost.getSkillRequirement());
                edtResponsibility.setText(currentJobPost.getResponsibility());
                edtSalaryStart.setText(currentJobPost.getSalaryStart());
                edtSalaryEnd.setText(currentJobPost.getSalaryEnd());
                edtCurrency.setText(currentJobPost.getCurrency());
                edtApplyUntil.setText(Utils.formatDate(currentJobPost.getApplyUntil()));
            }
        });

        //Lay danh sach jobcategory
        ArrayList<String> jobCategoriesName = new ArrayList<>();
        jobPostViewModel.getAllJobCategoryResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Load jobcategory result", result);
            else
                Log.e("Load jobcategory result", "Jobcategory result null");
        });
        jobPostViewModel.getAllJobCategoryData().observe(getViewLifecycleOwner(), data ->
        {
            if(data != null)
            {
                jobCategories.clear();
                jobCategoriesName.clear();
                jobCategories.addAll(data);
                for(JobCategory jobCategory : jobCategories)
                    jobCategoriesName.add(jobCategory.getName());

                //Hien thi category name hien tai tu id trong jobpost
                String categoryName = null;
                Log.d("current cate id", curJobPost.getJobCategoryId());

                for (JobCategory jobCategory : jobCategories) {
                    Log.d("cate id", jobCategory.getId());
                    if (jobCategory.getId().equals(curJobPost.getJobCategoryId())) {
                        categoryName = jobCategory.getName(); // Gán vào categorySelected
                        break;
                    }
                }
                autoJobCategory.setText(categoryName);
            }
            else
                Log.e("Load jobcategory data", "Jobcategory data null");
        });
        jobPostViewModel.getAllJobCategory();

        //Xu li Auto complete textview jobcategory
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, jobCategoriesName);
        autoJobCategory.setAdapter(adapterCategory);
        // Hiển thị danh sách khi nhận focus hoac click, kiem tra ton tai
        autoJobCategory.setThreshold(0); // Hiển thị danh sách ngay khi click, không cần nhập ký tự
        autoJobCategory.setOnClickListener(v -> autoJobCategory.showDropDown()); // Hiển thị danh sách khi click
        autoJobCategory.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                autoJobCategory.showDropDown();
            else if (!hasFocus && !jobCategoriesName.contains(autoJobCategory.getText().toString())) {
                Toast.makeText(this.getActivity(), "Please select an available category", Toast.LENGTH_SHORT).show();
                autoJobCategory.setText(""); // Xóa nếu nhập không hợp lệ
            }
        });



        //Lay danh sach jobtype
        ArrayList<String> jobTypesName = new ArrayList<>();
        jobPostViewModel.getAllJobTypeResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Load jobtype result", result);
            else
                Log.e("Load jobtype result", "JobType result null");
        });
        jobPostViewModel.getAllJobTypeData().observe(getViewLifecycleOwner(), data ->
        {
            if(data != null)
            {
                jobTypes.clear();
                jobTypesName.clear();
                jobTypes.addAll(data);
                for(JobType jobType : jobTypes)
                    jobTypesName.add(jobType.getName());

                //Hien thi type name hien tai tu id trong jobpost
                String typeName = null;
                for (JobType jobType : jobTypes) {
                    if (jobType.getId().equals(curJobPost.getJobTypeId())) {
                        typeName = jobType.getName(); // Gán vào typeSelected
                        break;
                    }
                }
                autoJobType.setText(typeName);
            }
            else
                Log.e("Load jobtype data", "Jobtype data null");
        });
        jobPostViewModel.getAllJobType();

        //Xu li Auto complete textview jobtype
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, jobTypesName);
        autoJobType.setAdapter(adapterType);
        // Hiển thị danh sách khi nhận focus hoac click, kiem tra ton tai
        autoJobType.setThreshold(0); // Hiển thị danh sách ngay khi click, không cần nhập ký tự
        autoJobType.setOnClickListener(v -> autoJobType.showDropDown()); // Hiển thị danh sách khi click
        autoJobType.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                autoJobType.showDropDown();
            else if (!hasFocus && !jobTypesName.contains(autoJobType.getText().toString())) {
                Toast.makeText(this.getActivity(), "Please select an available Type", Toast.LENGTH_SHORT).show();
                autoJobType.setText(""); // Xóa nếu nhập không hợp lệ
            }
        });

        //Nhan ket qua update jobpost
        jobPostViewModel.getUpdateJobPostResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Update jobpost result:", result);
            else
                Log.e("Update jobpost result:", "Update jobpost result null");

            if(isJobPostSubmitted) {
                // Hien lai bottom navigation va quay ve
                ((NavigationActivity) getActivity()).showBottomNav(true);
                NavHostFragment.findNavController(this).navigateUp();
            }
        });

        // TODO: Add listeners or bind ViewModel here
        btnSaveJob.setOnClickListener(v -> {
            // Tìm JobCategory tương ứng
            String categoryId = null;
            for (JobCategory jobCategory : jobCategories) {
                if (jobCategory.getName().equals(autoJobCategory.getText().toString())) {
                    categoryId = jobCategory.getId(); // Gán vào categorySelected
                    break;
                }
            }
            // Tìm JobType tương ứng
            String typeId = null;
            for (JobType jobType : jobTypes) {
                if (jobType.getName().equals(autoJobType.getText().toString())) {
                    typeId = jobType.getId(); // Gán vào typeSelected
                    break;
                }
            }

            // Handle save logic here
            JobPost jobPost = new JobPost(
                    //getArguments().getString("companyId"),
                    curJobPost.getCompanyId(),
                    categoryId,
                    typeId,
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
                    "OPENING", //Defalue value is OPENING with new jobpost
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

            //Do update khong truyen company nen can gan lai
            jobPost.setCompany(curJobPost.getCompany());

            Log.d("updated jobpost", new Gson().toJson(jobPost));

            jobPostViewModel.updateJobPost(curJobPost.getId(), jobPost);
            jobPostViewModel.setCurrentJobPost(jobPost);

            isJobPostSubmitted = true;
        });

        btnCancel.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
}