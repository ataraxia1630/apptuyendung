package com.example.workleap.ui.view.main.cv_appliedjob;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.workleap.data.model.entity.User;

public class CVPagerAdapter extends FragmentStateAdapter {
    private User user;
    public CVPagerAdapter(Fragment fragment, User user) {
        super(fragment);
        this.user = user;
    }

    @Override
    public Fragment createFragment(int position) {
        MyCVFragment myCVFragment = new MyCVFragment();
        AppliedJobFragment appliedJobFragment = new AppliedJobFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);

        myCVFragment.setArguments(args);
        appliedJobFragment.setArguments(args);

        return position == 0 ? myCVFragment : appliedJobFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
