package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubmittedProfilesFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;
    ArrayList<JobApplied> submittedProfiles;
    private JobPost currentJobPost;
    private TextView tvProfileCount, tvExport, tvStatus;
    private EditText etSearch;
    private ImageButton btnFilter;
    private RecyclerView rvSubmittedCVs;

    public SubmittedProfilesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_submitted_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProfileCount = view.findViewById(R.id.tvProfileCount);
        etSearch = view.findViewById(R.id.etSearch);
        tvExport = view.findViewById(R.id.tvExport);
        btnFilter = view.findViewById(R.id.btnFilter);
        rvSubmittedCVs = view.findViewById(R.id.rvSubmittedCVs);

        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.getMyJobPostById(currentJobPost.getId());
        jobPostViewModel.getMyJobPostByIdData().observe(getViewLifecycleOwner(), jobpost -> {
            if(jobpost==null)
            {
                Log.e("SubProfileFragment", "getMyJobPostByIdData NULL");
                return;
            }


            this.submittedProfiles = jobpost.getJobApplied();
            if(submittedProfiles==null)
            {
                Log.e("eeeeee", currentJobPost.getId());
                Log.e("SubmitProfileFragment", "submittedProfiles NULL");
                return;
            }
            else
            {
                SubmittedProfilesAdapter adapter = new SubmittedProfilesAdapter(
                        submittedProfiles,
                        new SubmittedProfilesAdapter.OnSubmittedProfilesMenuClickListener() {
                            @Override
                            public void onOpen(JobApplied jobApplied) {
                                // TODO: Implement open logic
                            }

                            @Override
                            public void onDownload(JobApplied jobApplied) {
                                // TODO: Implement download logic
                            }

                            @Override
                            public void onApprove(JobApplied jobApplied) {
                                // TODO: Implement approve logic
                            }

                            @Override
                            public void onDismiss(JobApplied jobApplied) {
                                // TODO: Implement dismiss logic
                            }
                        }
                );

                rvSubmittedCVs.setLayoutManager(new LinearLayoutManager(requireContext()));
                rvSubmittedCVs.setAdapter(adapter);

                tvProfileCount.setText(String.valueOf(submittedProfiles.size()));
                Log.e("SubmitProfileFragment", "submitted profiles NOT null");
            }


        });
    }

}
