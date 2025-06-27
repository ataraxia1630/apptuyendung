package com.example.workleap.ui.view.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobSaved;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.data.model.response.FieldStat;
import com.example.workleap.data.model.response.MonthlyStat;
import com.example.workleap.data.model.response.TopJobPostResponse;
import com.example.workleap.ui.view.main.jobpost_post.JobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.JobpostFragment;
import com.example.workleap.ui.view.main.jobpost_post.MyJobPostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.StatisticViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsFragment extends Fragment {
    TextView tvUserCount, tvJobPostCount, tvReportCount, tvApplicationCount, tvUserLabel, tvJobPostLabel, tvReportLabel, tvApplicationLabel;
    PieChart pieChart;

    RecyclerView recyclerTopCompanies, recyclerTopJobs;

    LineChart lineChart;

    StatisticViewModel statisticViewModel;
    ViewGroup itemUserCount, itemJobPostCount, itemReportCount, itemApplicationCount;

    private JobPostAdapter adapterTopJobPosts;
    List<Entry> userEntries, jobEntries;
    List<String> monthLabels;

    List<MonthlyStat> userGrowth, jobGrowth;
    List<Integer> colors;

    private Bundle bundle;

    private User user; //myUser
    private NavController nav;

    private JobPostViewModel jobPostViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pieChartCategory);

        //setupPieChart();
        //loadPieChartData();

        lineChart = view.findViewById(R.id.lineChartGrowth);

        itemUserCount = view.findViewById(R.id.userCount);
        itemJobPostCount = view.findViewById(R.id.jobPostCount);
        itemReportCount = view.findViewById(R.id.companyCount);
        itemApplicationCount = view.findViewById(R.id.applicationCount);
        recyclerTopCompanies = view.findViewById(R.id.recyclerTopCompanies);
        recyclerTopJobs = view.findViewById(R.id.recyclerTopJobs);

        tvUserLabel  = itemUserCount.findViewById(R.id.txtLabel);
        tvUserLabel.setText("User");
        tvJobPostLabel  = itemJobPostCount.findViewById(R.id.txtLabel);
        tvJobPostLabel.setText("Job Post");
        tvReportLabel  = itemReportCount.findViewById(R.id.txtLabel);
        tvReportLabel.setText("Company");
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

        userEntries = new ArrayList<>();
        jobEntries = new ArrayList<>();
        monthLabels = new ArrayList<>();

        //tao mang nhieu mau hon 4 mau co ban cho pie chart
        colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }

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

        user = (User) getArguments().getSerializable("user");
        nav = NavHostFragment.findNavController(this);
        jobPostViewModel  = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);

        //over view
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
            tvReportCount.setText(String.valueOf(dailyStatistic.getCompanyCount()));
            tvApplicationCount.setText(String.valueOf(dailyStatistic.getApplicationCount()));
        });


        //top company
        statisticViewModel.getTopCompany(1, 5);
        statisticViewModel.getTopCompanyResult().observe(getViewLifecycleOwner(), result ->{
            if(!isAdded() || getView()==null) return;

            if(result!=null)
                Log.e("StatisticFragment", "getTopCompanyResult " + result);
            else
                Log.e("StatisticFragment", "getTopCompanyResult result NULL" );
        });

        TopCompanyAdapter adapter = new TopCompanyAdapter(requireContext(), new ArrayList<>());
        recyclerTopCompanies.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTopCompanies.setAdapter(adapter);
        statisticViewModel.getTopCompanyData().observe(getViewLifecycleOwner(), topCompanyList -> {
            if (topCompanyList != null) {
                adapter.setData(topCompanyList);
            }
            else
            {
                Log.e("StatisticFragment", "getTopCompanyData topCompanyList NULL");
            }
        });

        //top jobpost
        statisticViewModel.getTopJobPost(1, 4);
        statisticViewModel.getTopJobPostResult().observe(getViewLifecycleOwner(), result->{
            if(!isAdded() || getView()==null) return;

            if(result!=null)
                Log.e("StatisticFragment", "getTopJobPostResult " + result);
            else
                Log.e("StatisticFragment", "getTopJobPostResult result NULL" );
        });

        adapterTopJobPosts = new JobPostAdapter(new ArrayList<>(), new JobPostAdapter.OnJobPostClickListener() {
            @Override
            public void onJobPostClick(JobPost jobPost) {
                // Handle item click
                bundle = new Bundle();
                bundle.putSerializable("user", user);
                //homejobpostfragment observe currentjobpost
                jobPostViewModel.setCurrentJobPost(jobPost);
                ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                nav.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment
            }

            @Override
            public void onSaveClick(JobPost jobpost) {
                jobPostViewModel.createJobSavedResult().observe(getViewLifecycleOwner(), result -> {
                    if(result != null)
                        Log.e("HomeFragment", "createJobSavedResult: " + result + "");
                    else
                        Log.e("HomeFragment", "createJobSavedResult: null");
                });
                if(user.getApplicantId() != null)
                {
                    JobSavedRequest jobSave = new JobSavedRequest(user.getApplicantId(), jobpost.getId());
                    jobPostViewModel.createJobSaved(jobSave);
                }
                return;
            }

            @Override
            public void onReportClick(JobPost jobpost) {
                return;
            }
        });
        recyclerTopJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTopJobs.setAdapter(adapterTopJobPosts);
        statisticViewModel.getTopJobPostData().observe(getViewLifecycleOwner(), topJobPostList->{
            if (topJobPostList != null) {
                //lay danh sach jobpost tu danh sach topjobpost
                List<JobPost> jobPosts = new ArrayList<>();
                for (TopJobPostResponse item : topJobPostList) {
                    if (item.getJob() != null && item.getJob().getCompany() != null) {
                        jobPosts.add(item.getJob());
                    }
                }
                adapterTopJobPosts.setData(jobPosts);
            } else {
                Log.e("StatisticFragment", "getTopJobPostData jobPostList NULL");
            }
        });

        //monthly growth
        statisticViewModel.getMonthlyGrowth();
        statisticViewModel.getListMonthlyStatResult().observe(getViewLifecycleOwner(), result->{
            if(!isAdded() || getView()==null) return;

            if(result!=null)
                Log.e("StatisticFragment", "getListMonthlyStatResult " + result);
            else
                Log.e("StatisticFragment", "getListMonthlyStatResult result NULL" );
        });
        statisticViewModel.getListMonthlyStatData().observe(getViewLifecycleOwner(), listMonthlyStat->{
            userEntries.clear();
            jobEntries.clear();
            monthLabels.clear();

            userGrowth = listMonthlyStat.getUserGrowth();
            jobGrowth = listMonthlyStat.getJobGrowth();

            if (listMonthlyStat.getUserGrowth().isEmpty() && listMonthlyStat.getJobGrowth().isEmpty()) {
                Log.e("StatisticFragment", "LineChart khong co du lieu hien thi");
                return;
            }

            // duyet qua userGrowth va gan vi tri x bang index
            for (int i = 0; i < userGrowth.size(); i++) {
                MonthlyStat stat = userGrowth.get(i);
                userEntries.add(new Entry(i, stat.getCount()));
                monthLabels.add(stat.getMonth()); // lam label truc x
            }

            // jobGrowth can map dung thang voi chi so x tuong ung
            for (MonthlyStat stat : jobGrowth) {
                int index = monthLabels.indexOf(stat.getMonth());
                if (index != -1) {
                    jobEntries.add(new Entry(index, stat.getCount()));
                }
            }

            setupLineChart();
        });


        //by field
        statisticViewModel.getByField(1, 10);
        statisticViewModel.getListFieldStatData().observe(getViewLifecycleOwner(), listFieldStat->{
            if (listFieldStat == null || listFieldStat.isEmpty()) return;

            List<PieEntry> entries = new ArrayList<>();

            for (FieldStat stat : listFieldStat) {
                entries.add(new PieEntry(stat.getCount(), stat.getName()));
            }

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            PieData pieData = new PieData(dataSet);
            pieData.setValueTextSize(14f);
            pieData.setValueTextColor(Color.BLACK);

            Legend legend = pieChart.getLegend();
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false); // khong ve ben trong, ve ngoai bieu do
            legend.setWordWrapEnabled(true); // cho phep xuong dong

            pieChart.setData(pieData);
            pieChart.setUsePercentValues(true); // nếu muốn dùng %
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleAlpha(110);
            pieChart.animateY(1000);
            pieChart.invalidate(); // refresh
        });

    }

    private void setupLineChart() {
        LineDataSet userDataSet = new LineDataSet(userEntries, "User Growth");
        userDataSet.setColor(Color.BLUE);
        userDataSet.setCircleColor(Color.BLUE);
        userDataSet.setLineWidth(2f);

        LineDataSet jobDataSet = new LineDataSet(jobEntries, "Job Growth");
        jobDataSet.setColor(Color.GREEN);
        jobDataSet.setCircleColor(Color.GREEN);
        jobDataSet.setLineWidth(2f);

        LineData lineData = new LineData(userDataSet, jobDataSet);
        lineChart.setData(lineData);

        // cai dat nhan truc x bang thang
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthLabels));

        lineChart.getDescription().setText("Monthly Growth");
        lineChart.animateX(1000);
        lineChart.invalidate(); // refresh chart

    }

}