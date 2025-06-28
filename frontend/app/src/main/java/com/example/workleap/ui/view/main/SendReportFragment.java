package com.example.workleap.ui.view.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workleap.R;

public class SendReportFragment extends Fragment {

    private TextView tvReportedTarget;
    private EditText edtReportReason;
    private Button btnSubmitReport;

    private String reportedTargetId;
    private String reportType;

    private String targetName;

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

        tvReportedTarget = view.findViewById(R.id.tvReportedTarget);
        edtReportReason = view.findViewById(R.id.edtReportReason);
        btnSubmitReport = view.findViewById(R.id.btnSubmitReport);

        if (getArguments() != null) {
            reportType = getArguments().getString("type", "USER");
            reportedTargetId = getArguments().getString("targetId");
            targetName = getArguments().getString("targetName");

            // Display the target based on type
            switch (reportType) {
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

            // send report to backend
            Log.d("ReportFragment", "Reported " + reportType + " ID: " + reportedTargetId);
            Log.d("ReportFragment", "Reason: " + reason);

            Toast.makeText(getContext(), "Report submitted successfully", Toast.LENGTH_LONG).show();

            // Optionally: Clear the form
            edtReportReason.setText("");
        });
    }
}
