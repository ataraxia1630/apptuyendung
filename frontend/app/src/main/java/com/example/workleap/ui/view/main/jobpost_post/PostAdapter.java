package com.example.workleap.ui.view.main.jobpost_post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private PostViewModel postViewModel;
    private Map<String, String> imageUrlMap = new HashMap<>();
    private Map<String, String> logoUrlMap = new HashMap<>();
    private String filePath;
    private String logoFilePath;
    private LifecycleOwner lifecycleOwner;
    private FragmentManager fragmentManager;
    private User user; //My User to reaction
    private NavController nav;
    private RecyclerView recyclerView;
    private boolean hasObserved = false;
    private boolean hasObservedGetPostById = false;

    public PostAdapter(List<Post> postList, PostViewModel postViewModel, LifecycleOwner lifecycleOwner, FragmentManager fragmentManager, User user, NavController nav, RecyclerView recyclerView) {
        this.postList = postList;
        this.postViewModel = postViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.fragmentManager = fragmentManager;
        this.user = user;
        this.nav = nav;
        this.recyclerView = recyclerView;
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

        //if

        //Xu li anh post
        if(post.getContents().size() > 1)
        {
            holder.imgPost.setVisibility(View.VISIBLE);
            filePath = post.getContents().get(1).getValue(); // dùng làm key
        }

        else
            holder.imgPost.setVisibility(View.GONE);

        if(filePath != null)
        {
            String imageUrl = imageUrlMap.get(filePath);
            if(holder.imgPost == null)
                Log.d("MyPostAdapter", "imgPost is null");
            if (imageUrl != null && holder.itemView.getContext() != null && holder.imgPost != null) {
                Glide.with(holder.itemView.getContext())
                        .asBitmap() // Load ảnh chính là bitmap
                        .load(imageUrl)
                        .placeholder(R.drawable.loading_icon) // Ảnh tạm (có thể là GIF nếu ảnh chính là bitmap)
                        .error(R.drawable.sample_photo)   // Ảnh fallback nếu lỗi
                        .into(holder.imgPost);
            }
        }

        //Xu li logo company post
        logoFilePath = post.getCompany().getUser().get(0).getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = logoUrlMap.get(logoFilePath);
            if(holder.logoPost == null)
                Log.d("MyPostAdapter", "logoPost is null");
            if (imageUrl != null && holder.logoPost != null) {
                Glide.with(holder.itemView.getContext())
                        .asBitmap() // Load ảnh chính là bitmap
                        .load(imageUrl)
                        .placeholder(R.drawable.loading_icon) // Ảnh tạm (có thể là GIF nếu ảnh chính là bitmap)
                        .error(R.drawable.sample_photo)   // Ảnh fallback nếu lỗi
                        .into(holder.logoPost);
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
            bottomSheet.setNavController(nav);
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


        //Watch profile
        holder.btnProfile.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userId", post.getCompany().getUser().get(0).getId());
            bundle.putSerializable("myUser", user);
            navController.navigate(R.id.watchCompanyProfileFragment, bundle);
        });

        //observe result update post status
        postViewModel.deletePostByIdResult().observe(lifecycleOwner, result -> {
            if(result != null)
            {
                Log.d("PostAdapter", "result: " + result);
            }
            else
                Log.d("PostAdapter", "result: null");
        });
        postViewModel.updatePostByIdResult().observe(lifecycleOwner, result -> {
            if(result != null)
            {
                Log.d("PostAdapter", "updatePostByIdResult result " + result);
            }
            else
                Log.d("PostAdapter", "updatePostByIdResult NULL");
        });

        // Thêm PopupMenu cho btnOption
        holder.btnOption.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnOption);
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                popupMenu.inflate(R.menu.menu_options_adminjobpost);
            } else {
                popupMenu.inflate(R.menu.menu_report_applicant); // Load menu từ file XML
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.menu_edit) {
                        //Chuyen sang fragment
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_delete)
                    {
                        //Xoa trong csdl
                        postViewModel.deletePostById(post.getId());
                        //Xoa lap tuc tren danh sach
                        postList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postList.size());
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_approve)
                    {
                        //Xoa trong csdl
                        postViewModel.updatePostStatus(post.getId(), "OPENING");

                        postList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postList.size());
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_reject)
                    {
                        //Xoa trong csdl
                        postViewModel.updatePostStatus(post.getId(), "CANCELLED");

                        postList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postList.size());
                        return true;
                    }
                    else if(item.getItemId() == R.id.action_report)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "post");
                        bundle.putString("targetId", post.getId());
                        bundle.putString("targetName", post.getTitle());
                        nav.navigate(R.id.sendReportFragment, bundle);
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
        TextView txtName, txtTime, txtTitle, txtContent, txtReactionCount, txtCommentShareCount;
        ImageView imgPost, logoPost, imgReaction;
        ImageButton btnOption;
        LinearLayout btnComment, btnReaction, btnShare, btnProfile;

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
            btnComment = itemView.findViewById(R.id.btnComment);
            btnReaction = itemView.findViewById(R.id.btn_like);
            //btnShare= itemView.findViewById(R.id.btn_share);

            btnProfile = itemView.findViewById(R.id.post_header);

            logoPost = itemView.findViewById(R.id.iv_user_avatar);
            imgReaction = itemView.findViewById(R.id.img_like);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.imageUrlMap = map;
        notifyDataSetChanged();
    }

    public void setLogoUrlMap(Map<String, String> map) {
        this.logoUrlMap = map;
        notifyDataSetChanged();
    }

    public void updateAPost(int position, Post post, LifecycleOwner lifecycleOwner)
    {
        if(!hasObservedGetPostById)
        {
            postViewModel.getPostByIdData().observe(lifecycleOwner, data -> {
                if(data != null)
                {
                    Log.d("PostAdapter", "getpostbyid: " + new Gson().toJson(data));
                    if (!recyclerView.isComputingLayout()) {
                        postList.set(position, data);
                        notifyItemChanged(position);
                    } else {
                        recyclerView.post(() -> {
                            postList.set(position, data);
                            notifyItemChanged(position);
                        });
                    }
                }
                else
                    Log.d("PostAdapter", "data: null");
            });
            hasObservedGetPostById = true;
        }
        postViewModel.getPostById(post.getId());
    }
}
