package com.example.workleap.ui.view.main.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;

import java.util.ArrayList;
import java.util.List;

public class ChooseCVFragment extends Fragment {

    private static final String ARG_CV_LIST = "cv_list";
    private List<CV> cvList;

    public static ChooseCVFragment newInstance(ArrayList<CV> cvList) {
        ChooseCVFragment fragment = new ChooseCVFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CV_LIST, cvList);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            cvList = (List<CV>) getArguments().getSerializable(ARG_CV_LIST);
        }

        Toolbar toolbar = view.findViewById(R.id.toolbarChooseCV);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ChooseCVAdapter(cvList, selectedCV -> {
            // tra ket qua ve fragment goi den
            Bundle result = new Bundle();
            result.putSerializable("selected_cv", selectedCV);
            getParentFragmentManager().setFragmentResult("choose_cv_result", result);

            NavHostFragment.findNavController(this).navigateUp();
        }));
    }
}
