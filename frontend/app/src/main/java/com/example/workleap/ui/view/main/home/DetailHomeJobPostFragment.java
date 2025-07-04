package com.example.workleap.ui.view.main.home;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DetailHomeJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtDescription, txtResponsibilities, txtPosition, txtWorkingAddress, txtEducationRequirement, txtSkillRequirement, txtApplyUntil, txtCvName;
    private Button btnApply, btnChangeCv;

    private NavController nav;
    private List<CV> cvList;

    private JobPost currentJobPost;
    private User user;
    private Company company;

    private LinearLayout layoutSubmittedCV;

    //getWithDrawCvResult goi lai moi lan quay ve fragment nen can bien flag de ngan viec choose cv fragment bi goi nhieu lan
    boolean hasHandledWithdrawResult = false;
    private boolean shouldRefreshJobAppliedAfterApply = false;

    private boolean hasSubmitted=false;

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
        layoutSubmittedCV = view.findViewById(R.id.layoutSubmittedCV);
        btnChangeCv = view.findViewById(R.id.btnChangeCV);


        // Lấy jobPost và user từ arguments
        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");
        user = (User) getArguments().getSerializable("user");
        //tat nut apply doi voi company
        if(!currentJobPost.getStatus().equals("OPENING"))
            btnApply.setVisibility(GONE);
        if(user.getApplicantId()==null)
            btnApply.setVisibility(GONE);

        if (currentJobPost != null) {
            company = currentJobPost.getCompany();
            txtDescription.setText(currentJobPost.getDescription());
            txtWorkingAddress.setText(currentJobPost.getWorkingAddress());
            txtResponsibilities.setText(currentJobPost.getResponsibility());
            txtPosition.setText(currentJobPost.getPosition());
            txtSkillRequirement.setText(currentJobPost.getSkillRequirement());
            txtEducationRequirement.setText(currentJobPost.getEducationRequirement());
            txtApplyUntil.setText(Utils.formatDate(currentJobPost.getApplyUntil()));
        }

        if (user == null) Log.e("DetailMyJobPostFragment", "user null");

        CVViewModel cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);
        cvViewModel.getAllCvData().observe(getViewLifecycleOwner(), CVs -> cvList = CVs);
        cvViewModel.getAllCv(user.getApplicantId());

        jobPostViewModel.getApplyAJobResult().observe(getViewLifecycleOwner(), result -> {
            if (!isAdded() || getView() == null) return;
            if(result==null)
            {
                Log.e("DetailHomeJobPost", "getApplyAJobResult NULL");
                return;
            }
            if(result.equalsIgnoreCase("Success"))
            {
                Log.e("DetailMyJobPostFragment", "getApplyAJobResult: " + result);
                //bi goi lai nhieu lan
                //Toast.makeText(getContext(), "Your CV has been submitted successfully.", Toast.LENGTH_SHORT).show();
                shouldRefreshJobAppliedAfterApply = true;
                //reload button change
                jobPostViewModel.getJobApplied(user.getApplicantId());
            }
        });
        jobPostViewModel.getWithDrawCvResult().observe(getViewLifecycleOwner(), result->{
            if (!isAdded() || getView() == null || !hasHandledWithdrawResult) return;
            Log.e("ooooo", "day day");
            hasHandledWithdrawResult = false;

            if(result.equalsIgnoreCase("Success"))
            {
                //van reload trong truong hop nguoi dung khong chon cv moi sau khi xoa
                //jobPostViewModel.getJobApplied(user.getApplicantId());
                //You have successfully changed your CV.
                //CV removed successfully.
                Toast.makeText(getContext(), "CV removed successfully.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Please select your new replacement CV.", Toast.LENGTH_SHORT).show();

                if (cvList == null || cvList.isEmpty()) {
                    Toast.makeText(getContext(), "Please upload a CV to apply", Toast.LENGTH_SHORT).show();
                    return;
                }

                jobPostViewModel.resetGetJobAppliedResultData();
                jobPostViewModel.resetApplyAJobResult();

                Bundle bundle = new Bundle();
                bundle.putSerializable("cv_list", new ArrayList<>(cvList));
                nav.navigate(R.id.chooseCVFragment, bundle);
            }
            else {
                Toast.makeText(getContext(), "Failed to change your CV. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        //logic da nop cv
        jobPostViewModel.getJobApplied(user.getApplicantId());
        jobPostViewModel.getJobAppliedResult().observe(getViewLifecycleOwner(), result ->{
            if (!isAdded() || getView() == null) return;
            Log.e("DetailMyJobPostFragment", "getJobAppliedResult: " + result);
        });
        jobPostViewModel.getJobAppliedData().observe(getViewLifecycleOwner(), jobApplieds->
        {
            hasSubmitted=false;
            if (jobApplieds == null) {
                Log.e("DetailHomeJPFragment", "JobApplieds NULL");
                return;
            }

            //kiem tra co du lieu moi tu apply a job khong, nhung van cho phep getjobapplied goi lan dau vao fragment
            if (shouldRefreshJobAppliedAfterApply) {
                shouldRefreshJobAppliedAfterApply = false; // tat doi den khi co apply a job moi
                jobPostViewModel.getJobApplied(user.getApplicantId()); // goi lai getJobAppliedData voi jobapplieds moi
                return; //ket thuc neu co ket qua moi tu apply a job
            }

            for (JobApplied applied : jobApplieds) {
                if (applied.getJobpostId().equals(currentJobPost.getId())) {
                    hasSubmitted = true;
                    btnApply.setVisibility(View.GONE);

                    layoutSubmittedCV.setVisibility(View.VISIBLE);

                    txtCvName = view.findViewById(R.id.txtCvName);
                    txtCvName.setText(applied.getCV().getTitle());

                    btnChangeCv.setOnClickListener(v ->{
                        hasHandledWithdrawResult=true;
                        jobPostViewModel.withDrawCv(user.getApplicantId(), currentJobPost.getId());
                    });

                    break;
                }
            }
            if(!hasSubmitted && currentJobPost.getStatus().equals("OPENING"))
            {
                btnApply.setVisibility(View.VISIBLE);
                btnApply.setOnClickListener(v -> {
                    Log.e("DetailHomeJPHome", "btnapply");
                    if (cvList == null || cvList.isEmpty() || shouldRefreshJobAppliedAfterApply) {
                        Toast.makeText(getContext(), "Please upload a CV to apply", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //reset tranh goi lai lan hai khi load fragment luc quay ve
                    jobPostViewModel.resetGetJobAppliedResultData();
                    jobPostViewModel.resetApplyAJobResult();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cv_list", new ArrayList<>(cvList));
                    nav.navigate(R.id.chooseCVFragment, bundle);
                });
            }
        });

        NavController navController = NavHostFragment.findNavController(this);
        SavedStateHandle savedStateHandle = navController.getCurrentBackStackEntry().getSavedStateHandle();

        savedStateHandle.getLiveData("selected_cv").observe(getViewLifecycleOwner(), selectedCV -> {
            if (selectedCV != null) {
                CV cv = (CV) selectedCV;
                jobPostViewModel.applyAJob(currentJobPost.getId(), cv.getApplicantId(), cv.getId());
                Toast.makeText(getContext(), "Selected CV: " + cv.getTitle(), Toast.LENGTH_SHORT).show();
                //tat btnApply khi quay ve
                btnApply.setVisibility(View.GONE);
                // xoa sau khi dung
                savedStateHandle.remove("selected_cv");
            }
        });
    }
}

