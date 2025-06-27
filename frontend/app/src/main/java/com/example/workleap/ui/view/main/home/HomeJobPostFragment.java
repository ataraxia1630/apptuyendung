package com.example.workleap.ui.view.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeJobPostFragment extends Fragment {

    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation;
    private ImageButton btnBack, btnOption;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private JobPostViewModel jobPostViewModel;

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

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        // Find views
        txtJobName = view.findViewById(R.id.txtJobName);
        txtCompanyName = view.findViewById(R.id.txtCompanyName);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtLocation = view.findViewById(R.id.txtLocation);
        btnBack = view.findViewById(R.id.btnBack);
        btnOption = view.findViewById(R.id.btnOption);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Back navigation
        btnBack.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true); // Show bottom navigation
            NavHostFragment.findNavController(this).navigateUp();
        });

        // Option (can be customized)
        btnOption.setOnClickListener(v -> {
            // TODO: handle options
        });

        // Get current jobpost
        jobPostViewModel.getCurrentJobPost().observe(getViewLifecycleOwner(), jobPost -> {
            if (jobPost != null) {
                this.currentJobPost = jobPost;

                txtJobName.setText(jobPost.getTitle());
                txtCompanyName.setText(jobPost.getCompany().getName());
                txtSalary.setText(jobPost.getSalaryStart() + " - " + jobPost.getSalaryEnd() + " " + jobPost.getCurrency());
                txtLocation.setText(jobPost.getLocation());
            }

            // Setup ViewPager2 sau khi co current jobpost
            HomeJobPostPagerAdapter adapter = new HomeJobPostPagerAdapter(this, user, currentJobPost);
            viewPager.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                if (position == 0) tab.setText("Decriptions");
                else tab.setText("Company");
            }).attach();
        });
    }
}
