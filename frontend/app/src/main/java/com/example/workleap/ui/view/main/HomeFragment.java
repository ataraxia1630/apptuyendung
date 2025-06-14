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

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

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

    private NavController nav;

    private User user;

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
        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        //lay user cho detail jobpost applied cv
        user = (User) getArguments().getSerializable("user");
        if(user==null) Log.e("HomeFragment", "user null");

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
                    bundle.putSerializable("jobPost", jobPost);
                    bundle.putSerializable("user", user);
                    ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                    nav.navigate(R.id.detailMyJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                }
            });
            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });
        jobPostViewModel.getAllJobPosts();


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
            allPosts.clear();
            if(posts != null)
                allPosts.addAll(posts);
            // Setup RecyclerView
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
            adapterPost = new PostAdapter(allPosts, postViewModel); // mặc định show tất cả
            recyclerViewPost.setAdapter(adapterPost);
            adapterPost.notifyDataSetChanged();
        });
        postViewModel.getAllPost();
    }
}