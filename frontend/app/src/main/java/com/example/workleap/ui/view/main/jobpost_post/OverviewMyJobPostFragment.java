package com.example.workleap.ui.view.main.jobpost_post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.home.DetailCompanyFragment;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.example.workleap.utils.ToastUtil;
import com.example.workleap.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class OverviewMyJobPostFragment extends Fragment {
    private TabLayout tabLayout;
    private FrameLayout fragmentContainer;
    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation;
    private ImageButton btnBack, btnOption;
    private ImageView imgAvatar;
    private User user;
    private JobPost currentJobPost;

    private JobPostViewModel jobPostViewModel;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_my_jobpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());

        user = (User) getArguments().getSerializable("user");
        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");
        Toast.makeText(getContext(), "currentJobPost: " + currentJobPost.getStatus(), Toast.LENGTH_SHORT).show();
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
        imgAvatar = view.findViewById(R.id.imgAvatar);

        txtJobName.setText(currentJobPost.getTitle());
        txtCompanyName.setText(currentJobPost.getCompany().getName());
        txtSalary.setText(currentJobPost.getSalaryStart() + " - " + currentJobPost.getSalaryEnd() + " " + currentJobPost.getCurrency());
        txtLocation.setText(currentJobPost.getLocation());

        btnBack.setOnClickListener(v ->
        {
            Toast.makeText(getContext(), "current stt" + currentJobPost.getStatus(), Toast.LENGTH_SHORT).show();
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
        //Lay user de lay avatar
        userViewModel.getUser(currentJobPost.getCompany().getUser().get(0).getId());

        jobPostViewModel.getUpdateJobPostResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null) {
                Log.d("JobPostViewModel", "updateJobPostResult result " + result);
            }
            else
                Log.d("JobPostViewModel", "updateJobPostResult NULL");
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
                else if(item.getItemId() == R.id.menu_status) {
                    String newStatus;
                    if(currentJobPost.getStatus().equalsIgnoreCase("OPENING"))
                        newStatus = "TERMINATED";
                    else
                        newStatus = "OPENING";

                    //Chuyen sang dialog status jobpost
                    new AlertDialog.Builder(this.getContext())
                            .setTitle("Change status of jobpost")
                            .setMessage("Do you want change status to " + newStatus + " ? If the apply-until date has passed, you must renew it to reopen")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Update status
                                    JobPost updatedJobPost = currentJobPost;
                                    updatedJobPost.setStatus(newStatus);
                                    updatedJobPost.setApplyUntil(Utils.formatDate(currentJobPost.getApplyUntil()));
                                    jobPostViewModel.updateJobPost(currentJobPost.getId(), updatedJobPost);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Log.d("after cancel", currentJobPost.getStatus());
                                }
                            })
                            .show();
                    return true;
                }
                else if(item.getItemId() == R.id.menu_delete)
                {
                    //Xoa trong csdl
                    jobPostViewModel.deleteJobPost(currentJobPost.getId());
                    return true;
                }
                else if(item.getItemId() == R.id.menu_approve)
                {
                    jobPostViewModel.toggleJobPostStatus(currentJobPost.getId(), "OPENING");
                    return true;
                }
                else if(item.getItemId() == R.id.menu_reject)
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

