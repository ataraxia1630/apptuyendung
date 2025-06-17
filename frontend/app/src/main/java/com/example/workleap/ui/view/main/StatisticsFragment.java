package com.example.workleap.ui.view.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workleap.R;
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

    PieChart pieChart;

    LineChart lineChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pieChart);

        setupPieChart();
        loadPieChartData();

        lineChart = view.findViewById(R.id.lineChart);
        setupLineChart();

        return view;
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