package com.example.workleap.ui.view.main.jobpost_post;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.PostContent;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

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

    private boolean isPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

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

        //Nhan ket qua creat post .
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
            ArrayList<PostContent> contents = new ArrayList<PostContent>();
            String companyId = getArguments().getString("companyId");

            //Xu li content
            PostContent textContent = new PostContent("TEXT", edtTextContent.getText().toString(), 1);
            contents.add(textContent);
            PostContent imageContent = new PostContent("IMAGE", "image_url", 2);
            contents.add(imageContent);

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
    }
}