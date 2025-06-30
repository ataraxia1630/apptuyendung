package com.example.workleap.ui.view.main.cv_appliedjob;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.jobpost_post.JobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.MyJobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.PostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppliedJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppliedJobFragment extends Fragment {

    private RecyclerView recyclerViewAppliedJob;
    private JobPostAdapter adapterJobPost;
    private List<JobPost> allJobs = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;

    private ImageButton btnRearrange;
    private NavController nav;
    private User user; //myUser

    public AppliedJobFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AppliedJobFragment newInstance(String param1, String param2) {
        AppliedJobFragment fragment = new AppliedJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applied_job, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //JOBPOST LIST
        recyclerViewAppliedJob = view.findViewById(R.id.recyclerAppliedJob); // ID trong layout
        //btnRearrange = view.findViewById(R.id.btnRearrange);
        jobPostViewModel  = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);

        //lay user cho detail jobpost applied cv
        user = (User) getArguments().getSerializable("user");

        jobPostViewModel.getJobApplied(user.getApplicantId());
        jobPostViewModel.getJobAppliedResult().observe(getViewLifecycleOwner(), result ->
        {
            Log.e("AppliedJobFragment", "getJobAppliedResult: " + String.valueOf(result) + "");
        });
        jobPostViewModel.getJobAppliedData().observe(getViewLifecycleOwner(), jobApplieds ->
        {
            allJobs.clear();
            for(JobApplied jobApplied : jobApplieds)
            {
                if(jobApplied != null && jobApplied.getJobPost() != null)
                    allJobs.add(jobApplied.getJobPost());
            }
            // Setup RecyclerView
            recyclerViewAppliedJob.setLayoutManager(new LinearLayoutManager(getContext()));
            //adapterJobPost = new JobPostAdapter(allJobs, jobPostViewModel); // mặc định show tất cả
            adapterJobPost = new JobPostAdapter(allJobs, new JobPostAdapter.OnJobPostClickListener() {
                @Override
                public void onJobPostClick(JobPost jobPost) {
                    // Handle item click
                    Bundle bundle = new Bundle();
                    jobPostViewModel.setCurrentJobPost(jobPost);
                    bundle.putSerializable("user", user);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                }

                @Override
                public void onSaveClick(JobPost jobpost) {
                    jobPostViewModel.createJobSavedResult().observe(getViewLifecycleOwner(), result -> {
                        if(result != null)
                            Log.e("HomeFragment", "createJobSavedResult: " + result + "");
                        else
                            Log.e("HomeFragment", "createJobSavedResult: null");
                    });
                    if(user.getApplicantId() != null)
                    {
                        JobSavedRequest jobSave = new JobSavedRequest(user.getApplicantId(), jobpost.getId());
                        jobPostViewModel.createJobSaved(jobSave);
                    }
                    return;
                }

                @Override
                public void onReportClick(JobPost jobpost) {
                    return;
                }
            });
            recyclerViewAppliedJob.setAdapter(adapterJobPost);
        });
    }
}