package com.example.workleap.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailMyJobPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMyJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation, txtDescription, txtResponsibilities, txtPosition, txtWorkingAddress, txtEducationRequirement, txtSkillRequirement, txtApplyUntil;
    private Button btnApply;
    private ImageButton btnOption;
    private NavController nav;
    private Bundle bundle;
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

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
        return inflater.inflate(R.layout.fragment_details_jobpost, container, false);
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

        //Get current jobpost from jobpost fragment
        JobPost jobPost = (JobPost) getArguments().getSerializable("jobPost");
        txtJobName.setText(jobPost.getTitle());
        txtCompanyName.setText(jobPost.getCompany().getName());
        txtSalary.setText(jobPost.getSalaryStart() + " - " + jobPost.getSalaryEnd() + " " + jobPost.getCurrency());
        txtLocation.setText(jobPost.getLocation());
        txtDescription.setText(jobPost.getDescription());
        txtWorkingAddress.setText(jobPost.getWorkingAddress());
        txtResponsibilities.setText(jobPost.getResponsibility());
        txtPosition.setText(jobPost.getPosition());
        txtSkillRequirement.setText(jobPost.getSkillRequirement());
        txtEducationRequirement.setText(jobPost.getEducationRequirement());
        txtLocation.setText(jobPost.getLocation());
        txtApplyUntil.setText(jobPost.getApplyUntil());
        // TODO: Add listeners or bind ViewModel here

        btnOption.setOnClickListener(v -> {
            // TODO: Handle option button click
            PopupMenu popupMenu = new PopupMenu(v.getContext(), btnOption);
            popupMenu.inflate(R.menu.menu_details_myjobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jobPost", jobPost);
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
    }
}