package com.example.workleap.ui.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailMyJobPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMyJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation, txtDescription, txtResponsibilities, txtPosition, txtWorkingAddress, txtEducationRequirement, txtSkillRequirement, txtApplyUntil;
    private Button btnApply, btnCompany;
    private ImageButton btnOption, btnBack;
    private NavController nav;
    private Bundle bundle;
    private List<CV> cvList;

    private User user;
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

    private Company company;

    public DetailMyJobPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailMyJobPostFragment newInstance(String param1, String param2) {
        DetailMyJobPostFragment fragment = new DetailMyJobPostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_jobpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        //find component
        txtJobName = view.findViewById(R.id.txtJobName);
        txtCompanyName = view.findViewById(R.id.txtCompanyName);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtWorkingAddress = view.findViewById(R.id.txtWorkingAddress);
        txtResponsibilities = view.findViewById(R.id.txtResponsibilities);
        txtPosition = view.findViewById(R.id.txtPosition);
        txtSkillRequirement = view.findViewById(R.id.txtSkillRequirement);
        txtEducationRequirement = view.findViewById(R.id.txtEducationRequirement);
        txtApplyUntil = view.findViewById(R.id.txtApplyUntil);
        btnApply = view.findViewById(R.id.btnApply);
        btnOption = view.findViewById(R.id.btnOption);
        btnBack = view.findViewById(R.id.btnBack);
        btnCompany = view.findViewById(R.id.btnCompany);

        //Get current jobpost from jobpost fragment
        jobPostViewModel.getCurrentJobPost().observe(getViewLifecycleOwner(), currentJobPost -> {
            if (currentJobPost != null) {
                // Lay ra company cho trang detail company
                company = currentJobPost.getCompany();

                //Set thong tin
                txtJobName.setText(currentJobPost.getTitle());
                txtCompanyName.setText(currentJobPost.getCompany().getName());
                txtSalary.setText(currentJobPost.getSalaryStart() + " - " + currentJobPost.getSalaryEnd() + " " + currentJobPost.getCurrency());
                txtLocation.setText(currentJobPost.getLocation());
                txtDescription.setText(currentJobPost.getDescription());
                txtWorkingAddress.setText(currentJobPost.getWorkingAddress());
                txtResponsibilities.setText(currentJobPost.getResponsibility());
                txtPosition.setText(currentJobPost.getPosition());
                txtSkillRequirement.setText(currentJobPost.getSkillRequirement());
                txtEducationRequirement.setText(currentJobPost.getEducationRequirement());
                txtLocation.setText(currentJobPost.getLocation());
                txtApplyUntil.setText(currentJobPost.getApplyUntil());
            }
        });
        //JobPost jobPost = (JobPost) getArguments().getSerializable("jobPost");

        // TODO: Add listeners or bind ViewModel here

        user = (User) getArguments().getSerializable("user");
        if(user==null) Log.e("DetailMyJobPostFragment", "user null");

        CVViewModel cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);
        cvViewModel.getAllCvData().observe(getViewLifecycleOwner(), CVs -> {
            cvList = CVs;
        });
        cvViewModel.getAllCv(user.getApplicantId());

        jobPostViewModel.getApplyAJobResult().observe(getViewLifecycleOwner(), result ->{
            if(!isAdded() || getView()==null) return;

            if(result!=null)
                Log.e("DetailMyJobPostFragment", "getApplyAJobResult " + result);
            else
                Log.e("DetailMyJobPostFragment", "getApplyAJobResult result NULL" );
        });

        btnOption.setOnClickListener(v -> {
            // TODO: Handle option button click
            PopupMenu popupMenu = new PopupMenu(v.getContext(), btnOption);
            popupMenu.inflate(R.menu.menu_details_myjobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    //Chuyen sang fragment edit update
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    nav.navigate(R.id.updateJobPostFragment, bundle);
                    return true;
                } else if (item.getItemId() == R.id.menu_cancle) {
                    //Doi status
                    return true;
                } else
                    return false;
            });
            popupMenu.show();
        });

        btnBack.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        btnCompany.setOnClickListener(x ->
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("company", company);
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    nav.navigate(R.id.detailCompanyFragment, bundle);
                }
        );


        btnApply.setOnClickListener(v -> {
            if (cvList == null || cvList.isEmpty()) {
                Toast.makeText(getContext(), "Please upload a CV to apply", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("cv_list", new ArrayList<>(cvList));
            nav.navigate(R.id.chooseCVFragment, bundle);

        });
        // lang nghe ket qua tra ve cua choose cv
        getParentFragmentManager().setFragmentResultListener("choose_cv_result", this, (requestKey, bundle) -> {
            CV selectedCV = (CV) bundle.getSerializable("selected_cv");
            if (selectedCV != null) {
                //jobPostViewModel.applyAJob(jobPost.getId(), selectedCV.getApplicantId(), selectedCV.getId());
                Toast.makeText(getContext(), "Selected CV: " + selectedCV.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
