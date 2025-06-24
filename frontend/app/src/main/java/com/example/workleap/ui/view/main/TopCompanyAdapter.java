package com.example.workleap.ui.adapter;

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

import java.util.List;

public class TopCompanyAdapter extends RecyclerView.Adapter<TopCompanyAdapter.TopCompanyViewHolder> {

    private final Context context;
    private final List<Company> companyList;

    public TopCompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public TopCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_companies, parent, false);
        return new TopCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCompanyViewHolder holder, int position) {
        Company company = companyList.get(position);

        // Set rank
        holder.txtRank.setText(String.valueOf(position + 1));

        // Set company name
        holder.txtCompanyName.setText(company.getName());

        // Set application count
        /*holder.txtApplicationCount.setText(company.getApplicationCount() + " ứng tuyển");

        // Load logo (nếu có URL logo)
        if (company.getLogoUrl() != null && !company.getLogoUrl().isEmpty()) {
            Picasso.get().load(company.getLogoUrl()).into(holder.imgCompanyLogo);
        } else {
            holder.imgCompanyLogo.setImageResource(R.drawable.sample_logo); // fallback
        }*/
    }

    @Override
    public int getItemCount() {
        return companyList.size();
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
