package com.example.workleap.ui.view.main.jobpost_post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {
    private List<JobPost> jobPostList;
    private JobPostViewModel jobPostViewModel;

    public JobPostAdapter(List<JobPost> jobPostList, JobPostViewModel jobPostViewModel) {
        this.jobPostList = jobPostList;
        this.jobPostViewModel = jobPostViewModel;
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
        //holder.imgPost.setImageResource(post.);

        // Thêm PopupMenu cho btnOption
        holder.btnOption.setOnClickListener(v -> {

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
                        jobPostViewModel.deleteJobPost(post.getId());

                        //Xoa lap tuc tren danh sach
                        jobPostList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, jobPostList.size());
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
        TextView txtTitle, txtCompany, txtSalary, txtLocation, txtTime;
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
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
