package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobpostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobpostFragment extends Fragment {
    private RecyclerView recyclerView;
    private JobPostAdapter adapter;
    private List<JobPost> allJobs = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;

    public JobpostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobpostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobpostFragment newInstance(String param1, String param2) {
        JobpostFragment fragment = new JobpostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_jobpost, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerJobPosts); // ID trong layout
        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());


        jobPostViewModel.getAllJobPostResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = result.toString();
            Log.e("JobpostFragment", "getAllJobPostResult: " + s + "");
        });
        jobPostViewModel.getAllJobPostData().observe(getViewLifecycleOwner(), jobPosts ->
        {
            allJobs.addAll(jobPosts);
            // Setup RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new JobPostAdapter(allJobs); // mặc định show tất cả
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        jobPostViewModel.getAllJobPosts();

    }
}