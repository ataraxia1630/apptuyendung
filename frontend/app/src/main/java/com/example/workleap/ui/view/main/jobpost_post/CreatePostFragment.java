package com.example.workleap.ui.view.main.jobpost_post;

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
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.PostContent;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.utils.ToastUtil;
import com.google.gson.Gson;
import static android.app.Activity.RESULT_OK;

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
 * Use the {@link CreatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePostFragment extends Fragment {
    private PostViewModel postViewModel;

    private AutoCompleteTextView autoJobCategory, autoJobType;
    private EditText edtTitle, edtTextContent;
    private Button btnSavePost, btnLoadImage, btnCancel;
    private ImageView imgContent;
    private static final int PICK_IMAGE_REQUEST = 1001;
    private boolean isPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công
    private Uri imageUri;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreatePostFragment newInstance(String param1, String param2) {
        CreatePostFragment fragment = new CreatePostFragment();
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

        //Nhan ket qua creat post .
        postViewModel.createPostData().observe(getViewLifecycleOwner(), post ->
        {
            if(post != null)
            {
                Log.e("Create post data", new Gson().toJson(post));

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
                Log.e("Create post data", "Create post data null");
        });
        postViewModel.createPostResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("Create post result", result);
            else
                Log.e("Create post result", "Create post result null");

            if(isPostSubmitted) {
                // Hien lai bottom navigation va quay ve
                ((NavigationActivity) getActivity()).showBottomNav(true);
                NavHostFragment.findNavController(this).navigateUp();
            }
        });

        // TODO: Add listeners or bind ViewModel here

        btnSavePost.setOnClickListener(v -> {
            if(edtTextContent.getText().toString().trim().isEmpty() || edtTitle.getText().toString().trim().isEmpty())
            {
                ToastUtil.showToast(v.getContext(), "Please fill all fields", ToastUtil.TYPE_WARNING);
                return;
            }

            ArrayList<PostContent> contents = new ArrayList<PostContent>();
            String companyId = getArguments().getString("companyId");

            //Xu li content
            PostContent textContent = new PostContent("TEXT", edtTextContent.getText().toString(), 1);
            contents.add(textContent);
            /*PostContent imageContent = new PostContent("IMAGE", "image_url", 2);
            contents.add(imageContent);*/

            //Handle save logic here
            Post post = new Post(
                    companyId,
                    edtTitle.getText().toString(),
                    contents
            );

            Log.d("new post", new Gson().toJson(post));
            postViewModel.createPost(post);
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