package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.home.DetailCompanyFragment;
import com.google.android.material.tabs.TabLayout;

public class OverviewMyJobPostFragment extends Fragment {

    private TabLayout tabLayout;
    private FrameLayout fragmentContainer;
    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation;
    private ImageButton btnBack, btnOption;

    private User user;
    private JobPost currentJobPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_my_jobpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");

        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");

        // Bind view
        tabLayout = view.findViewById(R.id.tabLayout);
        fragmentContainer = view.findViewById(R.id.fragmentContainer);

        txtJobName = view.findViewById(R.id.txtJobName);
        txtCompanyName = view.findViewById(R.id.txtCompanyName);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtLocation = view.findViewById(R.id.txtLocation);
        btnBack = view.findViewById(R.id.btnBack);
        btnOption = view.findViewById(R.id.btnOption);

        txtJobName.setText(currentJobPost.getTitle());
        txtCompanyName.setText(currentJobPost.getCompany().getName());
        txtSalary.setText(currentJobPost.getSalaryStart() + " - " + currentJobPost.getSalaryEnd() + " " + currentJobPost.getCurrency());
        txtLocation.setText(currentJobPost.getLocation());

        btnBack.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        // Tab xử lý
        tabLayout.addTab(tabLayout.newTab().setText("Descriptions"));
        tabLayout.addTab(tabLayout.newTab().setText("Company"));

        // Load tab đầu
        loadTabFragment(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                loadTabFragment(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadTabFragment(int position) {
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("currentJobPost", currentJobPost);

        if (position == 1) {
            fragment = new DetailCompanyFragment();
        } else {
            fragment = new DetailMyJobPostFragment();
        }

        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}

