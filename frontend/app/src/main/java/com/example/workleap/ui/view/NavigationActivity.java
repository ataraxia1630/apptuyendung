package com.example.workleap.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private AuthViewModel authViewModel;
    private String userRole="";
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

        NavController navController = Navigation.findNavController(NavigationActivity.this, R.id.fragment_container);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        authViewModel.getLoginUser().observe(this, user->
        {
            if(user!=null)
            {
                userRole = user.getRole();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                navController.navigate(R.id.menu_home);
                return true;

            } else if (itemId == R.id.menu_cv_jobpost) {
                if ("applicant".equalsIgnoreCase(userRole)) { //dat userRole sau tranh loi null
                    navController.navigate(R.id.cvFragment);
                } else if ("company".equalsIgnoreCase(userRole)) {
                    navController.navigate(R.id.jobpostFragment);
                } else {
                    Log.e("NavActivity", "role: "+userRole );
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
                if ("applicant".equalsIgnoreCase(userRole)) {
                    navController.navigate(R.id.applicantProfileFragment);
                } else if ("company".equalsIgnoreCase(userRole)) {
                    navController.navigate(R.id.companyProfileFragment);
                } else {
                    Toast.makeText(this, "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
                return true;

            } else
            {
                return false;
            }

        });
    }
}