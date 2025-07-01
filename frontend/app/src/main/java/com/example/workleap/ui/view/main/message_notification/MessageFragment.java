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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.ConversationUser;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    private ArrayList<ConversationUser> conversationUsers = new ArrayList<ConversationUser>();;
    private ConversationViewModel conversationViewModel;
    private ConversationUserAdapter conversationUserAdapter;
    private RecyclerView conversationRecyclerView;
    private ImageButton btnNotification, btnFollowing;
    private ProgressBar progressCenterLoading;
    private NavController nav;
    private UserViewModel userViewModel;
    private User myUser;

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
            myUser = (User) getArguments().getSerializable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initiate view model
        conversationViewModel = new ConversationViewModel();
        conversationViewModel.initiateRepository(getContext());
        //conversationViewModel.getAllChats();
        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());

        //Find component
        conversationRecyclerView = view.findViewById(R.id.recyclerConversationList);
        btnNotification = view.findViewById(R.id.btnNotification);
        btnFollowing = view.findViewById(R.id.btnFollowing);
        progressCenterLoading = view.findViewById(R.id.progressCenterLoading);

        //Loading
        progressCenterLoading.setVisibility(View.VISIBLE);

        conversationUserAdapter = new ConversationUserAdapter(getContext(), conversationUsers, new ConversationUserAdapter.OnConversationClickListener() {
            @Override
            public void onConversationClick(ConversationUser conversationUser) {
                // Xử lý khi click vào đoạn chat
                Bundle bundle = new Bundle();
                bundle.putSerializable("conversationUser", conversationUser);
                bundle.putSerializable("myUser", myUser);
                ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                nav.navigate(R.id.messageDetailFragment, bundle); // Navigate to DetailJobPostFragment*/
            }
        }
        );

        //Get all conversations
        conversationViewModel.getAllChatsData().observe(getViewLifecycleOwner(), data -> {
            progressCenterLoading.setVisibility(View.GONE);

            if(conversationUsers != null) {
                this.conversationUsers.clear();
                this.conversationUsers.addAll(data);
                conversationUserAdapter.notifyDataSetChanged();

                //Logo chat bang usermodel
                userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                    conversationUserAdapter.setImageUrlMap(map);  // Truyền map xuống adapter
                });
                for (ConversationUser conversationUser : conversationUsers) {
                    int indexOfUser;
                    if(conversationUser.getUserId().equals(conversationUser.getConversation().getMembers().get(0).getUserId()))
                        indexOfUser = 1;
                    else
                        indexOfUser = 0;
                    userViewModel.getLogoJobPostImageUrl(conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getAvatar()); //dung logopath lam key
                    Log.d("indexuser", conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getUsername());
                    Log.d("indexuser", conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getAvatar());
                }


                conversationRecyclerView.setAdapter(conversationUserAdapter);
                conversationUserAdapter.notifyDataSetChanged();

                Log.d("conversationUsers", new Gson().toJson(conversationUsers));
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

        btnFollowing.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(MessageFragment.this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", myUser);
            navController.navigate(R.id.followingFragment, bundle);
        });
    }
}