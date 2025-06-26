package com.example.workleap.ui.view.main.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.jobpost_post.MyJobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.MyPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.PostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class ManageJobPostFragment extends Fragment {

    private RecyclerView recyclerViewJobPost, recyclerViewPost;
    private MyJobPostAdapter adapterJobPost;
    private PostAdapter adapterPost;
    private List<JobPost> jobPostList = new ArrayList<>();
    private List<Post> postList = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;
    private PostViewModel postViewModel;
    private int page = 1;
    private int pageSize = 5;

    private ImageButton btnPrev, btnNext;
    private TextView tvPageNumber;
    private TabLayout tabLayoutStatus;
    private NavController nav;

    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);
        return inflater.inflate(R.layout.fragment_manage_job_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");

        recyclerViewJobPost = view.findViewById(R.id.recyclerJobPosts);
        recyclerViewPost = view.findViewById(R.id.recyclerViewPosts);
        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        tvPageNumber = view.findViewById(R.id.tvPageNumber);
        tabLayoutStatus = view.findViewById(R.id.tabLayoutStatus);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());
        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);
        postViewModel.InitiateRepository(getContext());

        //adapterJopPost
        adapterJobPost = new MyJobPostAdapter(jobPostList, jobPostViewModel, jobPost -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("jobPost", jobPost);
            nav.navigate(R.id.HomeJobPostFragment, bundle);
        });
        recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewJobPost.setAdapter(adapterJobPost);

        //adapterPost
        adapterPost = new PostAdapter(postList, postViewModel, this, requireActivity().getSupportFragmentManager(), user, nav);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPost.setAdapter(adapterPost);

        //tablayout
        tabLayoutStatus.addTab(tabLayoutStatus.newTab().setText("Pending"));
        //tabLayoutStatus.addTab(tabLayoutStatus.newTab().setText("Approved"));
        tabLayoutStatus.addTab(tabLayoutStatus.newTab().setText("Rejected"));

        tabLayoutStatus.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page = 1;
                loadJobPostsByStatus(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        btnPrev.setOnClickListener(v -> {
            if (page > 1) {
                page--;
                loadJobPostsByStatus(tabLayoutStatus.getSelectedTabPosition());
            }
        });

        btnNext.setOnClickListener(v -> {
            page++;
            loadJobPostsByStatus(tabLayoutStatus.getSelectedTabPosition());
        });

        // mac dinh tab dau tien va load danh sach
        tabLayoutStatus.selectTab(tabLayoutStatus.getTabAt(0));
        loadJobPostsByStatus(0);
    }

    private void loadJobPostsByStatus(int statusTabIndex) {
        if (statusTabIndex == 0) {
            //jobpost
            jobPostViewModel.getJobPostByStatus(page, pageSize, "OPENING");
            jobPostViewModel.getJobPostByStatusData().observe(getViewLifecycleOwner(), jobPosts -> {
                if(jobPosts==null)
                {
                    Log.e("ManageJobPostFragment", "getJobPostByStatusData NULL");
                    return;
                }
                updateJobPostList(jobPosts);
            });
            jobPostViewModel.getJobPostByStatusResult().observe(getViewLifecycleOwner(), result->{
                Log.e("ManageJobPostFragment", "getJobPostByStatusResult: " + result);
            });

            //post
            postViewModel.getPostByStatus(page, pageSize, "OPENING");
            postViewModel.getPostByStatusData().observe(getViewLifecycleOwner(), posts -> {
                if(posts==null)
                {
                    Log.e("ManageJobPostFragment", "getPostByStatusData NULL");
                    return;
                }
                updatePostList(posts);
            });
            postViewModel.getPostByStatusResult().observe(getViewLifecycleOwner(), result->{
                Log.e("ManageJobPostFragment", "getPostByStatusResult: " + result);
            });

        } else {
            jobPostViewModel.getJobPostByStatus(page, pageSize, "CANCELLED");
            jobPostViewModel.getJobPostByStatusData().observe(getViewLifecycleOwner(), jobPosts -> {
                if(jobPosts==null)
                {
                    Log.e("ManageJobPostFragment", "getJobPostByStatusData NULL");
                    return;
                }
                updateJobPostList(jobPosts);
            });
            jobPostViewModel.getJobPostByStatusResult().observe(getViewLifecycleOwner(), result->{
                Log.e("ManageJobPostFragment", "getJobPostByStatusResult: " + result);
            });

            postViewModel.getPostByStatus(page, pageSize, "CANCELLED");
            postViewModel.getPostByStatusData().observe(getViewLifecycleOwner(), posts -> {
                if(posts==null)
                {
                    Log.e("ManageJobPostFragment", "getPostByStatusData NULL");
                    return;
                }
                updatePostList(posts);
            });
            postViewModel.getPostByStatusResult().observe(getViewLifecycleOwner(), result->{
                Log.e("ManageJobPostFragment", "getPostByStatusResult: " + result);
            });
        }
        tvPageNumber.setText(String.valueOf(page));
    }

    private void updateJobPostList(List<JobPost> jobPosts) {
        jobPostList.clear();
        if (jobPosts != null) jobPostList.addAll(jobPosts);
        adapterJobPost.notifyDataSetChanged();
    }
    private void updatePostList(List<Post> posts) {
        postList.clear();
        if (posts != null) postList.addAll(posts);
        adapterPost.notifyDataSetChanged();
    }
}
