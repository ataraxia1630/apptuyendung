package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CVFragment extends Fragment {

    CVViewModel cvViewModel;
    User user;

    public CVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cv, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);
        cvViewModel.InitiateRepository(getContext());

        user = (User) getArguments().getSerializable("user");
        cvViewModel.getAllCv(user.getApplicantId());

        ViewPager2 viewPager = view.findViewById(R.id.cv_viewpager);
        TabLayout tabLayout = view.findViewById(R.id.cv_tablayout);

        CVPagerAdapter adapter = new CVPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "My CV" : "Applied Job")
        ).attach();
    }
}