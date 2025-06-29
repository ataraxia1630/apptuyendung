package com.example.workleap.ui.view.main.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.auth.MainActivity;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminProfileFragment extends Fragment {

    private ShapeableImageView avatarImageView;
    private TextView tvName, tvEmail, tvPhone;
    private ImageButton btnOptions;
    private TabLayout tabLayoutUserType;
    private SearchView searchViewUser;
    private RecyclerView recyclerUserList;

    private UserViewModel userViewModel;
    private ApplicantViewModel applicantViewModel;
    private CompanyViewModel companyViewModel;
    private AuthViewModel authViewModel;
    private UserListAdapter userAdapter;
    private List<User> allUsers = new ArrayList<>();

    private String currentRoleFilter = "applicant"; // or "company"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init view
        avatarImageView = view.findViewById(R.id.shapeableImageView);
        tvName = view.findViewById(R.id.tvApplicantName);
        tvEmail = view.findViewById(R.id.emailInfo);
        tvPhone = view.findViewById(R.id.phoneInfo);
        btnOptions = view.findViewById(R.id.btnOptions);
        tabLayoutUserType = view.findViewById(R.id.tabLayoutUserType);
        searchViewUser = view.findViewById(R.id.searchViewUser);
        recyclerUserList = view.findViewById(R.id.recyclerUserList);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());
        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
        applicantViewModel.InitiateRepository(getContext());
        companyViewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
        companyViewModel.InitiateRepository(getContext());

        User currentUser = (User) getArguments().getSerializable("user");  // Giả sử bạn có phương thức này
        if (currentUser != null) {
            tvName.setText(currentUser.getUsername());
            tvEmail.setText(currentUser.getEmail());
            tvPhone.setText(currentUser.getPhoneNumber());

            Glide.with(this)
                    .load(currentUser.getAvatar())
                    .placeholder(R.drawable.ic_admin)
                    .into(avatarImageView);
        }

        // Option menu
        btnOptions.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), btnOptions);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_options, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_setting) {
                    return true;
                }
                else if (item.getItemId() == R.id.menu_logout) {
                    authViewModel.logout();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                    return true;
                }
                return false;
            });
            popup.show();
        });

        // Tab setup
        tabLayoutUserType.addTab(tabLayoutUserType.newTab().setText("Applicant"));
        tabLayoutUserType.addTab(tabLayoutUserType.newTab().setText("Company"));

        tabLayoutUserType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                currentRoleFilter = (tab.getPosition() == 0) ? "applicant" : "company";
                loadUsers(currentRoleFilter);
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // RecyclerView setup
        recyclerUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserListAdapter(allUsers);
        recyclerUserList.setAdapter(userAdapter);

        // Search
        searchViewUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                filterUsers(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });

        // Load initial users
        loadUsers("applicant");
    }

    private void loadUsers(String role) {
        /*if(currentRoleFilter=="applicant")
        {
            applicantViewModel.getAllApplicant();
        }else{
            companyViewModel.geta,
        }
        userViewModel.get(role).observe(getViewLifecycleOwner(), users -> {

        });
        allUsers.clear();
        if (users != null) {
            allUsers.addAll(users);
        }
        userAdapter.notifyDataSetChanged();*/
    }

    private void filterUsers(String query) {
        List<User> filtered = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getUsername().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                filtered.add(user);
            }
        }
        userAdapter.updateList(filtered);
    }
}
