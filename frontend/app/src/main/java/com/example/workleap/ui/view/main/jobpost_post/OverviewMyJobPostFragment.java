package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.home.DetailCompanyFragment;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class OverviewMyJobPostFragment extends Fragment {
    private TabLayout tabLayout;
    private FrameLayout fragmentContainer;
    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation;
    private ImageButton btnBack, btnOption;

    private User user;
    private JobPost currentJobPost;

    private JobPostViewModel jobPostViewModel;

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

        jobPostViewModel = new JobPostViewModel();
        jobPostViewModel.InitiateRepository(getContext());

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

        btnBack.setOnClickListener(v ->
        {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        jobPostViewModel.getDeleteJobPostResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
            {
                Log.d("JobPostViewModel", "result: " + result);
                //Quay ve
                ((NavigationActivity) getActivity()).showBottomNav(true);
                NavHostFragment.findNavController(this).navigateUp();
            }
            else
                Log.d("JobPostViewModel", "result: null");
        });
        jobPostViewModel.toggleJobPostResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
            {
                Log.d("JobPostViewModel", "toggleJobPostResult result " + result);
                //Quay ve
                ((NavigationActivity) getActivity()).showBottomNav(true);
                NavHostFragment.findNavController(this).navigateUp();
            }
            else
                Log.d("JobPostViewModel", "toggleJobPostResult NULL");
        });

        //Delete or edit
        btnOption.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), btnOption);
            // Load menu từ file XML
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                popupMenu.inflate(R.menu.menu_options_adminjobpost);
            } else {
                popupMenu.inflate(R.menu.menu_options_myjobpost);
            }

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.menu_edit) {
                    //Chuyen sang fragment edit jobpost
                    NavController nav = NavHostFragment.findNavController(this);
                    nav.navigate(R.id.updateJobPostFragment, getArguments());
                    return true;
                }
                else if(item.getItemId() == R.id.menu_delete)
                {
                    //Xoa trong csdl
                    jobPostViewModel.deleteJobPost(currentJobPost.getId());
                    return true;
                }
                else if(item.getItemId() == R.id.menu_open)
                {
                    jobPostViewModel.toggleJobPostStatus(currentJobPost.getId(), "OPENING");
                    return true;
                }
                else if(item.getItemId() == R.id.menu_cancel)
                {
                    jobPostViewModel.toggleJobPostStatus(currentJobPost.getId(), "CANCELLED");
                    return true;
                }
                else
                    return false;
            });
            popupMenu.show();
        });

        // Tab xử lý
        tabLayout.addTab(tabLayout.newTab().setText("Descriptions"));
        tabLayout.addTab(tabLayout.newTab().setText("Candidates"));

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
            fragment = new SubmittedProfilesFragment();
        } else {
            fragment = new DetailMyJobPostFragment();
        }

        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}

