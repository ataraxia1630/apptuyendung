package com.example.workleap.ui.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.AuthViewModel;

public class UserProfile extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private TextView tvUserName, tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        tvUserName = findViewById(R.id.usernameText);
        tvEmail = findViewById(R.id.emailText);

        authViewModel.getLoginUser().observe(this, user -> {
            if (user != null) {
                tvUserName.setText(user.getUsername());
                tvEmail.setText(user.getEmail());
            }
        });
    }
}