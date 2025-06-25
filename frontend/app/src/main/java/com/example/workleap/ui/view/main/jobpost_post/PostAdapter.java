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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.home.CommentBottomSheet;
import com.example.workleap.ui.viewmodel.PostViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private PostViewModel postViewModel;
    private Map<String, String> imageUrlMap = new HashMap<>();
    private String filePath;
    private LifecycleOwner lifecycleOwner;
    private FragmentManager fragmentManager;
    private User user;
    private NavController nav;

    public PostAdapter(List<Post> postList, PostViewModel postViewModel, LifecycleOwner lifecycleOwner, FragmentManager fragmentManager, User user, NavController nav) {
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
            if(holder.imgPost == null)
                Log.d("MyPostAdapter", "imgPost is null");
            if (imageUrl != null && holder.itemView.getContext() != null && holder.imgPost != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgPost);
            }
        }

        //Comment
        holder.btnComment.setOnClickListener(v -> {
            CommentBottomSheet bottomSheet = CommentBottomSheet.newInstance(post.getId(), user);
            bottomSheet.setNavController(nav);
            bottomSheet.show(fragmentManager, "commentSheet");
        });

        //React
        holder.btnReaction.setOnClickListener(v -> {
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
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_love).setOnClickListener(rv -> {
                Log.d("Reaction", "LOVE");
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_wow).setOnClickListener(rv -> {
                Log.d("Reaction", "WOW");
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_sad).setOnClickListener(rv -> {
                Log.d("Reaction", "SAD");
                popupWindow.dismiss();
            });

            popupView.findViewById(R.id.img_idea).setOnClickListener(rv -> {
                Log.d("Reaction", "IDEA");
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
        ImageView imgPost;
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
            btnShare= itemView.findViewById(R.id.btn_share);

            btnProfile = itemView.findViewById(R.id.post_header);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.imageUrlMap = map;
        notifyDataSetChanged(); // hoặc chỉ update item cụ thể nếu muốn tối ưu
    }
}
