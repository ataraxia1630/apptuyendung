package com.example.workleap.ui.view.main.home;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobSaved;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.jobpost_post.JobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.PostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.example.workleap.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewJobPost, recyclerViewPost;
    private JobPostAdapter adapterJobPost;
    private PostAdapter adapterPost;
    private List<JobPost> allJobs = new ArrayList<>();
    private List<Post> allPosts = new ArrayList<>();
    private JobPostViewModel jobPostViewModel;
    private PostViewModel postViewModel;
    private UserViewModel userViewModel;
    private int pageJobPost = 1;
    private int pageSizeJobPost = 4;
    private int pagePost = 1;
    private int pageSizePost = 4;
    private boolean isSearching = false;
    private boolean isAdvancedSearching = false;
    String title, location, position, company, educationRequirement;
    private String searchText = "";

    private ImageButton btnPrev, btnNext, btnAdvancedSearch, btnClose;
    private ProgressBar progressLoadMore;
    private Button btnMorePost;
    private TextView tvPageNumber;
    private TabLayout tabFilter;
    private String tabName = "New";

    private boolean isMorePost = false; // Kiểm tra đang tải lại fragment hay tải thêm bài đăng
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
        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        tvPageNumber = view.findViewById(R.id.tvPageNumber);
        btnMorePost = view.findViewById(R.id.btnLoadMorePosts);
        btnAdvancedSearch = view.findViewById(R.id.btnAdvanceSearch);
        btnClose = view.findViewById(R.id.btnClose);
        progressLoadMore = view.findViewById(R.id.progressLoadMore);

        jobPostViewModel  = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());

        //lay user cho detail jobpost applied cv
        user = (User) getArguments().getSerializable("user");
        if(user==null) Log.e("HomeFragment", "user null");

        //filter tab
        tabFilter = view.findViewById(R.id.tabLayout);
        tabFilter.addTab(tabFilter.newTab().setText("New"));
        if(user.getApplicantId() != null)
        {
            tabFilter.addTab(tabFilter.newTab().setText("For You"));
            tabFilter.addTab(tabFilter.newTab().setText("Saved"));
        }

        tabFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabName = tab.getText().toString();
                Log.d("TabFilter", "Selected tab: " + tabName);

                //Loading
                adapterJobPost.showShimmer(); // trước khi load

                //Reset page
                pageJobPost = 1;
                tvPageNumber.setText(String.valueOf(pageJobPost));

                switch (tabName) {
                    case "New":
                        jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                        break;
                    case "Following":
                        // TODO: Lọc theo các công ty đang theo dõi
                        break;
                    case "For You":
                        jobPostViewModel.getJobPostsRecommend(pageJobPost, pageSizeJobPost);
                        break;
                    case "Saved":
                        jobPostViewModel.getAllJobSaved(user.getApplicantId());
                        break;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });


        //Khoi tao adapter voi loading effect
        adapterJobPost = new JobPostAdapter(new ArrayList<>(), new JobPostAdapter.OnJobPostClickListener() {
            @Override
            public void onJobPostClick(JobPost jobPost) {
                // Handle item click
                Bundle bundle = new Bundle();
                jobPostViewModel.setCurrentJobPost(jobPost);
                bundle.putSerializable("jobPost", jobPost);
                bundle.putSerializable("user", user);
                ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                nav.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment
            }

            @Override
            public void onSaveClick(JobPost jobpost) {
                jobPostViewModel.createJobSavedResult().observe(getViewLifecycleOwner(), result -> {
                    if(result != null)
                    {
                        Log.e("HomeFragment", "createJobSavedResult: " + result + "");
                        ToastUtil.showToast(getContext(), "You has saved this jobpost", ToastUtil.TYPE_SUCCESS);
                    }
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
                Bundle bundle = new Bundle();
                bundle.putString("type", "jobpost");
                bundle.putString("targetId", jobpost.getId());
                bundle.putString("targetName", jobpost.getTitle());
                nav.navigate(R.id.sendReportFragment, bundle);
                return;
            }
        });
        adapterJobPost.showShimmer(); // trước khi load
        recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewJobPost.setAdapter(adapterJobPost);

        //observe list jobpost
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
            adapterJobPost.hideShimmer(allJobs); //An shimmer, hien thi du lieu

            //Logo jobpost bang usermodel
            userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                adapterJobPost.setLogoUrlMap(map);  // Truyền map xuống adapter
            });
            for (JobPost jobPost : jobPosts) {
                userViewModel.getLogoJobPostImageUrl(jobPost.getCompany().getUser().get(0).getAvatar()); //dung logopath company lam key
            }

            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });
        jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);

        //observe recommend jobpost
        jobPostViewModel.getJobPostsRecommendResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("HomeFragment", "getjobrecommendResult: " + result + "");
            else
                Log.d("HomeFragment", "getjobrecommendResult: null");
                });
        jobPostViewModel.getJobPostsRecommendData().observe(getViewLifecycleOwner(), jobPosts ->
        {
            allJobs.clear();
            if(jobPosts != null)
                allJobs.addAll(jobPosts);
            // Setup RecyclerView
            recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
            //adapterJobPost = new JobPostAdapter(allJobs, jobPostViewModel); // mặc định show tất cả
            adapterJobPost.hideShimmer(jobPosts);

            //Logo jobpost bang usermodel
            userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                adapterJobPost.setLogoUrlMap(map);  // Truyền map xuống adapter
            });
            for (JobPost jobPost : jobPosts) {
                userViewModel.getLogoJobPostImageUrl(jobPost.getCompany().getUser().get(0).getAvatar()); //dung logopath company lam key
            }

            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });

        //observe saved jobpost
        jobPostViewModel.getAllJobSavedResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("HomeFragment", "getAllJobSavedResult: " + result + "");
            else
                Log.d("HomeFragment", "getAllJobSavedResult: null");
        });
        jobPostViewModel.getAllJobSaved().observe(getViewLifecycleOwner(), jobPosts ->
        {
            allJobs.clear();
            if(jobPosts != null)
                allJobs.addAll(jobPosts);
            // Setup RecyclerView
            recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
            //adapterJobPost = new JobPostAdapter(allJobs, jobPostViewModel); // mặc định show tất cả
            adapterJobPost.hideShimmer(jobPosts);

            //Logo jobpost bang usermodel
            userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                adapterJobPost.setLogoUrlMap(map);  // Truyền map xuống adapter
            });
            for (JobPost jobPost : jobPosts) {
                if(jobPost.getCompany() != null)
                    userViewModel.getLogoJobPostImageUrl(jobPost.getCompany().getUser().get(0).getAvatar()); //dung logopath company lam key
            }

            recyclerViewJobPost.setAdapter(adapterJobPost);
            adapterJobPost.notifyDataSetChanged();
        });

        //Page for jobpost
        btnPrev.setOnClickListener(v -> {
            if (pageJobPost > 1) {
                pageJobPost--;

                //Loading
                adapterJobPost.showShimmer(); // trước khi load

                if(isAdvancedSearching)
                {
                    jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, title, location, position, company, educationRequirement);
                    tvPageNumber.setText(String.valueOf(pageJobPost));
                    return;
                }

                if(isSearching)
                {
                    jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, searchText, "", "", "", "");
                    tvPageNumber.setText(String.valueOf(pageJobPost));
                    return;
                }


                //Tuy thuoc tab filter
                switch (tabName) {
                    case "New":
                        jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                        break;
                    case "Following":
                        break;
                    case "For You":
                        jobPostViewModel.getJobPostsRecommend(pageJobPost, pageSizeJobPost);
                        break;
                    case "Saved":
                        Log.d("hi", "hi bug");
                        jobPostViewModel.getAllJobSaved(user.getApplicantId());
                        break;
                }

                tvPageNumber.setText(String.valueOf(pageJobPost));
            }
        });
        btnNext.setOnClickListener(v -> {
            if(allJobs.size() < pageSizeJobPost)
            {
                ToastUtil.showToast(this.getContext(), "No more posts", ToastUtil.TYPE_ERROR);
                return;
            }

            pageJobPost++;
            adapterJobPost.showShimmer(); // trước khi load

            if(isAdvancedSearching)
            {
                jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, title, location, position, company, educationRequirement);
                tvPageNumber.setText(String.valueOf(pageJobPost));
                return;
            }

            if(isSearching)
            {
                jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, searchText, "", "", "", "");
                tvPageNumber.setText(String.valueOf(pageJobPost));
                return;
            }

            //Tuy thuoc tab filter
            switch (tabName) {
                case "New":
                    jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                    break;
                case "Following":
                    // TODO: Lọc theo các công ty đang theo dõi
                    break;
                case "For You":
                    jobPostViewModel.getJobPostsRecommend(pageJobPost, pageSizeJobPost);
                    break;
                case "Saved":
                    jobPostViewModel.getAllJobSaved(user.getApplicantId());
                    break;
            }

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
            // Dữ liệu đã load xong
            btnMorePost.setVisibility(View.VISIBLE);
            progressLoadMore.setVisibility(View.GONE);

            if(!isMorePost)
               allPosts.clear(); //Neu khong phai tai them thi clear de tranh bi trung

            isMorePost = false; //Dat lai neu dang la true

            if(posts != null && !posts.isEmpty()) {
                allPosts.addAll(posts);
            }
            else
                ToastUtil.showToast(this.getContext(), "No more posts", ToastUtil.TYPE_ERROR);


            // Setup RecyclerView
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
            adapterPost = new PostAdapter(allPosts, postViewModel, this, requireActivity().getSupportFragmentManager(), user, nav, recyclerViewPost); // mặc định show tất cả

            //Xu li anh cua post bang postviewmodel va logo cua post bang usermodel
            postViewModel.getImageUrlMap().observe(getViewLifecycleOwner(), map -> {
                adapterPost.setImageUrlMap(map);  // Truyền map xuống adapter
            });
            userViewModel.getLogoPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                adapterPost.setLogoUrlMap(map);  // Truyền map xuống adapter
            });
            for (Post post : posts) {
                if(post.getContents().size() > 1)
                {
                    String filePath = post.getContents().get(1).getValue();  // hoặc chỗ chứa đường dẫn ảnh
                    postViewModel.getImageUrlMap(filePath); // dùng filePath làm key
                }
                userViewModel.getLogoPostImageUrl(post.getCompany().getUser().get(0).getAvatar()); //dung logopath company lam key
            }

            //Hien thi
            recyclerViewPost.setAdapter(adapterPost);
            adapterPost.notifyDataSetChanged();
        });
        postViewModel.getAllPost(pagePost, pageSizePost);

        //Load more posts
        btnMorePost.setOnClickListener(v -> {
            //Loading
            btnMorePost.setVisibility(View.GONE);
            progressLoadMore.setVisibility(View.VISIBLE);

            pagePost++;
            isMorePost = true;
            postViewModel.getAllPost(pagePost, pageSizePost);
        });

        //observe search
        jobPostViewModel.getSearchJobPostResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.e("HomeFragment", "getSearchJobPostResult: " + result + "");
            else
                Log.e("HomeFragment", "getSearchJobPostResult: null");
        });
        jobPostViewModel.getSearchJobPostData().observe(getViewLifecycleOwner(), data -> {
            allJobs.clear();
            if(data != null)
            {
                allJobs.addAll(data);
            }
            adapterJobPost.hideShimmer(allJobs);
            adapterJobPost.notifyDataSetChanged();
        });
        
        //Search
        SearchView searchView = view.findViewById(R.id.searchView);
        //Debounce chống spam API khi gõ
        Handler handler = new Handler();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String title) {
                isSearching = true;
                searchText = title;
                //Loading
                adapterJobPost.showShimmer();
                // Tim theo title neu search thong thuong

                pageJobPost = 1; //reset when search
                jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, title, "", "", "", "");

                //search post
                postViewModel.searchPostResult().observe(getViewLifecycleOwner(), result -> {
                    if(result != null)
                        Log.e("HomeFragment", "getSearchPostResult: " + result + "");
                    else
                        Log.e("HomeFragment", "getSearchPostResult: null");
                });
                postViewModel.searchPostData().observe(getViewLifecycleOwner(), post -> {
                    if(post != null)
                    {
                        allPosts.clear();
                        allPosts.addAll(post);
                    }
                    adapterPost.notifyDataSetChanged();
                });
                postViewModel.searchPosts(title, "");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Khi người dùng xóa hết chữ (bấm X)
                    pageJobPost = 1;
                    pagePost = 1;
                    tvPageNumber.setText("1");
                    isSearching = false;
                    jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                    postViewModel.getAllPost(pagePost, pageSizePost);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // HÀM NÀY SẼ CHẠY KHI NGƯỜI DÙNG ĐÓNG SEARCHVIEW
                // reset kết quả tìm kiếm
                pageJobPost = 1;
                pagePost = 1;
                tvPageNumber.setText("1");
                isSearching = false;
                jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                postViewModel.getAllPost(pagePost, pageSizePost);
                return false; // trả về true nếu đã xử lý hành vi đóng
            }
        });

        //Close advanced search
        btnClose.setOnClickListener( v -> {
            btnClose.setVisibility(View.GONE);
            isAdvancedSearching = false;
            pageJobPost = 1;
            pagePost = 1;
            tvPageNumber.setText("1");
            isSearching = false;
            adapterJobPost.showShimmer();
            jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
            postViewModel.getAllPost(pagePost, pageSizePost);
        });

        btnAdvancedSearch.setOnClickListener(v -> {
            btnClose.setVisibility(View.VISIBLE);
            isAdvancedSearching = true;

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View dialogView = inflater.inflate(R.layout.dialog_advanced_search, null);

            TabLayout tabLayout = dialogView.findViewById(R.id.tabLayout);
            ViewFlipper viewFlipper = dialogView.findViewById(R.id.viewFlipper);

            // Thêm 2 tab
            tabLayout.addTab(tabLayout.newTab().setText("JOB POST"));
            tabLayout.addTab(tabLayout.newTab().setText("POST"));

            // Mặc định tab đầu
            viewFlipper.setDisplayedChild(0);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewFlipper.setDisplayedChild(tab.getPosition());
                }

                @Override public void onTabUnselected(TabLayout.Tab tab) {}
                @Override public void onTabReselected(TabLayout.Tab tab) {}
            });

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Advanced Search")
                    .setView(dialogView)
                    .setNegativeButton("Cancel", (d, i) -> {
                        btnClose.setVisibility(View.GONE);
                        isAdvancedSearching = false;
                    })
                    .setPositiveButton("OK", (d, i) -> {
                        if (tabLayout.getSelectedTabPosition() == 0) {

                            // Tìm việc
                            title = ((EditText) dialogView.findViewById(R.id.edtJobTitle)).getText().toString();
                            location = ((EditText) dialogView.findViewById(R.id.edtLocation)).getText().toString();
                            position = ((EditText) dialogView.findViewById(R.id.edtPosition)).getText().toString();
                            company = ((EditText) dialogView.findViewById(R.id.edtCompanyNameJob)).getText().toString();
                            educationRequirement = ((EditText) dialogView.findViewById(R.id.edtEducationRequirement)).getText().toString();

                            //Neu rong
                            if (title.isEmpty() && location.isEmpty() && position.isEmpty() && company.isEmpty() && educationRequirement.isEmpty()) {
                                pageJobPost = 1;
                                jobPostViewModel.getAllJobPosts(pageJobPost, pageSizeJobPost);
                                return;
                            }

                            pageJobPost = 1; //reset when search

                            //Load
                            adapterJobPost.showShimmer();
                            jobPostViewModel.searchJobPosts(pageJobPost, pageSizeJobPost, title, location, position, educationRequirement, company);
                        } else {
                            // Tìm bài đăng
                            title = ((EditText) dialogView.findViewById(R.id.edtPostTitle)).getText().toString();
                            company = ((EditText) dialogView.findViewById(R.id.edtCompanyNamePost)).getText().toString();

                            //Neu rong
                            if (title.isEmpty() && company.isEmpty()) {
                                pageJobPost = 1;
                                postViewModel.getAllPost(pagePost, pageSizePost);
                                return;
                            }

                            //search post
                            postViewModel.searchPostResult().observe(getViewLifecycleOwner(), result -> {
                                if(result != null)
                                    Log.e("HomeFragment", "getSearchPostResult: " + result + "");
                                else
                                    Log.e("HomeFragment", "getSearchPostResult: null");
                            });
                            postViewModel.searchPostData().observe(getViewLifecycleOwner(), post -> {
                                if(post != null)
                                {
                                    allPosts.clear();
                                    allPosts.addAll(post);
                                }
                                adapterPost.notifyDataSetChanged();
                            });

                            postViewModel.searchPosts(title, company);
                        }
                    })
                    .create();

            dialog.show();
        });
    }
}