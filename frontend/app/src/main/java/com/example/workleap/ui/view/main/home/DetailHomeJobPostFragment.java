package com.example.workleap.ui.view.main.home;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailHomeJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtDescription, txtResponsibilities, txtPosition, txtWorkingAddress, txtEducationRequirement, txtSkillRequirement, txtApplyUntil;
    private Button btnApply;
    private NavController nav;
    private List<CV> cvList;

    private JobPost currentJobPost;
    private User user;
    private boolean isJobPostSubmitted = false;
    private Company company;

    public DetailHomeJobPostFragment() {}

    public static DetailHomeJobPostFragment newInstance(String param1, String param2) {
        DetailHomeJobPostFragment fragment = new DetailHomeJobPostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);
        return inflater.inflate(R.layout.fragment_detail_home_jobpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        // Find views
        txtDescription = view.findViewById(R.id.txtDescription);
        txtWorkingAddress = view.findViewById(R.id.txtWorkingAddress);
        txtResponsibilities = view.findViewById(R.id.txtResponsibilities);
        txtPosition = view.findViewById(R.id.txtPosition);
        txtSkillRequirement = view.findViewById(R.id.txtSkillRequirement);
        txtEducationRequirement = view.findViewById(R.id.txtEducationRequirement);
        txtApplyUntil = view.findViewById(R.id.txtApplyUntil);
        btnApply = view.findViewById(R.id.btnApply);

        // Lấy jobPost và user từ arguments
        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");
        user = (User) getArguments().getSerializable("user");
        //tat nut apply doi voi company
        if(user.getApplicantId()==null) btnApply.setVisibility(GONE);

        if (currentJobPost != null) {
            company = currentJobPost.getCompany();
            txtDescription.setText(currentJobPost.getDescription());
            txtWorkingAddress.setText(currentJobPost.getWorkingAddress());
            txtResponsibilities.setText(currentJobPost.getResponsibility());
            txtPosition.setText(currentJobPost.getPosition());
            txtSkillRequirement.setText(currentJobPost.getSkillRequirement());
            txtEducationRequirement.setText(currentJobPost.getEducationRequirement());
            txtApplyUntil.setText(currentJobPost.getApplyUntil());
        }

        if (user == null) Log.e("DetailMyJobPostFragment", "user null");

        CVViewModel cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);
        cvViewModel.getAllCvData().observe(getViewLifecycleOwner(), CVs -> cvList = CVs);
        cvViewModel.getAllCv(user.getApplicantId());

        jobPostViewModel.getApplyAJobResult().observe(getViewLifecycleOwner(), result -> {
            if (!isAdded() || getView() == null) return;
            Log.e("DetailMyJobPostFragment", "getApplyAJobResult: " + result);
        });

        btnApply.setOnClickListener(v -> {
            if (cvList == null || cvList.isEmpty()) {
                Toast.makeText(getContext(), "Please upload a CV to apply", Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("cv_list", new ArrayList<>(cvList));
            nav.navigate(R.id.chooseCVFragment, bundle);
        });

        getParentFragmentManager().setFragmentResultListener("choose_cv_result", this, (requestKey, bundle) -> {
            CV selectedCV = (CV) bundle.getSerializable("selected_cv");
            if (selectedCV != null) {
                jobPostViewModel.applyAJob(currentJobPost.getId(), selectedCV.getApplicantId(), selectedCV.getId());
                Toast.makeText(getContext(), "Selected CV: " + selectedCV.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

