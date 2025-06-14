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
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewJobPost, recyclerViewPost;
    private MyJobPostAdapter adapterJobPost;
    private PostAdapter adapterPost;
    private List<JobPost> allJobs = new ArrayList<>();
    private List<Post> allPosts = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;
    private PostViewModel postViewModel;
    private int pageJobPost = 1;
    private int pageSizeJobPost = 4;
    private int pagePost = 1;
    private int pageSizePost = 4;

    private ImageButton btnPrev, btnNext;
    private Button btnMorePost;
    private TextView tvPageNumber;

    private boolean isMorePost = false; // Kiểm tra đang tải lại fragment hay tải thêm bài đăng
    private NavController nav;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //JOBPOST LIST
        recyclerViewJobPost = view.findViewById(R.id.recyclerJobPosts); // ID trong layout
        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        tvPageNumber = view.findViewById(R.id.tvPageNumber);
        btnMorePost = view.findViewById(R.id.btnLoadMorePosts);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        jobPostViewModel.getAllJobPostResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = result.toString();
            Log.e("HomeFragment", "getAllJobPostResult: " + s + "");
        });
        jobPostViewModel.getAllJobPostData().observe(getViewLifecycleOwner(), jobPosts ->
        {
            allJobs.clear();
            if(jobPosts != null)
                allJobs.addAll(jobPosts);
            // Setup RecyclerView
            recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
            //adapterJobPost = new JobPostAdapter(allJobs, jobPostViewModel); // mặc định show tất cả
            adapterJobPost = new MyJobPostAdapter(allJobs, jobPostViewModel, new MyJobPostAdapter.OnJobPostClickListener() {
                @Override
                public void onJobPostClick(JobPost jobPost) {
                    // Handle item click
                    Bundle bundle = new Bundle();
                    jobPostViewModel.setCurrentJobPost(jobPost);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.detailMyJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                }
            });
            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });
        jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);

        //Page for jobpost
        btnPrev.setOnClickListener(v -> {
            if (pageJobPost > 1) {
                pageJobPost--;
                jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                tvPageNumber.setText(String.valueOf(pageJobPost));
            }
        });
        btnNext.setOnClickListener(v -> {
            pageJobPost++;
            jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
            tvPageNumber.setText(String.valueOf(pageJobPost));
        });

        //POST LIST
        recyclerViewPost = view.findViewById(R.id.recyclerViewPosts); // ID trong layout
        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);
        postViewModel.InitiateRepository(getContext());

        postViewModel.getAllPostResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = result.toString();
            Log.e("HomeFragment", "getAllPostResult: " + s + "");
        });
        postViewModel.getAllPostData().observe(getViewLifecycleOwner(), posts ->
        {
            if(!isMorePost)
               allPosts.clear(); //Neu khong phai tai them thi clear de tranh bi trung

            isMorePost = false; //Dat lai neu dang la true

            if(posts != null && !posts.isEmpty()) {
                allPosts.addAll(posts);
                Toast.makeText(this.getContext(), "Loading Posts...", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this.getContext(), "No more posts", Toast.LENGTH_SHORT).show();

            // Setup RecyclerView
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
            adapterPost = new PostAdapter(allPosts, postViewModel); // mặc định show tất cả
            recyclerViewPost.setAdapter(adapterPost);
            adapterPost.notifyDataSetChanged();
        });
        postViewModel.getAllPost(pagePost, pageSizePost);

        //Load more posts
        btnMorePost.setOnClickListener(v -> {
            pagePost++;
            isMorePost = true;
            postViewModel.getAllPost(pagePost, pageSizePost);
        });
    }
}