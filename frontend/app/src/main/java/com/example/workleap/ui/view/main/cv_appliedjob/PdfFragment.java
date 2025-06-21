package com.example.workleap.ui.view.main.cv_appliedjob;

import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.workleap.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfFragment extends Fragment {
    private String pdfUrl;
    private ImageButton btnBack;

    private TextView tvPdfTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfUrl = getArguments() != null ? getArguments().getString("pdf_url") : "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);
        new Thread(() -> {
            try {
                File pdfFile = downloadPdfFromUrl(pdfUrl);
                getActivity().runOnUiThread(() -> renderPdf(pdfFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.back_button);
        tvPdfTitle = view.findViewById(R.id.pdf_title);

        btnBack.setOnClickListener( v ->requireActivity().getOnBackPressedDispatcher().onBackPressed());
        tvPdfTitle.setText(getArguments().getString("pdf_title"));
    }

    private File downloadPdfFromUrl(String url) throws IOException {
        URL pdfUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) pdfUrl.openConnection();
        File tempFile = new File(getActivity().getCacheDir(), "temp.pdf");
        InputStream input = conn.getInputStream();
        FileOutputStream output = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.close();
        input.close();
        return tempFile;
    }
    private void renderPdf(File pdfFile) {
        try {
            ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer renderer = new PdfRenderer(descriptor);

            RecyclerView recyclerView = getView().findViewById(R.id.pdfRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new PdfPageAdapter(renderer));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        File tempFile = new File(getActivity().getCacheDir(), "temp.pdf");
        if (tempFile.exists()) tempFile.delete();
    }
    public void hideToolbar() {
        if (getView() != null) {
            View toolbar = getView().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setVisibility(View.GONE);
            }
        }
    }
}
