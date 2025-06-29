package com.example.workleap.ui.view.main.message_notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Notification;
import com.example.workleap.ui.view.main.message_notification.NotificationAdapter;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList = new ArrayList<>();

    private ConversationViewModel conversationViewModel;
    private ProgressBar progressCenterLoading;

    public NotificationFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NotificationAdapter(notificationList, notification -> {
            // Xử lý khi click vào notification
        });
        recyclerView.setAdapter(adapter);

        //Loading
        progressCenterLoading = view.findViewById(R.id.progressCenterLoading);
        progressCenterLoading.setVisibility(View.VISIBLE);

        conversationViewModel = new ViewModelProvider(requireActivity()).get(ConversationViewModel.class);
        conversationViewModel.initiateRepository(getContext());
        conversationViewModel.getAllNotification();
        conversationViewModel.getAllNotificationData().observe(getViewLifecycleOwner(), notifications -> {

            progressCenterLoading.setVisibility(View.GONE);

            if(notifications==null)
            {
                Log.e("NotificationFragment","getAllNotificationData NULL");
                return;
            }
            notificationList.clear();
            notificationList.addAll(notifications);

            adapter.notifyDataSetChanged();
        });



        // Thêm swipe để xoá
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // Không dùng drag
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                String notificationId = notificationList.get(position).getId();

                notificationList.remove(position);
                adapter.notifyItemRemoved(position);

                conversationViewModel.deleteNotification(notificationId);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
