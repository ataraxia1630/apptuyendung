package com.example.workleap.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.repository.PreferencesManager;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private UserViewModel userViewModel;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.e("hello", "heelo");
        //Check token
        PreferencesManager preferencesManager = new PreferencesManager(this);
        if (preferencesManager.isTokenValid()) {
            String userId = preferencesManager.getUserId();
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.InitiateRepository(this);
            String token = preferencesManager.getToken();
            Log.e("hi", token);
            Log.e("userId", userId);

            userViewModel.getGetUserResult().observe(this, res -> {
                if(res != null)
                Log.e("getUserResult", res);
                else
                    Log.e("result", "no result");
            });

            userViewModel.getGetUserData().observe(this, user -> {
                if (user == null) {
                    Log.e("AutoLogin", "user null");
                } else {
                    Intent intent = new Intent(this, NavigationActivity.class);
                    Log.e("Welcome", "wellcome");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            });
            userViewModel.getUser(userId);
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }
}