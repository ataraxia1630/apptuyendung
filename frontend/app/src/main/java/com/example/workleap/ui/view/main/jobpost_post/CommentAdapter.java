package com.example.workleap.ui.view.main.jobpost_post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;
    private PostViewModel commentViewModel;

    public CommentAdapter(List<Comment> commentList, PostViewModel commentViewModel) {
        this.commentList = commentList;
        this.commentViewModel = commentViewModel;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.txtUsername.setText(comment.getUser().getUsername());
        holder.txtDateTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(comment.getCreatedAt()));
        holder.txtCommentDetail.setText(comment.getCommentDetail());
        //holder.imgPost.setImageResource();

        //ChildComment
        // Xóa các reply cũ nếu có (tránh bị lặp lại do ViewHolder được tái sử dụng)
        holder.layoutReplies.removeAllViews();
        // Duyệt qua các child comment
        List<Comment> childComments = comment.getChildComment();
        if (childComments != null && !childComments.isEmpty()) {

            //Hien ra layout replies
            holder.layoutReplies.removeAllViews();
            holder.layoutReplies.setVisibility(View.VISIBLE);

            Log.d("Child comment", childComments.get(0).getCommentDetail());
            for (Comment child : childComments) {
                View childView = LayoutInflater.from(holder.itemView.getContext())
                        .inflate(R.layout.item_comment, holder.layoutReplies, false);

                // Gán dữ liệu child comment
                TextView txtUsername = childView.findViewById(R.id.tvUsername);
                TextView txtDateTime = childView.findViewById(R.id.tvDateTime);
                TextView txtCommentDetail = childView.findViewById(R.id.tvCommentDetail);
                ImageView imgAvatar = childView.findViewById(R.id.imgAvatar);
                ImageButton btnOptions = childView.findViewById(R.id.btnOptions);

                txtUsername.setText(child.getUser().getUsername());
                txtDateTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(child.getCreatedAt()));
                txtCommentDetail.setText(child.getCommentDetail());

                // Ẩn các thành phần không cần thiết
                LinearLayout childReplies = childView.findViewById(R.id.layoutReplies);
                childReplies.setVisibility(View.GONE);

                // Căn lề trái (thụt vào để phân biệt comment con)
                View rootLayout = childView.findViewById(R.id.itemCommentRoot);
                if (rootLayout != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rootLayout.getLayoutParams();
                    params.setMarginStart(48);  // hoặc dùng paddingStart nếu muốn padding
                    rootLayout.setLayoutParams(params);
                }

                holder.layoutReplies.addView(childView);
            }
        }
        else
            Log.d("COMMENT_ADAPTER", "No childComment");

        // Thêm PopupMenu cho btnOption
        /*holder.btnOption.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnOption);
            popupMenu.inflate(R.menu.menu_options_mycomment); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.menu_edit) {
                        //Chuyen sang fragment
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_delete)
                    {
                        //Xoa trong csdl
                        commentViewModel.deleteComment(comment.getId());

                        //Xoa lap tuc tren danh sach
                        commentList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, commentList.size());
                        return true;
                    }
                    else
                        return false;
            });
            popupMenu.show();
        });*/
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtDateTime, txtCommentDetail;
        ImageView imgAvatar;
        ImageView btnOption;
        LinearLayout layoutReplies;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.tvUsername);
            txtDateTime = itemView.findViewById(R.id.tvDateTime);
            txtCommentDetail = itemView.findViewById(R.id.tvCommentDetail);
            layoutReplies = itemView.findViewById(R.id.layoutReplies);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
