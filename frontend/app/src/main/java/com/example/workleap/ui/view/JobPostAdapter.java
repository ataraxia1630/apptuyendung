package com.example.workleap.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;

import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {
    private List<JobPost> jobPostList;

    public JobPostAdapter(List<JobPost> jobPostList) {
        this.jobPostList = jobPostList;
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
        //holder.txtCompany.setText(post.getCompany().getName());
        holder.txtSalary.setText(post.getSalaryStart() + " - " + post.getSalaryEnd() + " " + post.getCurrency());
        holder.txtTime.setText(post.getUpdatedAt().toString());
        holder.txtLocation.setText(post.getPosition());
        //holder.imgPost.setImageResource(post.);
    }

    @Override
    public int getItemCount() {
        return jobPostList.size();
    }

    static class JobPostViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtCompany, txtSalary, txtLocation, txtTime;
        ImageView imgPost;

        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgPost = itemView.findViewById(R.id.imgPost);
        }
    }
}
