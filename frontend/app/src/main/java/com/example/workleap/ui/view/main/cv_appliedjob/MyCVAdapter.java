package com.example.workleap.ui.view.main.cv_appliedjob;

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
import com.example.workleap.data.model.entity.CV;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyCVAdapter extends RecyclerView.Adapter<MyCVAdapter.CVViewHolder> {
    private List<CV> CVList;
    private OnCVMenuClickListener menuClickListener;
    public MyCVAdapter(List<CV> CVList, OnCVMenuClickListener listener)
    {
        this.CVList = CVList;
        this.menuClickListener = listener;
    }

    public interface OnCVMenuClickListener {
        void onOpen(CV cv);
        void onDownload(CV cv);
        void onRename(CV cv);
        void onDelete(CV cv);
    }

    @NonNull
    @Override
    public CVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cv, parent, false);
        return new CVViewHolder(view, CVList, menuClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CVViewHolder holder, int position) {
        CV cv = CVList.get(position);
        holder.txtTitle.setText(cv.getTitle());

        //size
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

        holder.txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(cv.getUpdatedAt()));
        holder.imgCV.setImageResource(R.drawable.ic_pdf);
    }

    @Override
    public int getItemCount() {
        return CVList.size();
    }

    public void updateList(List<CV> newCVs) {
        this.CVList.clear();
        this.CVList.addAll(newCVs);
        notifyDataSetChanged();
    }

    static class CVViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCV;
        TextView txtTitle, txtSize, txtDate;
        ImageButton btnOption;
        private final List<CV> cvList;
        private final OnCVMenuClickListener listener;
        public CVViewHolder(@NonNull View itemView,  List<CV> cvList, OnCVMenuClickListener listener) {
            super(itemView);

            this.cvList = cvList;
            this.listener = listener;

            imgCV = itemView.findViewById(R.id.imgPost);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnOption = itemView.findViewById(R.id.btnOption);


            itemView.setOnLongClickListener(v -> {
                showPopupMenu(v, getAdapterPosition());
                return true;
            });
            itemView.setOnClickListener( v ->{
                listener.onOpen(cvList.get(getAdapterPosition()));
            });
            btnOption.setOnClickListener( v -> {
                showPopupMenu(v, getAdapterPosition());
            });
        }
        private void showPopupMenu(View view, int position) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.menu_cv_options); // File menu XML

            popup.setOnMenuItemClickListener(item -> {
                CV cv = cvList.get(position);

                if (item.getItemId() == R.id.action_open) {
                    //Toast.makeText(view.getContext(), "Open " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    listener.onOpen(cv);
                    return true;
                } else if (item.getItemId() == R.id.action_download) {
                    listener.onDownload(cv);
                    return true;
                } else if (item.getItemId() == R.id.action_rename) {
                    //Toast.makeText(view.getContext(), "Rename " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    listener.onRename(cv);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    //Toast.makeText(view.getContext(), "Delete " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    listener.onDelete(cv);
                    return true;
                } else {
                    return false;
                }
            });
            popup.show();
        }
    }
}
