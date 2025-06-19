package com.example.workleap.ui.view.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.ui.view.main.jobpost_post.CommentAdapter;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_POST_ID = "postId";
    private PostViewModel postViewmodel;

    public static CommentBottomSheet newInstance(String postId) {
        CommentBottomSheet fragment = new CommentBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    private String postId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_bottom_sheet, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerComment);
        EditText edtComment = view.findViewById(R.id.edtComment);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        postViewmodel = new PostViewModel();
        postViewmodel.InitiateRepository(getContext());

        if (getArguments() != null) {
            postId = getArguments().getString(ARG_POST_ID);
        }

        // Setup adapter RecyclerView hiển thị comment theo postId
        postViewmodel.getCommentByPostData().observe(getViewLifecycleOwner(), comments->
        {
            if(comments != null)
            {
                CommentAdapter adapter = new CommentAdapter(comments, postViewmodel);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("CommentBottomSheet", "Comment size:" + comments.size());
            }
            else
                Log.d("CommentBottomSheet", "Comment null");
        });
        postViewmodel.getCommentByPostResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.d("CommentBottomSheet", "Get comment resutl: " + result);
        });
        postViewmodel.getCommentByPost(postId);

        // Gửi comment mới khi nhấn nút btnSend

        return view;
    }
}
