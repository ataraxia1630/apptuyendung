package com.example.workleap.ui.view.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.StatisticViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    TextView tvUserCount, tvJobPostCount, tvReportCount, tvApplicationCount, tvUserLabel, tvJobPostLabel, tvReportLabel, tvApplicationLabel;
    PieChart pieChart;

    LineChart lineChart;

    StatisticViewModel statisticViewModel;
    ViewGroup itemUserCount, itemJobPostCount, itemReportCount, itemApplicationCount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pieChartCategory);

        setupPieChart();
        loadPieChartData();

        lineChart = view.findViewById(R.id.lineChartGrowth);
        setupLineChart();

        itemUserCount = view.findViewById(R.id.userCount);
        itemJobPostCount = view.findViewById(R.id.jobPostCount);
        itemReportCount = view.findViewById(R.id.reportCount);
        itemApplicationCount = view.findViewById(R.id.applicationCount);

        tvUserLabel  = itemUserCount.findViewById(R.id.txtLabel);
        tvUserLabel.setText("User");
        tvJobPostLabel  = itemJobPostCount.findViewById(R.id.txtLabel);
        tvJobPostLabel.setText("Job Post");
        tvReportLabel  = itemReportCount.findViewById(R.id.txtLabel);
        tvReportLabel.setText("Report");
        tvApplicationLabel = itemApplicationCount.findViewById(R.id.txtLabel);
        tvApplicationLabel.setText("Application");

        ImageView imgUser = itemUserCount.findViewById(R.id.imgIcon);
        ImageView imgJob = itemJobPostCount.findViewById(R.id.imgIcon);
        ImageView imgReport = itemReportCount.findViewById(R.id.imgIcon);
        ImageView imgApp = itemApplicationCount.findViewById(R.id.imgIcon);

        imgUser.setImageResource(R.drawable.ic_group_users);
        imgJob.setImageResource(R.drawable.job_post_photo);
        imgReport.setImageResource(R.drawable.ic_report);
        imgApp.setImageResource(R.drawable.ic_cv);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statisticViewModel = new ViewModelProvider(requireActivity()).get(StatisticViewModel.class);
        statisticViewModel.InitiateRepository(getContext());

        tvUserCount = itemUserCount.findViewById(R.id.txtCount);
        tvJobPostCount = itemJobPostCount.findViewById(R.id.txtCount);
        tvReportCount = itemReportCount.findViewById(R.id.txtCount);
        tvApplicationCount = itemApplicationCount.findViewById(R.id.txtCount);


        statisticViewModel.getOverview();
        statisticViewModel.getGetOverviewResult().observe(getViewLifecycleOwner(), result -> {
            if(!isAdded() || getView()==null) return;

            if(result!=null)
                Log.e("StatisticsFragment", "getGetOverviewResult " + result);
            else
                Log.e("StatisticsFragment", "getGetOverviewResult result NULL" );

        });
        statisticViewModel.getGetOverviewData().observe(getViewLifecycleOwner(), dailyStatistic -> {
            if(dailyStatistic==null)
            {
                Log.e("StatisticFragment", "getGetOverviewData NULL");
                return;
            }
            tvUserCount.setText(String.valueOf(dailyStatistic.getUserCount()));
            tvJobPostCount.setText(String.valueOf(dailyStatistic.getJobPostCount()));
            tvReportCount.setText(String.valueOf(dailyStatistic.getReportCount()));
            tvApplicationCount.setText(String.valueOf(dailyStatistic.getApplicationCount()));
        });
    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
    }
    private void setupLineChart() {
        // Dữ liệu mẫu (VD: số CV theo từng ngày)
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 5));   // Ngày 1: 5 CV
        entries.add(new Entry(2, 9));   // Ngày 2: 9 CV
        entries.add(new Entry(3, 6));   // Ngày 3: 6 CV

        LineDataSet dataSet = new LineDataSet(entries, "Số lượng CV mỗi ngày");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(12f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Tuỳ chỉnh trục, hiệu ứng
        lineChart.getDescription().setText("Thống kê CV theo ngày");
        lineChart.animateX(1000);
        lineChart.invalidate(); // refresh
    }

    private void loadPieChartData() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Đã duyệt"));
        entries.add(new PieEntry(30f, "Chờ duyệt"));
        entries.add(new PieEntry(30f, "Từ chối"));

        PieDataSet dataSet = new PieDataSet(entries, "Trạng thái hồ sơ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

}