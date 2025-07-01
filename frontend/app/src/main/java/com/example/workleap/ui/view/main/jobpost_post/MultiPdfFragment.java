package com.example.workleap.ui.view.main.jobpost_post;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.view.main.cv_appliedjob.PdfFragment;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class MultiPdfFragment extends Fragment {

    private ArrayList<String> pdfUrls;
    private ArrayList<String> applicantNames;
    private int currentIndex = 0;

    private TextView tvApplicantName, tvPdfIndex, tvStatus;
    private ImageButton btnPrevious, btnNext, btnDismiss, btnApprove, btnBack;

    private ArrayList<JobApplied> jobApplieds;

    private JobApplied currentJobApplied;

    private JobPostViewModel jobPostViewModel;

    private String urlSupabase = "https://uoqlxeqtvtwacknpjpsn.supabase.co/storage/v1/object/public/cv-storage/";


    public static MultiPdfFragment newInstance(ArrayList<String> pdfUrls, ArrayList<String> applicantNames) {
        MultiPdfFragment fragment = new MultiPdfFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("pdf_urls", pdfUrls);
        args.putStringArrayList("applicant_names", applicantNames);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi_pdf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobApplieds = (ArrayList<JobApplied>) getArguments().getSerializable("jobApplieds");
        pdfUrls = new ArrayList<>();
        applicantNames = new ArrayList<>();

        for (JobApplied item : jobApplieds) {
            pdfUrls.add(item.getCV().getFilePath());
            applicantNames.add(item.getApplicant().getFirstName() +" "+ item.getApplicant().getLastName());
        }

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.getProcessCvAppliedData().observe(getViewLifecycleOwner(), jobApplied -> {
            if(jobApplied==null)
            {
                Log.e("multipdf", "getProcessCvAppliedData NULL");
                //Toast.makeText(getContext(), "An error occurred while updating the CV evaluation result. Please try again later.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "CV review updated successfully.", Toast.LENGTH_SHORT).show();
                tvStatus.setText(jobApplied.getStatus());
                //cap nhat cho jobapplied trong danh sach
                jobApplieds.get(currentIndex).setStatus(jobApplied.getStatus());
                SetColorForStatus();

                jobPostViewModel.ResetGetProcessCVAppliedData();
            }
        });

        tvApplicantName = view.findViewById(R.id.tvApplicantName);
        tvPdfIndex = view.findViewById(R.id.tvPdfIndex);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnBack = view.findViewById(R.id.btnBack);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnDismiss = view.findViewById(R.id.btnDismiss);
        btnApprove = view.findViewById(R.id.btnApprove);

        btnBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        btnPrevious.setOnClickListener(v -> showPdf(currentIndex - 1));
        btnNext.setOnClickListener(v -> showPdf(currentIndex + 1));
        btnDismiss.setOnClickListener(v -> handleDismiss());
        btnApprove.setOnClickListener(v -> handleApprove());

        showPdf(currentIndex);
    }

    private void showPdf(int index) {
        if (pdfUrls == null || index < 0 || index >= pdfUrls.size()) return;
        currentIndex = index;

        currentJobApplied = jobApplieds.get(index);
        String url = pdfUrls.get(currentIndex);
        String applicant = applicantNames.get(currentIndex);

        tvApplicantName.setText(applicant);
        tvPdfIndex.setText((currentIndex + 1) + "/" + pdfUrls.size());
        tvStatus.setText(currentJobApplied.getStatus());
        SetColorForStatus();

        PdfFragment pdfFragment = new PdfFragment();
        Bundle args = new Bundle();
        args.putString("pdf_url", urlSupabase + url);
        args.putString("pdf_title", applicant);
        args.putBoolean("hide_toolbar", true);
        pdfFragment.setArguments(args);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.pdfViewerContainer, pdfFragment);
        transaction.commit();
    }

    private void handleDismiss() {
        jobPostViewModel.processCvApplied(currentJobApplied.getJobpostId(), currentJobApplied.getApplicantId(), "FAILURE");
    }

    private void handleApprove() {
        jobPostViewModel.processCvApplied(currentJobApplied.getJobpostId(), currentJobApplied.getApplicantId(), "SUCCESS");
    }

    private void SetColorForStatus()
    {
        if(String.valueOf(tvStatus.getText()).equalsIgnoreCase("SUCCESS"))
            tvStatus.setTextColor(Color.GREEN);
        if(String.valueOf(tvStatus.getText()).equalsIgnoreCase("FAILURE"))
            tvStatus.setTextColor(Color.RED);
        if(String.valueOf(tvStatus.getText()).equalsIgnoreCase("PENDING"))
            tvStatus.setTextColor(Color.GRAY);
    }
}

