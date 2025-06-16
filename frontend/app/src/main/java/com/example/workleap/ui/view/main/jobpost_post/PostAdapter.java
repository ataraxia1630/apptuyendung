package com.example.workleap.ui.view.main.jobpost_post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.ui.viewmodel.PostViewModel;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private PostViewModel postViewModel;

    public PostAdapter(List<Post> postList, PostViewModel postViewModel) {
        this.postList = postList;
        this.postViewModel = postViewModel;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.txtName.setText(post.getCompany().getName());
        holder.txtTime.setText(post.getCreatedAt().toString());
        holder.txtTitle.setText(post.getTitle());
        holder.txtContent.setText(post.getContents().get(0).getValue());
        //holder.txtReactionCount.setText(post.getContents().length);
        //holder.txtShareCount.setText(post.getReaction().length);
        //holder.txtCommentCount.setText(post.getComment().length);

        // Thêm PopupMenu cho btnOption
        /*holder.btnOption.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnOption);
            popupMenu.inflate(R.menu.menu_options_myjobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.menu_edit) {
                        //Chuyen sang fragment
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_delete)
                    {
                        //Xoa trong csdl
                        postViewModel.deletePost(post.getId());

                        //Xoa lap tuc tren danh sach
                        postList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postList.size());
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
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTime, txtTitle, txtContent, txtReactionCount, txtCommentShareCount;
        ImageView imgPost, imgAvatar;
        ImageButton btnOption, btnReact, btnComment, btnShare;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_user_name);
            txtTime = itemView.findViewById(R.id.tv_post_time);
            txtTitle = itemView.findViewById(R.id.tv_post_title);
            txtContent = itemView.findViewById(R.id.tv_post_content);

            txtCommentShareCount = itemView.findViewById(R.id.tv_comment_share_count);
            txtReactionCount = itemView.findViewById(R.id.tv_like_count);

            imgPost = itemView.findViewById(R.id.imgPost);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
