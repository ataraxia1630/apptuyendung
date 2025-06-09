package com.example.workleap.ui.view.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.CVViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            allCVs.clear();
            allCVs.addAll(CVs);
            // Setup RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MyCVAdapter(allCVs); // mặc định show tất cả
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
                            cvViewModel.createCvResult().observe(getViewLifecycleOwner(), createResult -> {
                                if(!isAdded() || getView()==null) return;

                                //reload
                                cvViewModel.getAllCv(user.getApplicantId());

                                if(result!=null)
                                    Log.e("MyCVFragment", createResult);
                                else
                                    Log.e("MyCVFragment", "create MyCVFragment result NULL" );
                            });
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
}