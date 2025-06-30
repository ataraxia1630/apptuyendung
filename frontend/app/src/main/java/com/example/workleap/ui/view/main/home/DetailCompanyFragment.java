package com.example.workleap.ui.view.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCompanyFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;
    private UserViewModel userViewmodel;
    private CompanyViewModel companyViewmodel;
    private ConversationViewModel conversationViewModel;
    private Company currentCompany;
    private JobPost currentJobPost;
    private TextView txtAboutUs, txtContactInfor;
    private Button btnFollow;
    private ImageButton btnChat;
    private NavController nav;
    private Bundle bundle;
    private String userIdOfCompany;
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

    public DetailCompanyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailCompanyFragment newInstance(String param1, String param2) {
        DetailCompanyFragment fragment = new DetailCompanyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());
        userViewmodel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewmodel.InitiateRepository(getContext());
        companyViewmodel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
        companyViewmodel.InitiateRepository(getContext());
        conversationViewModel = new ConversationViewModel();
        conversationViewModel.initiateRepository(getContext());

        //Lay jobpost, company hien tai
        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");
        currentCompany = currentJobPost.getCompany();
        User myUser = (User) getArguments().getSerializable("user");

        //find component
        txtAboutUs = view.findViewById(R.id.txtAboutUs);
        txtContactInfor = view.findViewById(R.id.txtContactInformation);
        btnChat = view.findViewById(R.id.btnChat);
        btnFollow = view.findViewById(R.id.btnFollow);

        if (currentJobPost != null) {
            //JobPost jobPost = (JobPost) getArguments().getSerializable("jobPost");
            txtAboutUs.setText(currentCompany.getDescription());
            txtContactInfor.setText(currentCompany.getTaxcode());
            // TODO: Add listeners or bind ViewModel here
        }


        //Check following to set button follow
        //Get following
        userViewmodel.getGetFollowingData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                // Nếu data chứa userIdOfCompany thì đặt btnFollow text thành "Followed" và vô hiệu hóa nó
                boolean isFollowing = false;
                for (Follower following : data) {
                    String followedUserId = following.getFollowedId();
                    if (followedUserId.equals(userIdOfCompany)) {
                        isFollowing = true;
                        break;
                    }
                }
                if (isFollowing) {
                    btnFollow.setText("UnFollow");
                    btnFollow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_light));
                } else {
                    btnFollow.setText("Follow");
                    btnFollow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
                }
            }
            else
            {
                Log.d("getFollowing", "null");
            }
        });
        userViewmodel.getGetFollowingResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("result get following ", result.toString());
            else
                Log.d("result get following ", "null");
        });


        //Get userId of company to check and follow
        companyViewmodel.getGetCompanyData().observe(getViewLifecycleOwner(), data ->
        {
            if(data != null)
            {
                Log.d("getCompanyUser", data.toString());
                userIdOfCompany = data.getUser().get(0).getId();

                //Lay following to check button status
                userViewmodel.getFollowing(myUser.getId());
            }
            else
                Log.d("getCompanyUser", "null");
        });


        //Follow
        companyViewmodel.getGetCompanyResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("result get company ", result.toString());
            else
                Log.d("result get company ", "null");
        });
        companyViewmodel.getCompany(currentCompany.getId());
        //observe follow result
        userViewmodel.getToggleFollowResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
            {
                Log.d("result toggle follow ", result.toString());
                //Update status for button follow
                userViewmodel.getFollowing(myUser.getId());
            }
            else
                Log.d("result toggle follow ", "null");
        });
        //click handle
        btnFollow.setOnClickListener(v -> {
            userViewmodel.toggleFollow(userIdOfCompany);
        });


        //Nhan id created chat
        conversationViewModel.getSingleChatData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                conversationViewModel.getChatById(data.getId());
            }
            else
                Log.d("conversation", "null");
        });
        conversationViewModel.getCreatedChatData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                bundle = new Bundle();
                Log.d("Chat company detail", new Gson().toJson(data));
                bundle.putSerializable("conversationUser", data.getMembers().get(1));
                bundle.putSerializable("conversation", data);
                nav.navigate(R.id.messageDetailFragment, bundle);
            }
            else
                Log.d("conversation", "null");
        });
        //Chat
        btnChat.setOnClickListener(v -> {
            //Tim thong tin day du created chat de cho vao bundle
            if(!(myUser.getId().equals(userIdOfCompany)))
                conversationViewModel.createChat(new FriendIdRequest(userIdOfCompany));
        });
    }

}