package com.example.workleap.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.CV;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyCVAdapter extends RecyclerView.Adapter<MyCVAdapter.CVViewHolder> {
    private List<CV> CVList;

    public MyCVAdapter(List<CV> CVList) {
        this.CVList = CVList;
    }

    @NonNull
    @Override
    public CVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cv, parent, false);
        return new CVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CVViewHolder holder, int position) {
        CV cv = CVList.get(position);
        holder.txtTitle.setText(cv.getTitle());
        //holder.txtSize.setText(cv.get().getName());
        holder.txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(cv.getUpdatedAt()));
        holder.imgCV.setImageResource(R.drawable.ic_pdf);
    }

    @Override
    public int getItemCount() {
        return CVList.size();
    }

    static class CVViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCV;
        TextView txtTitle, txtSize, txtDate;
        ImageButton btnOption;

        public CVViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCV = itemView.findViewById(R.id.imgPost);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
