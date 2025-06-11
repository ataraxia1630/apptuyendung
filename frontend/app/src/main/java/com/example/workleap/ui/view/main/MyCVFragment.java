package com.example.workleap.ui.view.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.CVViewModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyCVFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyCVAdapter adapter;
    private List<CV> allCVs = new ArrayList<>();
    private CVViewModel cvViewModel;
    private User user;

    private ImageButton btnAddCV;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> createFileLauncher;
    private CV downloadingCV;
    private String urlSupabase = "https://epuxazakjgtmjuhuwkza.supabase.co/storage/v1/object/public/cv-storage/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");

        recyclerView = view.findViewById(R.id.recyclerCvs); // ID trong layout
        cvViewModel = new ViewModelProvider(requireActivity()).get(CVViewModel.class);

        btnAddCV = view.findViewById(R.id.btnAddCV);

        createFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        downloadFileToUri(uri, downloadingCV);
                    }
                }
        );

        if(adapter==null)
        {
            adapter = new MyCVAdapter(allCVs, new MyCVAdapter.OnCVMenuClickListener() {
                @Override
                public void onOpen(CV cv) {
                    //Toast.makeText(getContext(), "Mở: " + cv.getTitle(), Toast.LENGTH_SHORT).show();
                    PdfFragment pdfFragment = new PdfFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("pdf_title", cv.getTitle());
                    bundle.putString("pdf_url", urlSupabase + cv.getFilePath());
                    pdfFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, pdfFragment)
                            .addToBackStack(null)
                            .commit();
                }

                @Override
                public void onDownload(CV cv)
                {
                    downloadingCV = cv;
                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.setType("application/pdf");
                    intent.putExtra(Intent.EXTRA_TITLE, cv.getTitle());
                    createFileLauncher.launch(intent);
                }

                @Override
                public void onRename(CV cv) {
                    //Toast.makeText(getContext(), "Đổi tên: " + cv.getTitle(), Toast.LENGTH_SHORT).show();
                    showInputDialog(getContext(), cv.getTitle(), newTitle ->{
                        cvViewModel.updateCvById(cv.getId(), newTitle);
                    });
                }

                @Override
                public void onDelete(CV cv) {
                    cvViewModel.deleteCvById(cv.getId());
                }
            });

            // Setup RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        cvViewModel.getAllCvResult().observe(getViewLifecycleOwner(), result ->
        {
            String s = String.valueOf(result);
            Log.e("MyCVFragment", "getAllCvResult: " + s);
        });
        cvViewModel.getAllCvData().observe(getViewLifecycleOwner(), CVs ->
        {
            if(CVs==null)
            {
                Log.e("CVFragment", "getAllCvData NULL");
                return;
            }

            adapter.updateList(CVs);
        });

        cvViewModel.updateCvByIdResult().observe(getViewLifecycleOwner(), updateCVResul-> {
            if(!isAdded() || getView()==null) return;

            //reload
            cvViewModel.getAllCv(user.getApplicantId());

            if(updateCVResul!=null)
                Log.e("MyCVFragment", "updateCvByIdResult " + updateCVResul);
            else
                Log.e("MyCVFragment", "updateCvByIdResult result NULL" );

        });
        cvViewModel.deleteCvByIdResult().observe(getViewLifecycleOwner(), deleteResult -> {
            if(!isAdded() || getView()==null) return;

            //reload
            cvViewModel.getAllCv(user.getApplicantId());

            if(deleteResult!=null)
                Log.e("MyCVFragment", "deleteCvByIdResult " + deleteResult);
            else
                Log.e("MyCVFragment", "deleteCvByIdResult result NULL" );
        });
        cvViewModel.createCvResult().observe(getViewLifecycleOwner(), createResult -> {
            if(!isAdded() || getView()==null) return;

            //reload
            cvViewModel.getAllCv(user.getApplicantId());

            if(createResult!=null)
                Log.e("MyCVFragment", createResult);
            else
                Log.e("MyCVFragment", "createCvResult NULL" );
        });

        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        String fileName = getFileNameFromUri(fileUri);

                        try {
                            // Tạo file tạm từ Uri
                            InputStream inputStream = getContext().getContentResolver().openInputStream(fileUri);
                            File targetFile = new File(getContext().getCacheDir(), fileName);

                            OutputStream outputStream = new FileOutputStream(targetFile);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                            outputStream.close();
                            inputStream.close();

                            // Gửi file vào ViewModel
                            cvViewModel.createCv(user.getApplicantId(), targetFile, fileName);

                            Log.e("MyCVFragment","create CV");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error reading file", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        btnAddCV.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            filePickerLauncher.launch(intent);
        });

    }
    private String getFileNameFromUri(Uri uri) {
        String result = null;

        if ("content".equals(uri.getScheme())) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    result = cursor.getString(nameIndex);
                }
                cursor.close();
            }
        }

        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private void showInputDialog(Context context, String oldTitle, OnInputConfirmedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rename");

        // Tạo EditText
        final EditText input = new EditText(context);
        input.setText(oldTitle);
        input.setTextSize(24);
        input.setPadding(64, 16, 48, 48);
        builder.setView(input);

        // Nút Xác nhận
        builder.setPositiveButton("OK", (dialog, which) -> {
            String userInput = input.getText().toString().trim();
            listener.onConfirmed(userInput);
        });

        // Nút Hủy
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
    public interface OnInputConfirmedListener {
        void onConfirmed(String input);
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

}