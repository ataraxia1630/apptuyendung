package com.example.workleap.ui.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.response.TopCompanyResponse;

import java.util.ArrayList;
import java.util.List;

public class TopCompanyAdapter extends RecyclerView.Adapter<TopCompanyAdapter.TopCompanyViewHolder> {

    private final Context context;
    private final List<TopCompanyResponse> topCompanyList;

    public TopCompanyAdapter(Context context, List<TopCompanyResponse> topCompanyList) {
        this.context = context;
        this.topCompanyList = new ArrayList<>(topCompanyList);
    }

    @NonNull
    @Override
    public TopCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_companies, parent, false);
        return new TopCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCompanyViewHolder holder, int position) {
        TopCompanyResponse company = topCompanyList.get(position);

        // Set rank
        holder.txtRank.setText(String.valueOf(position + 1));

        // Set company name
        holder.txtCompanyName.setText(company.getCompany().getName());

        // Set application count
        holder.txtApplicationCount.setText("Total: "+company.getApplicationCount() + " applications");
    }

    @Override
    public int getItemCount() {
        return topCompanyList.size();
    }
    public void setData(List<TopCompanyResponse> newList) {
        topCompanyList.clear();
        topCompanyList.addAll(newList);
        notifyDataSetChanged();
    }
    public static class TopCompanyViewHolder extends RecyclerView.ViewHolder {
        TextView txtRank, txtCompanyName, txtApplicationCount;
        ImageView imgCompanyLogo;

        public TopCompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRank = itemView.findViewById(R.id.txtRank);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtApplicationCount = itemView.findViewById(R.id.txtApplicationCount);
            imgCompanyLogo = itemView.findViewById(R.id.imgCompanyLogo);
        }
    }
}
