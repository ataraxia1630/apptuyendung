package com.example.workleap.ui.view.main.jobpost_post;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
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
import com.example.workleap.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

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
    private EditText edtSalaryStart, edtSalaryEnd, edtApplyUntil;
    private AutoCompleteTextView edtCurrency;
    private Button btnSaveJob, btnCancel;

    private ArrayList<JobCategory> jobCategories = new ArrayList<>();
    private ArrayList<JobType> jobTypes = new ArrayList<>();
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

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

        // Áp dụng DatePicker cho edtApplyUntil
        final Calendar calendar = Calendar.getInstance();

        // Ngăn bàn phím xuất hiện
        edtApplyUntil.setFocusable(false);
        edtApplyUntil.setKeyListener(null);
        edtApplyUntil.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        // Format: dd-MM-yyyy
                        String formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                        edtApplyUntil.setText(formattedDate);
                    },
                    year, month, day
            );

            // Không cho chọn ngày trong quá khứ
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        //Currency
        String[] currencies = {"USD", "VND", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, currencies);
        edtCurrency.setAdapter(adapter);
        edtCurrency.setOnClickListener(v -> edtCurrency.showDropDown());

        //Lay danh sach jobcategory
        ArrayList<String> jobCategoriesName = new ArrayList<>();

        //Xu li Auto complete textview jobcategory
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, jobCategoriesName);
        autoJobCategory.setAdapter(adapterCategory);

        jobPostViewModel.getAllJobCategoryResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Load jobcategory result", result);
            else
                Log.e("Load jobcategory result", "Create jobcategory result null");
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

                adapterCategory.notifyDataSetChanged();
            }
            else
                Log.e("Load jobcategory data", "Jobcategory data null");
        });
        jobPostViewModel.getAllJobCategory();

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

        //Xu li Auto complete textview jobtype
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, jobTypesName);
        autoJobType.setAdapter(adapterType);

        jobPostViewModel.getAllJobTypeResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Load jobtype result", result);
            else
                Log.e("Load jobtype result", "Create jobType result null");
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

                adapterType.notifyDataSetChanged();
            }
            else
                Log.e("Load jobtype data", "Jobtype data null");
        });
        jobPostViewModel.getAllJobType();

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



        //Nhan ket qua creat jobpost
        jobPostViewModel.getCreateJobPostResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Create jobpost result", result);
            else
                Log.e("Create jobpost result", "Create jobpost result null");

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

            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            String location = edtLocation.getText().toString().trim();
            String position = edtPosition.getText().toString().trim();
            String workingAddress = edtWorkingAddress.getText().toString().trim();
            String education = edtEducation.getText().toString().trim();
            String skillRequirement = edtSkillRequirement.getText().toString().trim();
            String responsibility = edtResponsibility.getText().toString().trim();
            String salaryStart = edtSalaryStart.getText().toString().trim();
            String salaryEnd = edtSalaryEnd.getText().toString().trim();
            String currency = edtCurrency.getText().toString().trim();
            String applyUntil = edtApplyUntil.getText().toString().trim();

            //Validation
            if (typeId == null || typeId.isEmpty()) {
                ToastUtil.showToast(getContext(), "Job Type ID cannot be empty", ToastUtil.TYPE_WARNING);
                return;
            }

            if (title.isEmpty()) {
                ToastUtil.showToast(getContext(), "Title cannot be empty", ToastUtil.TYPE_WARNING);
                return;
            }

            if (title.length() > 255) {
                ToastUtil.showToast(getContext(), "Title must be at most 255 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (description.length() > 1000) {
                ToastUtil.showToast(getContext(), "Description must be at most 1000 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (location.length() > 255) {
                ToastUtil.showToast(getContext(), "Location must be at most 255 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (position.length() > 255) {
                ToastUtil.showToast(getContext(), "Position must be at most 255 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (education.length() > 1000) {
                ToastUtil.showToast(getContext(), "Education requirement must be at most 1000 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (skillRequirement.length() > 1000) {
                ToastUtil.showToast(getContext(), "Skill requirement must be at most 1000 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (responsibility.length() > 2000) {
                ToastUtil.showToast(getContext(), "Responsibility must be at most 2000 characters", ToastUtil.TYPE_WARNING);
                return;
            }

            if (!salaryStart.isEmpty()) {
                try {
                    Double.parseDouble(salaryStart);
                } catch (NumberFormatException e) {
                    ToastUtil.showToast(getContext(), "Salary start must be a number", ToastUtil.TYPE_WARNING);
                    return;
                }
            }

            if (!salaryEnd.isEmpty()) {
                try {
                    Double.parseDouble(salaryEnd);
                } catch (NumberFormatException e) {
                    ToastUtil.showToast(getContext(), "Salary end must be a number", ToastUtil.TYPE_WARNING);
                    return;
                }
            }

            if (!currency.isEmpty()) {
                if (!currency.equals("USD") && !currency.equals("VND") && !currency.equals("EUR")) {
                    ToastUtil.showToast(getContext(), "Currency must be USD, VND or EUR", ToastUtil.TYPE_WARNING);
                    return;
                }
            }

            if (applyUntil.isEmpty()) {
                ToastUtil.showToast(getContext(), "Apply until cannot be empty", ToastUtil.TYPE_WARNING);
                return;
            }

            // Kiểm tra định dạng dd-MM-yyyy
            if (!applyUntil.matches("^([0-2][0-9]|(3)[0-1])\\-(0[1-9]|1[0-2])\\-\\d{4}$")) {
                ToastUtil.showToast(getContext(), "Apply until must be in format dd-MM-yyyy", ToastUtil.TYPE_WARNING);
                return;
            }

            // Handle save logic here
            JobPost jobPost = new JobPost(
                    getArguments().getString("companyId"),
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

            Log.d("new jobpost", new Gson().toJson(jobPost));
            jobPostViewModel.createJobPost(jobPost);

            isJobPostSubmitted = true;
        });

        btnCancel.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
}