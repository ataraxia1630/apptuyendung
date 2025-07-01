package com.example.workleap.ui.view.main.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeJobPostFragment extends Fragment {

    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation;
    private ImageView imgAvatar;
    private ImageButton btnBack, btnOption;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageButton btnProfile;
    private JobPostViewModel jobPostViewModel;
    private UserViewModel userViewModel;

    private User user;

    private JobPost currentJobPost;

    public HomeJobPostFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_job_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        // Find views
        txtJobName = view.findViewById(R.id.txtJobName);
        txtCompanyName = view.findViewById(R.id.txtCompanyName);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtLocation = view.findViewById(R.id.txtLocation);
        btnBack = view.findViewById(R.id.btnBack);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        btnProfile = view.findViewById(R.id.btnProfile);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        // Back navigation
        btnBack.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true); // Show bottom navigation
            NavHostFragment.findNavController(this).navigateUp();
        });

        btnProfile.setOnClickListener(v -> {
            //Move to profile company
            Bundle bundle = new Bundle();
            bundle.putSerializable("myUser", user);
            bundle.putString("userId", currentJobPost.getCompany().getUser().get(0).getId());
            NavHostFragment.findNavController(this).navigate(R.id.watchCompanyProfileFragment, bundle);
        });

        // Get current jobpost
        jobPostViewModel.getCurrentJobPost().observe(getViewLifecycleOwner(), jobPost -> {
            if (jobPost != null) {
                this.currentJobPost = jobPost;

                txtJobName.setText(jobPost.getTitle());
                txtCompanyName.setText(jobPost.getCompany().getName());
                txtSalary.setText(jobPost.getSalaryStart() + " - " + jobPost.getSalaryEnd() + " " + jobPost.getCurrency());
                txtLocation.setText(jobPost.getWorkingAddress());
                //Lay user de lay avatar
                userViewModel.getUser(currentJobPost.getCompany().getUser().get(0).getId());
            }

            // Setup ViewPager2 sau khi co current jobpost
            HomeJobPostPagerAdapter adapter = new HomeJobPostPagerAdapter(this, user, currentJobPost);
            viewPager.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                if (position == 0) tab.setText("Decriptions");
                else tab.setText("Company");
            }).attach();
        });

        //Lay avatar
        //Observe
        userViewModel.getUrlAvatarResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("CompanyProfile avatar", result);
            else
                Log.d("Companyprofile avatar", "getUrlAvatarResult NULL");
        });
        userViewModel.getUrlAvatarData().observe(getViewLifecycleOwner(), dataImage -> {
            if(dataImage != null)
            {
                Glide.with(this.getContext()).load(dataImage).into(imgAvatar);
                Log.d("ApplicantProfile avatar", "Set avatar success");
            }
            else
                Log.d("ApplicantProfile avatar", "getUrlAvatarData NULL");
        });

        //Set value from user, call api
        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                //Check and get avatar
                if(data.getAvatar() != null)
                {
                    //Load avatar from database
                    userViewModel.getAvatarUrl(data.getAvatar());
                }
                else
                    Log.d("Logo jobpost avatar", "user avatar null");
            }
            else
                Log.d("Logo jobpost avatar", "user null");
        });
    }
}
