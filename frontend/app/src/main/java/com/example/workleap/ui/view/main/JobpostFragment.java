package com.example.workleap.ui.view.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.MyJobPostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class JobpostFragment extends Fragment{
    private RecyclerView recyclerView;
    private MyJobPostAdapter adapter;
    private List<JobPost> allJobs = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;

    private User user;
    private Bundle bundle;
    private NavController nav;
    private ImageButton btnCreateJobPost;

    public JobpostFragment() {
        // Required empty public constructor
    }

    public static JobpostFragment newInstance(String param1, String param2) {
        JobpostFragment fragment = new JobpostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        nav = NavHostFragment.findNavController(this);

        View v = inflater.inflate(R.layout.fragment_my_jobpost, container, false);
        btnCreateJobPost = v.findViewById(R.id.btnAdd);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerJobPosts); // ID trong layout
        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        user = (User) getArguments().getSerializable("user");
        bundle = new Bundle();
        bundle.putSerializable("companyId", user.getCompanyId());

        jobPostViewModel.getJobPostsByCompanyResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = result.toString();
            Log.e("JobpostFragment", "getJobPost Company Result: " + s + "");
        });
        jobPostViewModel.getJobPostsByCompanyData().observe(getViewLifecycleOwner(), jobPosts ->
        {
            if(jobPosts != null)
                allJobs.addAll(jobPosts);
            // Setup RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //show tat ca jobpost va vao detail fragment khi click vao item
            adapter = new MyJobPostAdapter(allJobs, jobPostViewModel, new MyJobPostAdapter.OnJobPostClickListener() {
                @Override
                public void onJobPostClick(JobPost jobPost) {
                    // Handle item click
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jobPost", jobPost);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.detailMyJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        jobPostViewModel.getJobPostsByCompany(user.getCompanyId());

        btnCreateJobPost.setOnClickListener(x ->
                {
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    nav.navigate(R.id.createJobpostFragment, bundle);
                }
        );

        //Viet lenh de khi click vao mot item trong recycler view thi se navigate den fragment DetailJObPost, dong thoi gui di jobpost do
    }
}