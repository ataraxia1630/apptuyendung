package com.example.workleap.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.workleap.ui.viewmodel.AuthViewModel;


public class ApplicantSignup extends AppCompatActivity {

    private AuthViewModel authViewModel;

    private EditText etFullName, etEmail, etPassword, etPhoneNumber;
    private Button btnSignUp;
    private TextView tvLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_applicant_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etFullName = findViewById(R.id.editTextFullName);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etPhoneNumber = findViewById(R.id.editTextPhone);
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvLogIn = findViewById(R.id.textViewLogIn);

        btnSignUp.setOnClickListener(v -> {
            Signup();
        });

        //ket qua dang ki
        authViewModel.getRegisterResult().observe(this, result -> {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

            if (result.contains("successfully")) {
                Log.e("ApplicantSignupActivity", "register successfully");
                navigateToLogin();
            }
        });

        tvLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }
    private void Signup() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.register(fullName, password, email, phone, "APPLICANT");
    }
    private void navigateToLogin(){
        Intent intent = new Intent(ApplicantSignup.this, Login.class);
        startActivity(intent);
        finish();  // Đảm bảo không quay lại màn hình đăng ký khi nhấn nút quay lại
    }

}