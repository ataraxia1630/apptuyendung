package com.example.workleap.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Applicant;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private AuthViewModel authViewModel;
    private ApplicantViewModel applicantViewModel;

    private CompanyViewModel companyViewModel;
    private User user;
    private Company company;
    private Applicant applicant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.navigation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        applicantViewModel = new ViewModelProvider(this).get(ApplicantViewModel.class);
        applicantViewModel.InitiateRepository(getApplicationContext());
        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        companyViewModel.InitiateRepository(getApplicationContext());

        NavController navController = Navigation.findNavController(NavigationActivity.this, R.id.fragment_container);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //lay user tu login activity
        user = (User) getIntent().getSerializableExtra("user");

        if ("applicant".equalsIgnoreCase(user.getRole())) {
            applicantViewModel.getApplicant(user.getApplicantId());
        } else {
            companyViewModel.getCompany(user.getCompanyId());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                navController.navigate(R.id.menu_home);
                return true;

            } else if (itemId == R.id.menu_cv_jobpost) {
                if ("applicant".equalsIgnoreCase( user.getRole())) { //dat userRole sau tranh loi null
                    navController.navigate(R.id.cvFragment);
                } else if ("company".equalsIgnoreCase(user.getRole())) {
                    navController.navigate(R.id.jobpostFragment);
                } else {
                    Log.e("NavActivity", "role: "+ user.getRole() );
                    Toast.makeText(this, "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
                return true;

            } else if (itemId == R.id.menu_statistics) {
                navController.navigate(R.id.menu_statistics);
                return true;

            } else if (itemId == R.id.menu_notifications) {
                navController.navigate(R.id.menu_notifications);
                return true;

            } else if (itemId == R.id.menu_profile) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);

                if ("applicant".equalsIgnoreCase( user.getRole())) {
                    navController.navigate(R.id.applicantProfileFragment, bundle);
                } else if ("company".equalsIgnoreCase( user.getRole())) {
                    navController.navigate(R.id.companyProfileFragment, bundle);
                } else {
                    Toast.makeText(this, "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
                return true;

            }
            return false;
        });
    }
}