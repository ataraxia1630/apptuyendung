package com.example.workleap.ui.view.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChooseCVAdapter extends RecyclerView.Adapter<ChooseCVAdapter.CVViewHolder> {

    private final List<CV> cvList;
    private final OnCVSelectedListener listener;

    public interface OnCVSelectedListener {
        void onSelected(CV cv);
    }

    public ChooseCVAdapter(List<CV> cvList, OnCVSelectedListener listener) {
        this.cvList = cvList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_cv, parent, false);
        return new CVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CVViewHolder holder, int position) {
        CV cv = cvList.get(position);
        holder.txtTitle.setText(cv.getTitle());

        // Hiển thị dung lượng
        long sizeInBytes = cv.getSize();
        String readableSize;
        if (sizeInBytes >= 1024 * 1024) {
            readableSize = String.format("%.2f MB", sizeInBytes / (1024.0 * 1024));
        } else if (sizeInBytes >= 1024) {
            readableSize = String.format("%.2f KB", sizeInBytes / 1024.0);
        } else {
            readableSize = sizeInBytes + " B";
        }
        holder.txtSize.setText(readableSize);

        // Hiển thị ngày cập nhật
        holder.txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(cv.getUpdatedAt()));

        // Icon CV
        holder.imgCV.setImageResource(R.drawable.ic_pdf);

        // Click chọn
        holder.itemView.setOnClickListener(v -> listener.onSelected(cv));
    }

    @Override
    public int getItemCount() {
        return cvList.size();
    }

    static class CVViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCV;
        TextView txtTitle, txtSize, txtDate;

        public CVViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCV = itemView.findViewById(R.id.imgPost);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
