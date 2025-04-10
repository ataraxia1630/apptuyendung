package com.example.workleap.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.workleap.R;
import com.example.workleap.data.model.LoginResponse;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import retrofit2.Call;

public class Login extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etEmail = findViewById(R.id.email_edittext);
        etPassword = findViewById(R.id.password_edittext);
        btnLogin = findViewById(R.id.login_button);
        tvSignup = findViewById(R.id.signup_textview);

        btnLogin.setOnClickListener(v -> loginWithEmail());

        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, RoleSelection.class);
            startActivity(intent);
        });

        //ket qua dang nhap
        authViewModel.getLoginResult().observe(this, result -> {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        });
        authViewModel.getLoginUser().observe(this, user -> {
            if(user.getRole().equalsIgnoreCase("user"))
            {
                Intent intent = new Intent(Login.this, UserProfile.class);
                startActivity(intent);
                finish(); // Kết thúc LoginActivity
            }
            else if(user.getRole().equalsIgnoreCase("company"))
            {
                Intent intent = new Intent(Login.this, CompanyProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void loginWithEmail() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.login(email, email, password); //tam thoi lay email lam name
    }
}