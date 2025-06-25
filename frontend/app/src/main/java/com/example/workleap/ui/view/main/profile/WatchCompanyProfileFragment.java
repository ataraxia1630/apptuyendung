package com.example.workleap.ui.view.main.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.JobApplied;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.ui.view.auth.MainActivity;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.view.main.jobpost_post.JobPostAdapter;
import com.example.workleap.ui.view.main.jobpost_post.PostAdapter;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.ConversationViewModel;
import com.example.workleap.ui.viewmodel.JobPostViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchCompanyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchCompanyProfileFragment extends Fragment {
    View view;
    TextView tvCompanyName;
    TextView tvAboutCompany;
    TextView tvCompanyNameInfo, tvEstablishedYear, tvMailInfo, tvPhoneInfo, tvTaxCode;
    ImageView avatar;
    User user, myUser;

    AuthViewModel authViewModel;
    UserViewModel userViewModel;
    JobPostViewModel jobPostViewModel;
    ConversationViewModel conversationViewModel;
    CompanyViewModel companyViewModel;
    NavController nav;
    ImageButton btnFollow, btnChat, btnBack;

    RecyclerView recyclerViewJobPost, recyclerViewPost;
    private JobPostAdapter adapterJobPost;
    private PostAdapter adapterPost;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userId, companyId; //userId of company, companyId of company

    public WatchCompanyProfileFragment() {
        // Required empty public constructor
    }

    public static WatchCompanyProfileFragment newInstance(String param1, String param2) {
        WatchCompanyProfileFragment fragment = new WatchCompanyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString("userId"); //User of this company
            myUser = (User) getArguments().getSerializable("myUser"); //The current user
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_watch_company_profile, container, false);

        //Viewmodel
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());
        companyViewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
        companyViewModel.InitiateRepository(getContext());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());
        conversationViewModel = new ViewModelProvider(requireActivity()).get(ConversationViewModel.class);
        conversationViewModel.initiateRepository(getContext());
        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);

        //Component
        tvCompanyName = (TextView) view.findViewById(R.id.textView2);
        tvAboutCompany = (TextView) view.findViewById(R.id.textViewAboutCompany);
        tvCompanyNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvEstablishedYear = (TextView) view.findViewById(R.id.textViewEstablishedYear);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);
        tvTaxCode = (TextView) view.findViewById(R.id.taxCodeInfo);
        btnChat = view.findViewById(R.id.btnChat);
        btnFollow = view.findViewById(R.id.btnFollow);
        btnBack = view.findViewById(R.id.btnBack);
        recyclerViewJobPost = view.findViewById(R.id.recyclerJobPosts);
        recyclerViewPost = view.findViewById(R.id.recyclerPosts);
        avatar = view.findViewById(R.id.shapeableImageView);

        //observe to Set value from company
        companyViewModel.getGetCompanyData().observe(getViewLifecycleOwner(), company -> {
            if(!isAdded() || getView()==null) return;
            if (company == null) {
                Log.e("ApplicantProfile", "applicant null");
            } else {
                Log.e("ApplicantProfile", "applicant setText");

                tvCompanyName.setText(company.getName());
                tvAboutCompany.setText(company.getDescription());
                tvEstablishedYear.setText(String.valueOf(company.getEstablishedYear()));
                tvTaxCode.setText(company.getTaxcode());
                tvCompanyNameInfo.setText(company.getName());
            }

            //job post list
            jobPostViewModel.getJobPostsByCompany(company.getId());
            jobPostViewModel.getJobPostsByCompanyResult().observe(getViewLifecycleOwner(), result ->
            {
                Log.e("AppliedJobFragment", "getJobPostsByCompanyResult: " + result);
            });
            jobPostViewModel.getJobPostsByCompanyData().observe(getViewLifecycleOwner(), jobPosts ->
            {
                if(jobPosts == null)
                {
                    Log.e("watchcompanyprofile", "jobposts NULL");
                    return;
                }else
                {
                    Log.e("eeeee", String.valueOf(jobPosts.size()));
                }
                // Setup RecyclerView
                recyclerViewJobPost.setLayoutManager(new LinearLayoutManager(getContext()));
                adapterJobPost = new JobPostAdapter(jobPosts, new JobPostAdapter.OnJobPostClickListener() {
                    @Override
                    public void onJobPostClick(JobPost jobPost) {
                        // Handle item click
                        Bundle bundle = new Bundle();
                        jobPostViewModel.setCurrentJobPost(jobPost);
                        bundle.putSerializable("user", user);
                        ((NavigationActivity) getActivity()).showBottomNav(false); // Hide bottom navigation
                        nav.navigate(R.id.HomeJobPostFragment, bundle); // Navigate to DetailJobPostFragment
                    }
                });
                recyclerViewJobPost.setAdapter(adapterJobPost);
            });
        });
        companyViewModel.getGetCompanyResult().observe(getViewLifecycleOwner(), result ->{
            if(!isAdded() || getView()==null) return;
            if(result!=null)
                Log.e("company profile", result);
            else
                Log.e("company profile", "update company result null" );
        });





        //Set value from user, call api
        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                user = data;
                //Goi api get following, can goi sau khi da co user cua company
                // Lay following to check button status
                userViewModel.getFollowing(myUser.getId());

                //get following data can goi sau khi da co user qua api get user
                //observe check following to set button follow
                userViewModel.getGetFollowingData().observe(getViewLifecycleOwner(), dataFollowing -> {
                    if(dataFollowing != null)
                    {
                        // Nếu data chứa userIdOfCompany thì đặt btnFollow text thành "Followed" và vô hiệu hóa nó
                        boolean isFollowing = false;
                        for (Follower following : dataFollowing) {
                            String followedUserId = following.getFollowedId();
                            if (followedUserId.equals(user.getId())) {
                                isFollowing = true;
                                break;
                            }
                        }
                        if (isFollowing) {
                            //dat lai scr image
                            btnFollow.setImageResource(R.drawable.ic_followed);
                            btnFollow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_light));
                        } else {
                            //Dat lai scr image
                            btnFollow.setImageResource(R.drawable.ic_follow);
                            btnFollow.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                    else
                    {
                        Log.d("getFollowing", "null");
                    }
                });
                userViewModel.getGetFollowingResult().observe(getViewLifecycleOwner(), result -> {
                    if(result != null)
                        Log.d("result get following ", result.toString());
                    else
                        Log.d("result get following ", "null");
                });

                tvMailInfo.setText(user.getEmail());
                tvPhoneInfo.setText(user.getPhoneNumber());

                //Lay avatar
                //Observe
                userViewModel.getUrlAvatarResult().observe(getViewLifecycleOwner(), result -> {
                    if(result != null)
                        Log.d("CompanyProfile avatar", result);
                    else
                        Log.d("Companyprofile avatar", "getUrlAvatarResult NULL");
                });
                userViewModel.getUrlAvatarData().observe(getViewLifecycleOwner(), dataImage -> {
                    if(dataImage != null)
                    {
                        Glide.with(this.getContext()).load(dataImage).into(avatar);
                        Log.d("ApplicantProfile avatar", "Set avatar success");
                    }
                    else
                        Log.d("ApplicantProfile avatar", "getUrlAvatarData NULL");
                });
                //Check and get avatar
                if(user.getAvatar() != null)
                {
                    //Load avatar from database
                    userViewModel.getAvatarUrl(user.getAvatar());
                }
                else
                    Log.d("CompanyProfile avatar", "user avatar null");


                //Lay ra company
                companyId = data.getCompanyId();
                companyViewModel.getCompany(companyId);
            }
            else
                Log.d("WatchCpnProfileFragment", "user null");
        });
        userViewModel.getUser(userId);


        //Follow observe and click handle
        userViewModel.getToggleFollowResult().observe(getViewLifecycleOwner(), result -> {
            if(result!=null)
            {
                Log.e("follow", result);
                //Lay following to update button status
                userViewModel.getFollowing(myUser.getId());
            }
            else
                Log.e("follow", "follow result null" );
        });
        btnFollow.setOnClickListener(v -> {
            userViewModel.toggleFollow(userId);
        });

        //Chat
        btnChat.setOnClickListener(v -> {
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
                    Bundle bundle = new Bundle();
                    Log.d("Chat company detail", new Gson().toJson(data));
                    bundle.putSerializable("conversationUser", data.getMembers().get(1));
                    bundle.putSerializable("conversation", data);
                    nav.navigate(R.id.messageDetailFragment, bundle);
                }
                else
                    Log.d("conversation", "null");
            });
            //Tim thong tin day du created chat de cho vao bundle
            conversationViewModel.createChat(new FriendIdRequest(user.getId()));

        });

        //Back
        btnBack.setOnClickListener(v ->
        {
            NavController nav = NavHostFragment.findNavController(this);
            nav.navigateUp();
        });
        return view;
    }
}