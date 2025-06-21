package com.example.workleap.ui.view.main.message_notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    private ArrayList<Conversation> conversations = new ArrayList<Conversation>();;
    private ConversationViewModel conversationViewModel;
    private ConversationAdapter conversationAdapter;
    private RecyclerView conversationRecyclerView;
    private ImageButton btnNotification;

    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initiate view model
        conversationViewModel = new ConversationViewModel();
        conversationViewModel.initiateRepository(getContext());
        conversationViewModel.getAllChats();

        //Find component
        conversationRecyclerView = view.findViewById(R.id.recyclerConversationList);
        btnNotification = view.findViewById(R.id.btnNotification);

        //Get all conversations
        conversationViewModel.getAllChatsData().observe(getViewLifecycleOwner(), conversations -> {
            if(conversations != null) {
                this.conversations.clear();
                this.conversations.addAll(conversations);
                conversationAdapter = new ConversationAdapter(getContext(),this.conversations, new ConversationAdapter.OnConversationClickListener() {
                    @Override
                    public void onConversationClick(Conversation conversation) {
                        // Xử lý khi click vào đoạn chat
                        /*Bundle bundle = new Bundle();
                        conversationViewModel.(jobPost);
                        bundle.putSerializable("jobPost", jobPost);
                        bundle.putSerializable("user", user);
                        ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                        nav.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment*/
                            }
                        }
                );
                conversationRecyclerView.setAdapter(conversationAdapter);
                conversationAdapter.notifyDataSetChanged();

                Log.d("conversations", new Gson().toJson(conversations));
            }
        });
        conversationViewModel.getErrorResult().observe(getViewLifecycleOwner(), message -> {
            if(message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        });
        conversationViewModel.getAllChats();


        btnNotification.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(MessageFragment.this);
            navController.navigate(R.id.action_messageFragment_to_notificationFragment);
        });
    }
}