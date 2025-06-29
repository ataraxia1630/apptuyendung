package com.example.workleap.ui.view.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.ReportViewModel;
import com.example.workleap.ui.viewmodel.StatisticViewModel;

import java.util.Locale;

public class SendReportFragment extends Fragment {

    private TextView tvReportedTarget;
    private EditText edtReportReason;
    private Button btnSubmitReport;
    private ImageButton btnBack;

    private String reportedTargetId;
    private String reportType;

    private String targetName;
    private ReportViewModel reportViewModel;

    public SendReportFragment() {
        // Required empty public constructor
    }

    public static SendReportFragment newInstance(String type, String targetId, String targetName) {
        SendReportFragment fragment = new SendReportFragment();
        Bundle args = new Bundle();
        args.putString("type", type);   //user, jobpost, post
        args.putString("targetId", targetId);
        args.putString("name", targetName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
        reportViewModel.initiateRepository(getContext());

        tvReportedTarget = view.findViewById(R.id.tvReportedTarget);
        edtReportReason = view.findViewById(R.id.edtReportReason);
        btnSubmitReport = view.findViewById(R.id.btnSubmitReport);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v ->
        {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        if (getArguments() != null) {
            reportType = getArguments().getString("type");
            reportedTargetId = getArguments().getString("targetId");
            targetName = getArguments().getString("targetName");

            // Display the target based on type
            String type = reportType.toUpperCase();
            switch (type) {
                case "POST":
                    tvReportedTarget.setText("Post: " + targetName);
                    break;
                case "JOBPOST":
                    tvReportedTarget.setText("Job Post: " + targetName);
                    break;
                default:
                    tvReportedTarget.setText("User: " + targetName);
                    break;
            }
        }

        btnSubmitReport.setOnClickListener(v -> {
            String reason = edtReportReason.getText().toString().trim();

            if (TextUtils.isEmpty(reason)) {
                Toast.makeText(getContext(), "Please enter a reason", Toast.LENGTH_SHORT).show();
                return;
            }
            if (reason.length() > 100) {
                Toast.makeText(getContext(), "Reason is too long. Please keep it under 100 characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            if("user".equalsIgnoreCase(reportType))
            {
                reportViewModel.createReportUser(reason, reportedTargetId);
            }
            else if ("jobpost".equalsIgnoreCase(reportType))
            {
                reportViewModel.createReportJobPost(reason, reportedTargetId);
            }
            else if ("post".equalsIgnoreCase(reportType))
            {
                reportViewModel.createReportPost(reason, reportedTargetId);
            }
            else {
                Toast.makeText(getContext(), "  Failed to send the report. Please try again.", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getContext(), "Report submitted successfully", Toast.LENGTH_LONG).show();

            // Optionally: Clear the form
            edtReportReason.setText("");
        });
    }
}
