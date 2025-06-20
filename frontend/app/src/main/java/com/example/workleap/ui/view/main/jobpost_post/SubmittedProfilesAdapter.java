package com.example.workleap.ui.view.main.jobpost_post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobApplied;

import java.util.List;

public class SubmittedProfilesAdapter extends RecyclerView.Adapter<SubmittedProfilesAdapter.JobAppliedViewHolder> {

    private final List<JobApplied> jobAppliedList;
    private final OnSubmittedProfilesMenuClickListener menuClickListener;

    public SubmittedProfilesAdapter(List<JobApplied> jobAppliedList, OnSubmittedProfilesMenuClickListener listener) {
        this.jobAppliedList = jobAppliedList;
        this.menuClickListener = listener;
    }

    public interface OnSubmittedProfilesMenuClickListener {
        void onOpen(JobApplied jobApplied);
        void onDownload(JobApplied jobApplied);
        void onApprove(JobApplied jobApplied);
        void onDismiss(JobApplied jobApplied);
    }

    @NonNull
    @Override
    public JobAppliedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submitted_profile, parent, false);
        return new JobAppliedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAppliedViewHolder holder, int position) {
        JobApplied jobApplied = jobAppliedList.get(position);

        holder.imgAvatar.setImageResource(R.drawable.sample_avatar);
        holder.txtApplicantName.setText("dang");
        holder.txtTitle.setText("cv cua dang");
        holder.txtStatus.setText(jobApplied.getStatus());

        holder.itemView.setOnClickListener(v -> menuClickListener.onOpen(jobApplied));
        holder.btnOption.setOnClickListener(v -> holder.showPopupMenu(v, jobApplied));
    }

    @Override
    public int getItemCount() {
        return jobAppliedList.size();
    }

    public void updateList(List<JobApplied> newList) {
        jobAppliedList.clear();
        jobAppliedList.addAll(newList);
        notifyDataSetChanged();
    }

    class JobAppliedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtApplicantName, txtTitle, txtStatus;
        ImageButton btnOption;

        public JobAppliedViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtApplicantName = itemView.findViewById(R.id.txtApplicantName);
            txtTitle = itemView.findViewById(R.id.txtCVTitle);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnOption = itemView.findViewById(R.id.imgOptions);
        }


        void showPopupMenu(View view, JobApplied jobApplied) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.menu_job_applied_options);

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.action_open) {
                    menuClickListener.onOpen(jobApplied);
                    return true;
                } else if (id == R.id.action_download) {
                    menuClickListener.onDownload(jobApplied);
                    return true;
                } else if (id == R.id.action_approve) {
                    menuClickListener.onApprove(jobApplied);
                    return true;
                } else if (id == R.id.action_dismiss) {
                    menuClickListener.onDismiss(jobApplied);
                    return true;
                } else {
                    return false;
                }
            });

            popup.show();
        }
    }
}
