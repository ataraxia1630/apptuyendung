package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.Reaction;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.home.CommentBottomSheet;
import com.example.workleap.ui.viewmodel.PostViewModel;
import com.google.gson.Gson;

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
    private NavController nav;
    private boolean hasObserved = false;
    public MyPostAdapter(List<Post> postList, PostViewModel postViewModel, LifecycleOwner lifecycleOwner, FragmentManager fragmentManager, User user, NavController nav) {
        this.postList = postList;
        this.postViewModel = postViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.fragmentManager = fragmentManager;
        this.user = user;
        this.nav = nav;
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
        holder.txtReactionCount.setText(post.getReaction().size() + " Reactions    •");
        holder.txtCommentShareCount.setText(post.getComment().size() + " Comments");

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

        //Xu li nut reaction
        if(post.getReaction().size() > 0)
        {
            boolean hasReaction = false;
            for (Reaction reaction : post.getReaction()) {
                if (reaction.getUserId().equals(user.getId())) {
                    hasReaction = true;
                    switch (reaction.getReactionType()) {
                        case "LIKE": holder.imgReaction.setImageResource(R.drawable.ic_like); break;
                        case "LOVE": holder.imgReaction.setImageResource(R.drawable.ic_love); break;
                        case "WOW":  holder.imgReaction.setImageResource(R.drawable.ic_wow);  break;
                        case "SAD":  holder.imgReaction.setImageResource(R.drawable.ic_sad);  break;
                        case "IDEA": holder.imgReaction.setImageResource(R.drawable.ic_idea); break;
                    }
                    break;
                }
            }
            if (!hasReaction) {
                holder.imgReaction.setImageResource(R.drawable.ic_reaction);
            }
        }

        //Comment
        holder.btnComment.setOnClickListener(v -> {
            CommentBottomSheet bottomSheet = CommentBottomSheet.newInstance(post.getId(), user);
            bottomSheet.show(fragmentManager, "commentSheet");
        });


        if(!hasObserved)
        {
            postViewModel.toggleReactionResult().observe(lifecycleOwner, result -> {
                if(result != null)
                {
                    Log.d("Reaction toggle", "Result: " + result);
                }
                else
                    Log.d("Reaction toggle", "Result is null");
            });
            postViewModel.toggleReactionData().observe(lifecycleOwner, data -> {
                if(data != null)
                {
                    Log.d("Reaction toggle", "Data toggle reaction: " + new Gson().toJson(data));
                    if(data.isRemoved())
                        holder.imgReaction.setImageResource(R.drawable.ic_reaction);
                }
                else
                    Log.d("Reaction toggle", "Data toggle reaction is null");
            });
            hasObserved = true;
        }


        //click same reaction to remove
        holder.btnReaction.setOnClickListener(v -> {
            //Lay ra reaction hien tai
            String currentReaction = "OLD";
            String newReaction = "NEW";
            for (Reaction reaction : post.getReaction()) {
                if (reaction.getUserId().equals(user.getId()))
                {
                    currentReaction = reaction.getReactionType();
                }
            }
            //holder.imgReaction.setImageResource(R.drawable.ic_reaction);

            View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_popup_reaction, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);

            // Hiển thị ngay phía trên nút
            popupWindow.showAsDropDown(v, 0, -v.getHeight() * 2);


            // Gắn sự kiện cho từng reaction
            popupView.findViewById(R.id.img_like).setOnClickListener(rv -> {
                Log.d("Reaction", "LIKE");
                Reaction request = new Reaction(post.getId(), user.getId(), "LIKE");
                postViewModel.toggleReaction(request);
                holder.imgReaction.setImageResource(R.drawable.ic_like);
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_love).setOnClickListener(rv -> {
                Log.d("Reaction", "LOVE");
                Reaction request = new Reaction(post.getId(), user.getId(), "LOVE");
                postViewModel.toggleReaction(request);
                holder.imgReaction.setImageResource(R.drawable.ic_love);
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_wow).setOnClickListener(rv -> {
                Log.d("Reactionnnn", "WOW");
                Reaction request = new Reaction(post.getId(), user.getId(), "WOW");
                postViewModel.toggleReaction(request);
                holder.imgReaction.setImageResource(R.drawable.ic_wow);
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_sad).setOnClickListener(rv -> {
                Log.d("Reaction", "SAD");
                Reaction request = new Reaction(post.getId(), user.getId(), "SAD");
                postViewModel.toggleReaction(request);
                holder.imgReaction.setImageResource(R.drawable.ic_sad);
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_idea).setOnClickListener(rv -> {
                Log.d("Reaction", "IDEA");
                Reaction request = new Reaction(post.getId(), user.getId(), "IDEA");
                postViewModel.toggleReaction(request);
                holder.imgReaction.setImageResource(R.drawable.ic_idea);
                popupWindow.dismiss();
            });
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
        holder.btnOption.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnOption);
            popupMenu.inflate(R.menu.menu_options_myjobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.menu_edit) {
                        //Chuyen sang fragment edit
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("post", post);
                        nav.navigate(R.id.updatePostFragment, bundle);
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_delete)
                    {
                        postViewModel.deletePostByIdResult().observe(lifecycleOwner, result -> {
                            if(result != null)
                            {
                                //Xoa lap tuc tren danh sach
                                postList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, postList.size());
                            }
                            else
                                Log.d("Delete post", "Result is null");
                        });
                        //Xoa trong csdl
                        postViewModel.deletePostById(post.getId());

                        return true;
                    }
                    else
                        return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTime, txtTitle, txtContent, txtReactionCount, txtCommentShareCount, txtShareCount;
        ImageView imgPost, logoPost, imgReaction;
        ImageButton btnOption, btnShare;
        LinearLayout btnComment, btnReaction;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_user_name);
            txtTime = itemView.findViewById(R.id.tv_post_time);
            txtContent = itemView.findViewById(R.id.tv_post_content);
            txtCommentShareCount = itemView.findViewById(R.id.tv_comment_share_count);
            txtReactionCount = itemView.findViewById(R.id.tv_like_count);
            txtShareCount = itemView.findViewById(R.id.tv_like_count);
            txtTitle = itemView.findViewById(R.id.tv_post_title);

            logoPost = itemView.findViewById(R.id.iv_user_avatar);
            imgPost = itemView.findViewById(R.id.imgPost);
            btnOption = itemView.findViewById(R.id.btnOption);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnReaction = itemView.findViewById(R.id.btn_like);
            imgReaction = itemView.findViewById(R.id.img_like);
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
