package com.example.workleap.ui.view.main;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CVPagerAdapter extends FragmentStateAdapter {
    public CVPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new MyCVFragment() : new AppliedJobFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
