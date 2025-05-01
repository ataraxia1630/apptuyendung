package com.example.workleap.ui.view;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RolePagerAdapter extends FragmentStateAdapter {
    public RolePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new ApplicantSignupFragment() : new CompanySignupFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
