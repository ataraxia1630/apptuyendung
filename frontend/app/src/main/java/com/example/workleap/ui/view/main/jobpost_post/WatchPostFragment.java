package com.example.workleap.ui.view.main.jobpost_post;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.PostContent;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchPostFragment extends Fragment {
    private PostViewModel postViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtTextContent;
    private Button btnCancel;
    private ImageView imgContent;
    private ImageButton btnOptions;
    private Post currentPost;


    public WatchPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WatchPostFragment newInstance(String param1, String param2) {
        WatchPostFragment fragment = new WatchPostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watch_post, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);
        postViewModel.InitiateRepository(getContext());

        //Tim cac thanh phan component
        edtTitle = view.findViewById(R.id.edtTitle);
        edtTextContent = view.findViewById(R.id.edtTextContent);
        imgContent = view.findViewById(R.id.imgContent);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOptions = view.findViewById(R.id.btnOptions);

        //Set current value
        currentPost = (Post) getArguments().getSerializable("post");
        edtTitle.setText(currentPost.getTitle());
        edtTextContent.setText(currentPost.getContents().get(0).getValue());

        btnOptions.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), btnOptions);
            popupMenu.getMenuInflater().inflate(R.menu.menu_options_adminjobpost, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.menu_delete) {
                    // Gọi ViewModel hoặc mở Fragment chỉnh sửa
                    Toast.makeText(getContext(), "Post Deleted", Toast.LENGTH_SHORT).show();
                    postViewModel.deletePostById(currentPost.getId());
                    return true;

                } else if (id == R.id.menu_approve) {
                    // Gọi ViewModel xoá bài viết
                    postViewModel.updatePostStatus(currentPost.getId(), "OPENING");
                    Toast.makeText(getContext(), "Post Opened", Toast.LENGTH_SHORT).show();
                    return true;

                } else if (id == R.id.menu_reject) {
                    // Cập nhật trạng thái bài viết
                    postViewModel.updatePostStatus(currentPost.getId(), "CANCELLED");
                    Toast.makeText(getContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            });

            popupMenu.show();
        });

        //Load anh hien tai
        postViewModel.getImageUrlData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                Log.d("Get image url data", "Get image url data: " + data);
                Glide.with(requireContext()).load(data).into(imgContent);
            } else
                Log.d("Get image url data", "Get image url data null");
        });
        postViewModel.getImageUrlResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null)
                Log.d("Get image url result", result);
            else
                Log.d("Get image url result", "Get image url result null");
        });
        if (currentPost.getContents().size() > 1) {
            if (currentPost.getContents().get(1).getValue() != null)
                postViewModel.getImageUrl(currentPost.getContents().get(1).getValue());
        }


        btnCancel.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

    }
}