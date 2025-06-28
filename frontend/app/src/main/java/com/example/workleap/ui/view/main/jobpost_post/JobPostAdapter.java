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

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private List<JobPost> jobPostList;
    private Map<String, String> logoUrlMap = new HashMap<>();
    private String logoFilePath;
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

    @NonNull
    @Override
    public JobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobpost, parent, false);
        return new JobPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobPostViewHolder holder, int position) {
        JobPost post = jobPostList.get(position);
        holder.txtTitle.setText(post.getTitle());
        holder.txtCompany.setText(post.getCompany().getName());
        holder.txtSalary.setText(post.getSalaryStart() + " - " + post.getSalaryEnd() + " " + post.getCurrency());
        holder.txtTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(post.getUpdatedAt()));
        holder.txtLocation.setText(post.getPosition());
        holder.txtTag1.setText(post.getJobCategory().getName());
        holder.txtTag2.setText(post.getJobType().getName());
        holder.txtTag3.setText(post.getPosition());

        //Xu li logo company jobpost
        logoFilePath = post.getCompany().getUser().get(0).getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = logoUrlMap.get(logoFilePath);
            if(holder.imgPost == null)
                Log.d("JobPostAdapter", "logoPost is null");
            if (imageUrl != null && holder.imgPost != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgPost);
            }
        }

        // Thêm sự kiện nhấp vào item
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onJobPostClick(post);
            }
        });

        // Thêm PopupMenu cho btnOption
        holder.btnOption.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnOption);
            popupMenu.inflate(R.menu.menu_options_jobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.menu_save) {
                        //Save
                        clickListener.onSaveClick(post);
                        return true;
                    }
                    else if(item.getItemId() == R.id.menu_report)
                    {
                       //report
                        clickListener.onReportClick(post);
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
        return jobPostList.size();
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

    public void setLogoUrlMap(Map<String, String> map) {
        this.logoUrlMap = map;
        notifyDataSetChanged();
    }
}
