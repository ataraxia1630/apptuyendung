package com.example.workleap.ui.view.main.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Report;
import com.example.workleap.ui.view.main.admin.ReportAdapter;
import com.example.workleap.ui.viewmodel.ReportViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    private RecyclerView recyclerViewReports;
    private TextView tvTitleReports, tvNoReports;
    private ReportAdapter reportAdapter;
    private List<Report> reportList = new ArrayList<>();
    private ReportViewModel reportViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitleReports = view.findViewById(R.id.tvTitleReports);
        tvNoReports = view.findViewById(R.id.tvNoReports);
        recyclerViewReports = view.findViewById(R.id.recyclerViewReports);

        /*// recycleview
        reportAdapter = new ReportAdapter(reportList);
        recyclerViewReports.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewReports.setAdapter(reportAdapter);

        // ViewModel
        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
        reportViewModel.InitiateRepository(getContext());

        // Quan sát dữ liệu từ ViewModel
        reportViewModel.getAllReports().observe(getViewLifecycleOwner(), reports -> {
            reportList.clear();
            if (reports != null && !reports.isEmpty()) {
                reportList.addAll(reports);
                tvNoReports.setVisibility(View.GONE);
            } else {
                tvNoReports.setVisibility(View.VISIBLE);
            }
            reportAdapter.notifyDataSetChanged();
        });
        reportViewModel.fetchAllReports();*/

    }
}
