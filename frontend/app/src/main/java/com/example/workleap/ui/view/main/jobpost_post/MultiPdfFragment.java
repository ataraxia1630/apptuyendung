package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.workleap.R;
import com.example.workleap.ui.view.main.cv_appliedjob.PdfFragment;

import java.util.ArrayList;

public class MultiPdfFragment extends Fragment {

    private ArrayList<String> pdfUrls;
    private ArrayList<String> applicantNames;
    private int currentIndex = 0;

    private TextView tvApplicantName, tvPdfIndex;
    private ImageButton btnPrevious, btnNext, btnDismiss, btnApprove, btnBack;

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

        pdfUrls = getArguments().getStringArrayList("pdf_urls");
        applicantNames = getArguments().getStringArrayList("applicant_names");

        tvApplicantName = view.findViewById(R.id.tvApplicantName);
        tvPdfIndex = view.findViewById(R.id.tvPdfIndex);
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

        String url = pdfUrls.get(currentIndex);
        String applicant = applicantNames.get(currentIndex);

        tvApplicantName.setText(applicant);
        tvPdfIndex.setText((currentIndex + 1) + "/" + pdfUrls.size());

        PdfFragment pdfFragment = new PdfFragment();
        Bundle args = new Bundle();
        args.putString("pdf_url", url);
        args.putString("pdf_title", applicant);
        pdfFragment.setArguments(args);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.pdfViewerContainer, pdfFragment);
        transaction.commit();
    }

    private void handleDismiss() {
        // TODO: xử lý logic loại bỏ hồ sơ
    }

    private void handleApprove() {
        // TODO: xử lý logic duyệt hồ sơ
    }
}
