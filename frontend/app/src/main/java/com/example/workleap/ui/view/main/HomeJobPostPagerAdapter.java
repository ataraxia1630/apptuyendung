package com.example.workleap.ui.view.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;

import kotlinx.coroutines.Job;

public class HomeJobPostPagerAdapter extends FragmentStateAdapter {

    private final User user;

    private final JobPost jobPost;

    public HomeJobPostPagerAdapter(@NonNull Fragment fragment, User user, JobPost jobPost) {
        super(fragment);
        this.user = user;
        this.jobPost = jobPost;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("currentJobPost", jobPost);

        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new DetailCompanyFragment();
                break;
            default:
                fragment = new DetailHomeJobPostFragment();
                break;
        }

        fragment.setArguments(bundle); // truyen du lieu vao fragment trong tab
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
