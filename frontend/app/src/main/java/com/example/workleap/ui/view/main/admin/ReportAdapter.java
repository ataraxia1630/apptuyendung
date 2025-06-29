package com.example.workleap.ui.view.main.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Report;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    public interface OnReportActionListener {
        void onViewTarget(Report report);
        void onViewReporter(Report report);
        void onApprove(Report report);
        void onReject(Report report);
    }

    private List<Report> reportList;
    private OnReportActionListener actionListener;
    private Context context;

    public ReportAdapter(Context context, List<Report> reportList, OnReportActionListener actionListener) {
        this.context = context;
        this.reportList = reportList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reportList.get(position);
        holder.tvReason.setText(report.getReason());

        String target = "Unknown";
        if (report.getReportedUserId() != null) target = "User ID: " + report.getReportedUserId();
        else if (report.getPostId() != null) target = "Post ID: " + report.getPostId();
        else if (report.getJobPostId() != null) target = "JobPost ID: " + report.getJobPostId();
        holder.tvTarget.setText(target);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.tvDate.setText(sdf.format(report.getCreated_at()));

        // Option menu
        holder.btnOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.btnOption);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_report_options, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (actionListener == null) return false;
                if (item.getItemId() == R.id.menu_view_target) {
                    actionListener.onViewTarget(report);
                    return true;
                }if (item.getItemId() == R.id.menu_view_reporter) {
                    actionListener.onViewReporter(report);
                    return true;
                }
                else if (item.getItemId() == R.id.menu_reply_report) {
                    actionListener.onApprove(report);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tvTarget, tvReason, tvDate;
        ImageButton btnOption;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTarget = itemView.findViewById(R.id.tvReportedTarget);
            tvReason = itemView.findViewById(R.id.tvReportReason);
            tvDate = itemView.findViewById(R.id.tvReportedTime);
            btnOption = itemView.findViewById(R.id.btnReportOptions);
        }
    }
}
