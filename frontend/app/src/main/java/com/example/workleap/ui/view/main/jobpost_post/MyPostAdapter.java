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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.home.CommentBottomSheet;
import com.example.workleap.ui.viewmodel.PostViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.PostViewHolder> {
    private List<Post> postList;
    private PostViewModel postViewModel;
    private Map<String, String> imageUrlMap = new HashMap<>();
    private String filePath;
    private LifecycleOwner lifecycleOwner;
    private FragmentManager fragmentManager;
    private Map<String, String> logoUrlMap = new HashMap<>();
    private String logoFilePath;
    private User user;
    public MyPostAdapter(List<Post> postList, PostViewModel postViewModel, LifecycleOwner lifecycleOwner, FragmentManager fragmentManager, User user) {
        this.postList = postList;
        this.postViewModel = postViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.fragmentManager = fragmentManager;
        this.user = user;
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
        //holder.txtCommentCount.setText(post.getComment().length)

        //Xu li anh jobpost
        if(post.getContents().size() > 1)
            filePath = post.getContents().get(1).getValue(); // dùng làm key
        if(filePath != null)
        {
            String imageUrl = imageUrlMap.get(filePath);
            Log.d("MyPostAdapter", "Image URL: " + imageUrl);
            if(holder.imgPost == null)
                Log.d("MyPostAdapter", "imgPost is null");
            if (imageUrl != null && holder.itemView.getContext() != null && holder.imgPost != null) {
                Log.d("MyPostAdapter", "Loading image from URL: " + imageUrl);
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgPost);
            }
        }

        //Comment
        holder.btnComment.setOnClickListener(v -> {
            CommentBottomSheet bottomSheet = CommentBottomSheet.newInstance(post.getId(), user);
            bottomSheet.show(fragmentManager, "commentSheet");
        });


        //Xu li logo company jobpost
        logoFilePath = post.getCompany().getUser().get(0).getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = logoUrlMap.get(logoFilePath);
            if(holder.logoPost == null)
                Log.d("MyPostAdapter", "logoPost is null");
            if (imageUrl != null && holder.logoPost != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.logoPost);
            }
        }

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
        TextView txtName, txtTime, txtTitle, txtContent, txtReactionCount, txtCommentCount, txtShareCount;
        ImageView imgPost, logoPost;
        ImageButton btnOption, btnReact, btnShare;
        LinearLayout btnComment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_user_name);
            txtTime = itemView.findViewById(R.id.tv_post_time);
            txtContent = itemView.findViewById(R.id.tv_post_content);
            txtCommentCount = itemView.findViewById(R.id.tv_comment_share_count);
            txtReactionCount = itemView.findViewById(R.id.tv_like_count);
            txtShareCount = itemView.findViewById(R.id.tv_like_count);
            txtTitle = itemView.findViewById(R.id.tv_post_title);

            logoPost = itemView.findViewById(R.id.iv_user_avatar);
            imgPost = itemView.findViewById(R.id.imgPost);
            btnOption = itemView.findViewById(R.id.btnOption);
            btnComment = itemView.findViewById(R.id.btnComment);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.imageUrlMap = map;
        notifyDataSetChanged(); // hoặc chỉ update item cụ thể nếu muốn tối ưu
    }

    public void setLogoUrlMap(Map<String, String> map) {
        this.logoUrlMap = map;
        notifyDataSetChanged();
    }
}
