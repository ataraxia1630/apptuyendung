package com.example.workleap.ui.view.main.message_notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.ConversationUser;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.Message;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageDetailFragment extends Fragment {

    private ArrayList<Message> messages = new ArrayList<Message>();;
    private ConversationViewModel conversationViewModel;
    private MessageAdapter messageAdapter;
    private RecyclerView messageRecyclerView;
    private NavController nav;
    private ImageButton btnBack, btnSend;
    private EditText edtMessage;
    private TextView txtName, txtEmail;
    private ConversationUser currentConversationUser;
    private Conversation currentConversation;
    private UserViewModel userViewModel;
    private User myUser;

    public MessageDetailFragment() {
        // Required empty public constructor
    }

    public static MessageDetailFragment newInstance(String param1, String param2) {
        MessageDetailFragment fragment = new MessageDetailFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           currentConversationUser = (ConversationUser) getArguments().getSerializable("conversationUser");
           currentConversation = (Conversation) getArguments().getSerializable("conversation");
           myUser = (User) getArguments().getSerializable("myUser");
            Log.d("Detailchat", new Gson().toJson(currentConversationUser));
            Log.d("DetailchatcurConv", new Gson().toJson(currentConversation));
           Log.d("conversationUser rc", new Gson().toJson(currentConversationUser));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initiate view model
        conversationViewModel = new ConversationViewModel();
        conversationViewModel.initiateRepository(getContext());
        conversationViewModel.getAllChats();
        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());

        //Find component
        messageRecyclerView = view.findViewById(R.id.recyclerMessages);
        btnBack = view.findViewById(R.id.btnBack);
        btnSend = view.findViewById(R.id.btnSend);
        edtMessage = view.findViewById(R.id.edtMessage);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);

        //Header chat
        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), user -> {
            if(user != null)
            {
                txtName.setText(user.getUsername());
            }
            else
                Log.d("Detailchat", "user null");
        });

        int indexOfUser;
        if(currentConversationUser.getUserId().equals(currentConversationUser.getConversation().getMembers().get(0)))
            indexOfUser = 1;
        else
            indexOfUser = 0;
        if(currentConversationUser.getConversation() != null)
            userViewModel.getUser(currentConversationUser.getConversation().getMembers().get(indexOfUser).getUserId());
        else
            userViewModel.getUser(currentConversation.getMembers().get(indexOfUser).getUserId());



        //Get all messages
        conversationViewModel.getGetMessageOfChatData().observe(getViewLifecycleOwner(), data -> {
            if(messages != null) {
                this.messages.clear();
                this.messages.addAll(data);
                messageAdapter = new MessageAdapter(getContext(),this.messages, myUser.getId(), new MessageAdapter.OnMessageClickListener() {
                    @Override
                    public void onMessageClick(Message message) {
                        // Xử lý khi click vào đoạn chat
                        //Bundle bundle = new Bundle();
                        //conversationViewModel.(jobPost);
                        //bundle.putSerializable("message", message);*/
                        //((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                        //nav.navigate(R.id., bundle); // Navigate to DetailJobPostFragment
                    }
                }
                );

                //Logo message bang usermodel
                userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                    messageAdapter.setImageUrlMap(map);  // Truyền map xuống adapter
                });
                for (Message message : messages) {
                    userViewModel.getLogoJobPostImageUrl(message.getSender().getAvatar()); //dung logopath company lam key
                }

                messageRecyclerView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();

                Log.d("messages", new Gson().toJson(messages));
            }
        });
        conversationViewModel.getErrorResult().observe(getViewLifecycleOwner(), message -> {
            if(message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        });
        conversationViewModel.getMessageByChatId(currentConversationUser.getConversationId());

        //Quay lai chat fragment
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(MessageDetailFragment.this);
            ((NavigationActivity) getActivity()).showBottomNav(true); // Show bottom navigation
            //navController.navigateUp();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", myUser);
            //navController.navigate(R.id.menu_notifications, bundle);
            navController.navigateUp();
        });

        //Send message
        // Gửi message mới khi nhấn nút btnSend
        btnSend.setOnClickListener(v -> {
            String messageContent = edtMessage.getText().toString();
            Message newMessage = null;
            newMessage = new Message(currentConversationUser.getConversationId(), currentConversationUser.getUserId(), messageContent);

            int indexOfSender;
            if(currentConversationUser.getUserId().equals(currentConversationUser.getConversation().getMembers().get(0)))
                indexOfSender = 1;
            else
                indexOfSender = 0;

            //Lay sender, gom 2 truong hop la chat co san hoac vua moi tao, vua moi tao thi conversation se null
            if(currentConversationUser.getConversation() != null)
                newMessage.setSender(currentConversationUser.getConversation().getMembers().get(indexOfSender).getUser());
            else
                newMessage.setSender(currentConversation.getMembers().get(indexOfSender).getUser());

            //Nhan ket qua mes tra ve de them ngay vao danh sach
            conversationViewModel.getSendMessageData().observe(getViewLifecycleOwner(), data ->{
                if(data != null)
                {
                    messages.add(data);
                    messageAdapter.notifyDataSetChanged();
                }
            });

            conversationViewModel.sendMessage(newMessage);
            edtMessage.setText("");
        });

        // Hủy bỏ khi nhấn nút btnCancel
        /*btnCancel.setOnClickListener(v -> {
            dismiss();
        });*/
    }
}