package com.example.workleap.ui.view.main.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Report;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.JobSavedRequest;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.jobpost_post.JobPostAdapter;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.ReportViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    private RecyclerView recyclerViewReports;
    private TextView tvTitleReports, tvNoReports;
    private ReportAdapter reportAdapter;
    private List<Report> reportList = new ArrayList<>();
    private ReportViewModel reportViewModel;

    private UserViewModel userViewModel;
    private JobPostViewModel jobPostViewModel;
    private NavController navController;
    private User user;

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

        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());
        jobPostViewModel  = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        user = (User) getArguments().getSerializable("user");

        navController = NavHostFragment.findNavController(this);

        // recycleview
        reportAdapter = new ReportAdapter(requireContext(), reportList, new ReportAdapter.OnReportActionListener() {
            @Override
            public void onViewTarget(Report report) {
                // Xem đối tượng bị báo cáo (User/Post/JobPost)
                Toast.makeText(getContext(), "View target: " + report.getId(), Toast.LENGTH_SHORT).show();
                if(report.getReportedUserId() != null)
                {
                    userViewModel.getUser(report.getReportedUserId());
                }
                else if(report.getJobPostId() != null)
                {
                    Log.e("eee","jobjobjob");
                    jobPostViewModel.getJobPostById(report.getJobPostId());
                }
                else if(report.getPostId() != null)
                {
                    Log.e("eee","popopo");
                }
                else Log.e("eee","uauaa");
            }
            public void onViewReporter(Report report) {
                // Xem nguoi bao cao (User/Post/JobPost)
                Toast.makeText(getContext(), "View target: " + report.getId(), Toast.LENGTH_SHORT).show();
                if(report.getUserId() != null)
                {
                    userViewModel.getUser(report.getUserId());
                }
                else {
                    Toast.makeText(getContext(), "Unknown sender" + report.getId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onApprove(Report report) {
                // Xử lý duyệt báo cáo
                Toast.makeText(getContext(), "Reply report", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReject(Report report) {
                // Xử lý từ chối báo cáo
                Toast.makeText(getContext(), "Rejected report", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewReports.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewReports.setAdapter(reportAdapter);

        // ViewModel
        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
        reportViewModel.initiateRepository(getContext());

        // Quan sát dữ liệu từ ViewModel
        reportViewModel.getGetReportData().observe(getViewLifecycleOwner(), reports -> {
            reportList.clear();
            if (reports != null && !reports.isEmpty()) {
                reportList.addAll(reports);
                tvNoReports.setVisibility(View.GONE);
            } else {
                tvNoReports.setVisibility(View.VISIBLE);
            }
            reportAdapter.notifyDataSetChanged();
        });
        reportViewModel.getAllReports();

        jobPostViewModel.getJobPostData().observe(getViewLifecycleOwner(), jobPost -> {
            Log.e("iii", "ysus");
            if(jobPost==null)
            {
                Log.e("ReportFragment","getMyJobPostByIdData NULL");
                return;
            }
            // Handle item click
            Bundle bundle = new Bundle();
            jobPostViewModel.setCurrentJobPost(jobPost);
            bundle.putSerializable("jobPost", jobPost);
            bundle.putSerializable("user", user);

            //tranh viec quay lai thi lai mo jobpost
            jobPostViewModel.resetJobPostData();
            ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
            navController.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment
        });
        jobPostViewModel.getJobPostResult().observe(getViewLifecycleOwner(), result->{
            Log.e("resultlll", result);
        });

        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), data -> {
            Log.e("eee","observe userdata");
            if(data != null)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", data);
                bundle.putSerializable("userId", data.getId());
                bundle.putSerializable("myUser", user);

                //Check company or applicant
                if(data.getCompanyId() == null)
                {
                    navController.navigate(R.id.watchApplicantProfileFragment, bundle);
                    Log.e("eee","wwatch");
                }
                else
                {
                    bundle.putString("companyId", data.getCompanyId());
                    Log.d("cmt bottsh", data.getId());
                    navController.navigate(R.id.watchCompanyProfileFragment, bundle);
                }
            }
            else
                Log.d("ReportFragment", "getReportedUserId NULL");
        });
    }
}
