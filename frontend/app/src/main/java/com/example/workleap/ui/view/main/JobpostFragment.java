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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.MyJobPostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class JobpostFragment extends Fragment{
    private RecyclerView recyclerViewJobPost;
    private RecyclerView recyclerViewPost;
    private MyJobPostAdapter adapterJobPost;
    private MyPostAdapter adapterPost;
    private List<JobPost> allJobs = new ArrayList<>();
    private List<Post> allPosts = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;
    private PostViewModel postViewModel;

    private User user;
    private Bundle bundle;
    private NavController nav;
    private ImageButton btnCreateNew;

    private boolean isOnJobPostTab = true;

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
        btnCreateNew = v.findViewById(R.id.btnAdd);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewJobPost = view.findViewById(R.id.recyclerJobPosts);
        recyclerViewPost = view.findViewById(R.id.recyclerPosts);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());
        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);
        postViewModel.InitiateRepository(getContext());

        user = (User) getArguments().getSerializable("user");
        bundle = new Bundle();
        bundle.putSerializable("companyId", user.getCompanyId());

        //Tab handle
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        LinearLayout layoutJobPost = view.findViewById(R.id.layoutJobPost);
        LinearLayout layoutPost = view.findViewById(R.id.layoutPost);
        // Thêm 2 tab
        tabLayout.addTab(tabLayout.newTab().setText("MY JOBPOST"));
        tabLayout.addTab(tabLayout.newTab().setText("MY POST"));
        // Xử lý khi chọn tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    layoutJobPost.setVisibility(View.VISIBLE);
                    layoutPost.setVisibility(View.GONE);
                    isOnJobPostTab = true;
                } else {
                    layoutJobPost.setVisibility(View.GONE);
                    layoutPost.setVisibility(View.VISIBLE);
                    isOnJobPostTab = false;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });


        //Load jobpost
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
            recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
            //show tat ca jobpost va vao detail fragment khi click vao item
            adapterJobPost = new MyJobPostAdapter(allJobs, jobPostViewModel, new MyJobPostAdapter.OnJobPostClickListener() {
                @Override
                public void onJobPostClick(JobPost jobPost) {
                    // Handle item click
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jobPost", jobPost);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.detailMyJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                }
            });
            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });
        jobPostViewModel.getJobPostsByCompany(user.getCompanyId());


        //Load Post
        postViewModel.getPostCompanyResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = result.toString();
            Log.e("PostFragment", "getPost Company Result: " + s + "");
        });
        postViewModel.getPostCompanyData().observe(getViewLifecycleOwner(), posts ->
        {
            if(posts != null)
                allPosts.addAll(posts);
            else
                Log.d("posts: ", "null");
            // Setup RecyclerView
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
            //show tat ca jobpost va vao detail fragment khi click vao item
            adapterPost = new MyPostAdapter(allPosts, postViewModel);
            /*adapter = new MyPostAdapter(allPosts, postViewModel, new MyPostAdapter.OnPostClickListener() {
                @Override
                public void onPostClick(JobPost post) {
                    // Handle item click
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.DetailMyPostFragment, bundle); // Navigate to DetailPostFragment
                }
            });*/
            recyclerViewPost.setAdapter(adapterPost);
            adapterPost.notifyDataSetChanged();
        });
        postViewModel.getPostByCompany(user.getCompanyId());


        //Create jobpost or post
        btnCreateNew.setOnClickListener(x ->
                {
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    if(isOnJobPostTab == true)
                        nav.navigate(R.id.createJobpostFragment, bundle);
                    else
                        nav.navigate(R.id.createPostFragment, bundle);
                }
        );
    }
}