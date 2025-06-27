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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * Use the {@link UpdatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePostFragment extends Fragment {
    private PostViewModel postViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtTextContent;
    private Button btnSavePost, btnLoadImage, btnCancel;
    private ImageView imgContent;
    private static final int PICK_IMAGE_REQUEST = 1001;
    private boolean isPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công
    private Uri imageUri;
    private Post currentPost;

    public UpdatePostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpdatePostFragment newInstance(String param1, String param2) {
        UpdatePostFragment fragment = new UpdatePostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false);
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
        btnSavePost = view.findViewById(R.id.btnSavePost);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnLoadImage = view.findViewById(R.id.btnLoadImage);

        //Set current value
        currentPost = (Post) getArguments().getSerializable("post");
        edtTitle.setText(currentPost.getTitle());
        edtTextContent.setText(currentPost.getContents().get(0).getValue());

        //Load anh hien tai
        postViewModel.getImageUrlData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                Log.d("Get image url data", "Get image url data: " + data);
                Glide.with(requireContext()).load(data).into(imgContent);
            }
            else
                Log.d("Get image url data", "Get image url data null");
        });
        postViewModel.getImageUrlResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("Get image url result", result);
            else
                Log.d("Get image url result", "Get image url result null");
        });
        if(currentPost.getContents().size() > 1)
        {
            if(currentPost.getContents().get(1).getValue() != null)
                postViewModel.getImageUrl(currentPost.getContents().get(1).getValue());
        }

        //Nhan ket qua update post .
        postViewModel.updatePostByIdData().observe(getViewLifecycleOwner(), post ->
        {
            if(post != null)
            {
                Log.e("Update post data", new Gson().toJson(post));

                //Upload anh dang hien thi len csdl
                //Ket qua
                postViewModel.uploadImageResult().observe(getViewLifecycleOwner(), result ->
                {
                    if(result != null)
                        Log.e("Upload image result", result);
                    else
                        Log.e("Upload image result", "Upload image result null");
                });

                //Tao file de gui
                if(imageUri != null)
                {
                    File imageFile = uriToFile(imageUri);
                    // Tạo RequestBody từ file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("image/*"),
                            imageFile
                    );
                    RequestBody postIdBody = RequestBody.create(
                            MediaType.parse("text/plain"), post.getId()
                    );
                    RequestBody orderBody = RequestBody.create(
                            MediaType.parse("text/plain"), String.valueOf(2) //Hinh luon co thu tu la 2
                    );

                    // Tạo MultipartBody.Part
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
                    postViewModel.uploadImage(body, postIdBody, orderBody);
                }
            }
            else
                Log.e("Update post data", "Update post data null");
        });
        postViewModel.updatePostByIdResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Update post result", result);
            else
                Log.e("Update post result", "Update post result null");

            if(isPostSubmitted) {
                // Hien lai bottom navigation va quay ve
                ((NavigationActivity) getActivity()).showBottomNav(true);
                NavHostFragment.findNavController(this).navigateUp();
            }
        });

        // TODO: Add listeners or bind ViewModel here

        btnSavePost.setOnClickListener(v -> {
            ArrayList<PostContent> contents = new ArrayList<PostContent>();
            String companyId = getArguments().getString("companyId");

            //Xu li content, add text va anh hien tai
            PostContent textContent = new PostContent("TEXT", edtTextContent.getText().toString(), 1);
            contents.add(textContent);
            if(currentPost.getContents().size() > 1)
            {
                PostContent imageContent = new PostContent("IMAGE", currentPost.getContents().get(1).getValue(), 2);
                contents.add(imageContent);
            }

            //Handle save logic here
            Post post = new Post(
                    companyId,
                    edtTitle.getText().toString(),
                    contents
            );

            Log.d("new post", new Gson().toJson(post));
            postViewModel.updatePostById(currentPost.getId(), post);
            isPostSubmitted = true;
        });

        btnCancel.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        btnLoadImage.setOnClickListener(v -> {
            Log.d("btnLoadImage", "btnLoadImage clicked");
            // Yêu cầu quyền (nếu chưa có)
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                Log.d("btnLoadImage", "xin quyen");
            } else {
                // Mở bộ chọn ảnh
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                Log.d("btnLoadImage", "mo bo chon anh");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            //Hien thi anh duoc chon len
            imgContent.setImageURI(imageUri);
        }
    }

    private File uriToFile(Uri uri) {
        File file = new File(requireContext().getCacheDir(), "avatar.jpg");
        try (InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            } else {
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                openImagePicker();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

}