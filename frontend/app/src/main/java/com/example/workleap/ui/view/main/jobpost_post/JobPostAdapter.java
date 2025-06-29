package com.example.workleap.ui.view.main.jobpost_post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SKELETON = 0;
    private static final int VIEW_TYPE_DATA = 1;

    private List<JobPost> jobPostList;
    private Map<String, String> logoUrlMap = new HashMap<>();
    private String logoFilePath;
    private boolean isLoading = false;
    private final int skeletonCount = 4;

    private OnJobPostClickListener clickListener;

    public interface OnJobPostClickListener {
        void onJobPostClick(JobPost jobPost);
        void onSaveClick(JobPost jobPost);
        void onReportClick(JobPost jobPost);
    }

    public JobPostAdapter(List<JobPost> jobPostList, OnJobPostClickListener clickListener) {
        this.jobPostList = jobPostList;
        this.clickListener = clickListener;
    }

    public void setData(List<JobPost> newList) {
        this.jobPostList.clear();
        this.jobPostList.addAll(newList);
        notifyDataSetChanged();
    }

    public void showShimmer() {
        isLoading = true;
        notifyDataSetChanged();
    }

    public void hideShimmer(List<JobPost> newList) {
        isLoading = false;
        setData(newList);
    }

    public void setLogoUrlMap(Map<String, String> map) {
        this.logoUrlMap = map;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isLoading ? VIEW_TYPE_SKELETON : VIEW_TYPE_DATA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SKELETON) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobpost_skeleton, parent, false);
            return new SkeletonViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobpost, parent, false);
            return new JobPostViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof JobPostViewHolder) {
            JobPost post = jobPostList.get(position);
            JobPostViewHolder h = (JobPostViewHolder) holder;

            h.txtTitle.setText(post.getTitle());
            h.txtCompany.setText(post.getCompany().getName());
            h.txtSalary.setText(post.getSalaryStart() + " - " + post.getSalaryEnd() + " " + post.getCurrency());
            h.txtTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(post.getUpdatedAt()));
            h.txtLocation.setText(post.getPosition());
            h.txtTag1.setText(post.getJobCategory().getName());
            h.txtTag2.setText(post.getJobType().getName());
            h.txtTag3.setText(post.getPosition());

            logoFilePath = post.getCompany().getUser().get(0).getAvatar();
            if (logoFilePath != null) {
                String imageUrl = logoUrlMap.get(logoFilePath);
                if (imageUrl != null) {
                    Glide.with(holder.itemView.getContext()).load(imageUrl).into(h.imgPost);
                }
            }

            h.itemView.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onJobPostClick(post);
            });

            h.btnOption.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), h.btnOption);
                popupMenu.inflate(R.menu.menu_options_jobpost);
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_save) {
                        clickListener.onSaveClick(post);
                        return true;
                    } else if (item.getItemId() == R.id.menu_report) {
                        clickListener.onReportClick(post);
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return isLoading ? skeletonCount : jobPostList.size();
    }

    static class JobPostViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtCompany, txtSalary, txtLocation, txtTime, txtTag1, txtTag2, txtTag3;
        ImageView imgPost;
        ImageButton btnOption;

        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgPost = itemView.findViewById(R.id.imgPost);
            txtTag1 = itemView.findViewById(R.id.txtTab1);
            txtTag2 = itemView.findViewById(R.id.txtTab2);
            txtTag3 = itemView.findViewById(R.id.txtTab3);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }

    static class SkeletonViewHolder extends RecyclerView.ViewHolder {
        public SkeletonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
