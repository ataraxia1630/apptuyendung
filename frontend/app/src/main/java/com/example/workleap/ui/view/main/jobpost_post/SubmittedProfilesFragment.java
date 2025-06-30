package com.example.workleap.ui.view.main.jobpost_post;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.view.main.cv_appliedjob.PdfFragment;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SubmittedProfilesFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;
    ArrayList<JobApplied> submittedProfiles;
    ArrayList<JobApplied> currentSubmittedProfiles;
    private JobPost currentJobPost;
    private TextView tvProfileCount, tvExport, tvStartReviewing;
    private EditText etSearch;
    private ImageButton btnFilter;
    private RecyclerView rvSubmittedCVs;

    private CV downloadingCV;
    private ActivityResultLauncher<Intent> createFileLauncher;
    private Uri zipOutputUri;
    private ActivityResultLauncher<Intent> zipFileLauncher;

    private String urlSupabase = "https://uoqlxeqtvtwacknpjpsn.supabase.co/storage/v1/object/public/cv-storage/";

    SubmittedProfilesAdapter adapter;

    String currentStatus = "all"; //all, success, failure, pending

    public SubmittedProfilesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_submitted_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProfileCount = view.findViewById(R.id.tvProfileCount);
        etSearch = view.findViewById(R.id.etSearch);
        tvExport = view.findViewById(R.id.tvExport);
        tvStartReviewing = view.findViewById(R.id.tvStartReviewing);
        btnFilter = view.findViewById(R.id.btnFilter);
        rvSubmittedCVs = view.findViewById(R.id.rvSubmittedCVs);

        currentSubmittedProfiles = new ArrayList<>();

        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.getMyJobPostById(currentJobPost.getId());
        jobPostViewModel.getMyJobPostByIdData().observe(getViewLifecycleOwner(), jobpost -> {
            if(jobpost==null)
            {
                Log.e("SubProfileFragment", "getMyJobPostByIdData NULL");
                return;
            }


            this.submittedProfiles = jobpost.getJobApplied();

            if(submittedProfiles==null)
            {
                Log.e("SubmitProfileFragment", "submittedProfiles NULL");
                return;
            }
            else
            {
                adapter = new SubmittedProfilesAdapter(
                        submittedProfiles,
                        new SubmittedProfilesAdapter.OnSubmittedProfilesMenuClickListener() {
                            @Override
                            public void onOpen(JobApplied jobApplied) {
                                PdfFragment pdfFragment = new PdfFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("pdf_title", jobApplied.getApplicant().getFirstName()+jobApplied.getApplicant().getLastName());
                                bundle.putString("pdf_url", urlSupabase + jobApplied.getCV().getFilePath());
                                pdfFragment.setArguments(bundle);

                                requireActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fullscreenFragmentContainer, pdfFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }

                            @Override
                            public void onDownload(JobApplied jobApplied) {
                                downloadingCV = jobApplied.getCV();
                                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                                intent.setType("application/pdf");
                                intent.putExtra(Intent.EXTRA_TITLE, jobApplied.getCV().getTitle());
                                createFileLauncher.launch(intent);
                            }

                            @Override
                            public void onApprove(JobApplied jobApplied) {
                                jobPostViewModel.processCvApplied(jobApplied.getJobpostId(), jobApplied.getApplicantId(), "SUCCESS");
                            }

                            @Override
                            public void onDismiss(JobApplied jobApplied) {
                                jobPostViewModel.processCvApplied(jobApplied.getJobpostId(), jobApplied.getApplicantId(), "FAILURE");
                            }
                        }
                );

                rvSubmittedCVs.setLayoutManager(new LinearLayoutManager(requireContext()));
                rvSubmittedCVs.setAdapter(adapter);

                tvProfileCount.setText(String.valueOf(submittedProfiles.size()));
                Log.e("SubmitProfileFragment", "submitted profiles NOT null");

                //giu lai filter neu co cap nhat status cho cv
                switch (currentStatus.toLowerCase()) {
                    case "all":
                        adapter.updateList(submittedProfiles);
                        currentSubmittedProfiles.clear();
                        currentSubmittedProfiles.addAll(submittedProfiles);
                        break;
                    case "pending":
                        updateAdapterList("PENDING");
                        break;
                    case "success":
                        updateAdapterList("SUCCESS");
                        break;
                    case "failure":
                        updateAdapterList("FAILURE");
                        break;
                    default:
                        adapter.updateList(submittedProfiles);
                        currentSubmittedProfiles.clear();
                        currentSubmittedProfiles.addAll(submittedProfiles);
                        break;
                }
            }


        });
        jobPostViewModel.getProcessCvAppliedResult().observe(getViewLifecycleOwner(), result ->{
            if(!isAdded() || getView()==null) return;

            //reload
            jobPostViewModel.getMyJobPostById(currentJobPost.getId());

            if(result!=null)
                Log.e("MyCVFragment", "updateCvByIdResult " + result);
            else
                Log.e("MyCVFragment", "updateCvByIdResult result NULL" );

        });


        btnFilter.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), btnFilter);
            popup.getMenuInflater().inflate(R.menu.menu_submitted_cv_filter, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.cv_all) {
                    currentStatus = "all";
                    adapter.updateList(submittedProfiles);
                    adapter.notifyDataSetChanged();
                    return true;
                } else if (id == R.id.cv_pending) {
                    currentStatus="pending";
                    updateAdapterList("PENDING");
                    return true;
                } else if (id == R.id.cv_success) {
                    currentStatus = "success";
                    updateAdapterList("SUCCESS");
                    return true;
                } else if (id == R.id.cv_failed) {
                    currentStatus = "failure";
                    updateAdapterList("FAILURE");
                    return true;
                } else {
                    return false;
                }
            });

            popup.show();
        });

        tvStartReviewing.setOnClickListener(v -> {
            if(currentSubmittedProfiles==null)
            {
                Toast.makeText(this.getActivity(), "No profiles have been submitted yet.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(currentSubmittedProfiles.size()==0)
            {
                Toast.makeText(this.getActivity(), "No profiles have been submitted yet.", Toast.LENGTH_SHORT).show();
                return;
            }
            MultiPdfFragment multiPdfFragment = new MultiPdfFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("jobApplieds", currentSubmittedProfiles);
            multiPdfFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fullscreenFragmentContainer, multiPdfFragment)
                    .addToBackStack(null)
                    .commit();
        });

        tvExport.setOnClickListener(v -> {
            if (currentSubmittedProfiles == null || currentSubmittedProfiles.isEmpty()) {
                Toast.makeText(getContext(), "No profiles to export.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.setType("application/zip");
            intent.putExtra(Intent.EXTRA_TITLE, "exported_cvs.zip");
            zipFileLauncher.launch(intent);
        });

        zipFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        zipOutputUri = result.getData().getData();
                        exportAllCVsToZip(zipOutputUri); // gọi sau khi người dùng chọn file
                    }
                }
        );
        createFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        downloadFileToUri(uri, downloadingCV);
                    }
                }
        );
    }
    private void downloadFileToUri(Uri uri, CV cv) {
        //notification
        String channelId = "download_channel";
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());

        // Tạo Notification Channel (chỉ cần tạo 1 lần)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Download Progress",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager.createNotificationChannel(channel);
        }

        int notificationId = (int) System.currentTimeMillis(); // ID duy nhất
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.ic_download) // Icon của bạn
                .setContentTitle("Downloading: " + cv.getTitle())
                .setContentText("0%")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(notificationId, builder.build());
            }
            else
            {
                Log.e("MyCVFragment", "khong co quyen thong bao");
            }
        } else {
            // Android dưới 13 không yêu cầu quyền này
            Log.e("MyCVFragment", "co quyen thong bao");

            notificationManager.notify(notificationId, builder.build());
        }

        new Thread(() -> {
            try {
                URL url = new URL(urlSupabase + cv.getFilePath());
                URLConnection connection = url.openConnection();
                int fileLength = connection.getContentLength(); // Tổng dung lượng file

                InputStream in = new BufferedInputStream(connection.getInputStream());
                OutputStream out = requireContext().getContentResolver().openOutputStream(uri);

                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalRead = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    totalRead += bytesRead;
                    out.write(buffer, 0, bytesRead);

                    // Tính phần trăm tải
                    int progress = (int) (totalRead * 100 / fileLength);

                    // Cập nhật tiến trình vào notification
                    builder.setContentText(progress + "%")
                            .setProgress(100, progress, false);
                    notificationManager.notify(notificationId, builder.build());
                }

                in.close();
                out.close();

                // Tạo intent mở file khi bấm vào notification
                Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
                openFileIntent.setDataAndType(uri, "application/pdf");
                openFileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        requireContext(),
                        0,
                        openFileIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                requireActivity().runOnUiThread(() -> {
                    try {
                        Log.d("NOTIFY", "Preparing to update notification");
                        builder.setContentText("Download complete")
                                .setSmallIcon(R.drawable.ic_success)
                                .setProgress(0, 0, false)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        notificationManager.notify(notificationId, builder.build());
                        Log.d("NOTIFY", "Notification updated successfully");
                    } catch (Exception e) {
                        Log.e("NOTIFY", "Error updating notification: " + e.getMessage());
                    }
                });

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Download successfully", Toast.LENGTH_SHORT).show()
                );

            } catch (IOException e) {
                e.printStackTrace();

                builder.setContentText("Download failed")
                        .setProgress(0, 0, false);
                notificationManager.notify(notificationId, builder.build());

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Download failed", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();

    }
    private void exportAllCVsToZip(Uri outputUri) {
        String exportZipChannelId = "export_zip_channel";
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        int exportNotificationId = (int) System.currentTimeMillis();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), exportZipChannelId)
                .setSmallIcon(R.drawable.ic_download) // icon tuỳ chỉnh của bạn
                .setContentTitle("Exporting CVs...")
                .setContentText("0%")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, false);

        // Kiểm tra quyền nếu Android 13+
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(exportNotificationId, builder.build());
        }

        new Thread(() -> {
            try {
                int totalFiles = currentSubmittedProfiles.size();
                int currentIndex = 0;

                ZipOutputStream zipOut = new ZipOutputStream(requireContext().getContentResolver().openOutputStream(outputUri));

                int index = 0; //danh so de khong trung ten file
                for (JobApplied jobApplied : currentSubmittedProfiles) {
                    Log.e("ZIP_EXPORT", "Processing file: " + jobApplied.getCV().getTitle());
                    String fileUrl = urlSupabase + jobApplied.getCV().getFilePath();
                    URL url = new URL(fileUrl);
                    InputStream inputStream = new BufferedInputStream(url.openStream());

                    String fileName = (index++)+ "_" + jobApplied.getApplicant().getFirstName()+ jobApplied.getApplicant().getLastName() + ".pdf";
                    ZipEntry entry = new ZipEntry(fileName);
                    zipOut.putNextEntry(entry);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, bytesRead);
                    }

                    zipOut.closeEntry();
                    inputStream.close();

                    currentIndex++;
                    int progress = (int) ((currentIndex * 100.0f) / totalFiles);

                    builder.setContentText(progress + "%")
                            .setProgress(100, progress, false);
                    notificationManager.notify(exportNotificationId, builder.build());
                }

                zipOut.close();

                // Mở file khi click vào thông báo
                Intent openIntent = new Intent(Intent.ACTION_VIEW);
                openIntent.setDataAndType(outputUri, "application/zip");
                openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        requireContext(), 0, openIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                builder.setContentTitle("Export complete")
                        .setContentText("All CVs exported.")
                        .setSmallIcon(R.drawable.ic_success)
                        .setProgress(0, 0, false)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(exportNotificationId, builder.build());

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Export successful!", Toast.LENGTH_SHORT).show());

            } catch (IOException e) {
                e.printStackTrace();
                builder.setContentTitle("Export failed")
                        .setContentText("An error occurred.")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.drawable.ic_cancel);
                notificationManager.notify(exportNotificationId, builder.build());

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Export failed.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (submittedProfiles != null) {
            submittedProfiles.clear();  //tranh mo lai o jobpost khac khi chua kip load du lieu moi
        }
    }
    private void updateAdapterList(String status) {
        List<JobApplied> statusProfiles = new ArrayList<>();
        for (JobApplied jobApplied : submittedProfiles) {
            if (status.equalsIgnoreCase(jobApplied.getStatus())) {
                statusProfiles.add(jobApplied);
            }
        }

        currentSubmittedProfiles.clear();
        currentSubmittedProfiles.addAll(statusProfiles);
        adapter.updateList(statusProfiles);
    }


}
