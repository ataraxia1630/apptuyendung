package com.example.workleap.ui.view.main.jobpost_post;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailMyJobPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMyJobPostFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtDescription, txtResponsibilities, txtPosition, txtWorkingAddress, txtEducationRequirement, txtSkillRequirement, txtApplyUntil;
    private NavController nav;

    private JobPost selectedJobPost ;
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
        txtDescription = view.findViewById(R.id.txtDescription);
        txtWorkingAddress = view.findViewById(R.id.txtWorkingAddress);
        txtResponsibilities = view.findViewById(R.id.txtResponsibilities);
        txtPosition = view.findViewById(R.id.txtPosition);
        txtSkillRequirement = view.findViewById(R.id.txtSkillRequirement);
        txtEducationRequirement = view.findViewById(R.id.txtEducationRequirement);
        txtApplyUntil = view.findViewById(R.id.txtApplyUntil);

        //Get current jobpost from jobpost fragment
        jobPostViewModel.getCurrentJobPost().observe(getViewLifecycleOwner(), currentJobPost -> {
            if (currentJobPost != null) {
                // Lay ra company cho trang detail company
                company = currentJobPost.getCompany();
                this.selectedJobPost  = currentJobPost;

                //Set thong tin
                txtDescription.setText(currentJobPost.getDescription());
                txtWorkingAddress.setText(currentJobPost.getWorkingAddress());
                txtResponsibilities.setText(currentJobPost.getResponsibility());
                txtPosition.setText(currentJobPost.getPosition());
                txtSkillRequirement.setText(currentJobPost.getSkillRequirement());
                txtEducationRequirement.setText(currentJobPost.getEducationRequirement());
                txtApplyUntil.setText(Utils.formatDate(currentJobPost.getApplyUntil()));
            }
        });
    }
}
