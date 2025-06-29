package com.example.workleap.ui.view.main.message_notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.Follower;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowingFragment extends Fragment {

    private ArrayList<Follower> followerUsers = new ArrayList<Follower>();;
    private UserViewModel userViewModel;
    private FollowerAdapter followerUserAdapter;
    private RecyclerView followerRecyclerView;
    private ImageButton btnBack;
    private ProgressBar progressCenterLoading;
    private NavController nav;
    private User myUser;

    public FollowingFragment() {
        // Required empty public constructor
    }

    public static FollowingFragment newInstance(String param1, String param2) {
        FollowingFragment fragment = new FollowingFragment();
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
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initiate view model
        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());
        userViewModel = new UserViewModel();
        userViewModel.InitiateRepository(getContext());

        //Find component
        followerRecyclerView = view.findViewById(R.id.recyclerFollowingList);
        btnBack = view.findViewById(R.id.btnBack);
        progressCenterLoading = view.findViewById(R.id.progressCenterLoading);
        //Loading
        progressCenterLoading.setVisibility(View.VISIBLE);

        //Get all followers
        userViewModel.getGetFollowingData().observe(getViewLifecycleOwner(), data -> {
            progressCenterLoading.setVisibility(View.GONE);
            if(followerUsers != null) {
                this.followerUsers.clear();
                this.followerUsers.addAll(data);
                followerUserAdapter = new FollowerAdapter(getContext(), followerUsers, new FollowerAdapter.OnFollowerClickListener() {
                    @Override
                    public void onFollowerClick(Follower followerUser) {
                        // Xử lý khi click vào follower, vao trang company hoac applicant profile
                        Bundle bundle = new Bundle();
                        if(followerUser.getFollowedUser().getCompanyId() != null)
                        {
                            //Company
                            bundle.putSerializable("userId", followerUser.getFollowedId());
                            bundle.putSerializable("myUser", myUser);
                            nav.navigate(R.id.watchCompanyProfileFragment, bundle);
                        }
                        else
                        {
                            //Applicant
                            bundle.putSerializable("user", followerUser.getFollowedUser());
                            bundle.putSerializable("myUser", myUser);
                            nav.navigate(R.id.watchApplicantProfileFragment, bundle);
                        }
                    }
                }
                );


                //Logo follower bang usermodel
                userViewModel.getLogoJobPostUrlMap().observe(getViewLifecycleOwner(), map -> {
                    followerUserAdapter.setImageUrlMap(map);  // Truyền map xuống adapter
                });
                for (Follower followerUser : followerUsers) {
                    userViewModel.getLogoJobPostImageUrl(followerUser.getFollowedUser().getAvatar()); //dung logopath lam key
                }


                followerRecyclerView.setAdapter(followerUserAdapter);
                followerUserAdapter.notifyDataSetChanged();

                Log.d("followerUsers", new Gson().toJson(followerUsers));
            }
        });
        userViewModel.getGetFollowingResult().observe(getViewLifecycleOwner(), message -> {
            if(message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        });
        userViewModel.getFollowing(myUser.getId());


        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(FollowingFragment.this);
            navController.navigateUp();
        });
    }
}