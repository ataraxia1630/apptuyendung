package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.CVViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyCVFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyCVAdapter adapter;
    private List<CV> allCVs = new ArrayList<>();
    private CVViewModel cvViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerCvs); // ID trong layout
        cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);


        cvViewModel.getAllCvResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = String.valueOf(result);
            Log.e("MyCVFragment", "getAllCvResult: " + s);
        });
        cvViewModel.getAllCvData().observe(getViewLifecycleOwner(), CVs ->
        {
            if(CVs==null)
            {
                Log.e("CVFragment", "getAllCvData NULL");
                return;
            }
            allCVs.addAll(CVs);
            // Setup RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MyCVAdapter(allCVs); // mặc định show tất cả
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }
}