package com.example.workleap.ui.view.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.jobpost_post.CommentAdapter;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CommentBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_POST_ID = "postId";
    private static final String ARG_USER = "user";
    private PostViewModel postViewmodel;
    private UserViewModel userViewModel;
    private NavController navController;
    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public static CommentBottomSheet newInstance(String postId, User user) {
        CommentBottomSheet fragment = new CommentBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    private String postId;
    private User myUser;
    private CommentAdapter adapter;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private String commentReplyId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_bottom_sheet, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerComment);
        EditText edtComment = view.findViewById(R.id.edtComment);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        ImageButton btnCancelReply = view.findViewById(R.id.btnCancelReply);
        TextView tvReplyTo = view.findViewById(R.id.tvReply);

        //Viewmodel
        postViewmodel = new PostViewModel();
        postViewmodel.InitiateRepository(getContext());
        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());

        if (getArguments() != null) {
            postId = getArguments().getString(ARG_POST_ID);
            myUser = (User) getArguments().getSerializable("user");
        }

        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", data);
                bundle.putSerializable("userId", data.getId());
                bundle.putSerializable("myUser", myUser);

                //Check company or applicant
                if(data.getCompanyId() == null)
                    navController.navigate(R.id.watchApplicantProfileFragment, bundle);
                else
                {
                    bundle.putString("companyId", data.getCompanyId());
                    Log.d("cmt bottsh", data.getId());
                    navController.navigate(R.id.watchCompanyProfileFragment, bundle);
                }

                //Show off the bottomsheet
                dismiss();
            }
            else
                Log.d("CommentBottomSheet", "User of comment null");
        });
        // Setup adapter RecyclerView hiển thị comment theo postId
        postViewmodel.getCommentByPostData().observe(getViewLifecycleOwner(), data->
        {
            if(data != null)
            {
                comments.clear();
                comments.addAll(data);

                //Khoi tao adapter cung su kien click item
                adapter = new CommentAdapter(comments, postViewmodel, userViewModel, this, new CommentAdapter.OnCommentClickListener() {
                    @Override
                    public void onCommentClick(Comment comment) {
                        tvReplyTo.setVisibility(View.VISIBLE);
                        btnCancelReply.setVisibility(View.VISIBLE);
                        tvReplyTo.setText("Reply to: " + comment.getUser().getUsername());
                        commentReplyId = comment.getId();
                    }
                    @Override
                    public void onAvatarClick(Comment comment) {
                        userViewModel.getUser(comment.getUserId());
                    }
                });

                //Logo jobpost bang usermodel
                userViewModel.avatarCommentUrlMap().observe(getViewLifecycleOwner(), map -> {
                    adapter.setImageUrlMap(map);  // Truyền map xuống adapter
                });
                for (Comment comment : comments) {
                    userViewModel.getAvatarCommentImageUrl(comment.getUser().getAvatar()); //dung logopath company lam key
                }

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

        //Nhan ket qua comment tra ve de them ngay vao danh sach
        postViewmodel.creatCommentData().observe(getViewLifecycleOwner(), data ->{
            if(data != null)
            {
                if(data.getCommentId() == null)
                    comments.add(data);
                else
                    postViewmodel.getCommentByPost(postId);
                adapter.notifyDataSetChanged();
            }
        });
        // Gửi comment mới khi nhấn nút btnSend
        btnSend.setOnClickListener(v -> {
            String commentDetail = edtComment.getText().toString();

            Comment newComment = null;
            if(commentReplyId != null)
                newComment = new Comment(myUser.getId(), postId, commentReplyId, commentDetail);
            else
                newComment = new Comment(myUser.getId(), postId, commentDetail);

            postViewmodel.createComment(newComment);
            edtComment.setText("");
        });

        //Huy reply
        btnCancelReply.setOnClickListener(v -> {
            tvReplyTo.setVisibility(View.GONE);
            btnCancelReply.setVisibility(View.GONE);
            commentReplyId = null;
        });

        // Hủy bỏ khi nhấn nút btnCancel
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }
}
