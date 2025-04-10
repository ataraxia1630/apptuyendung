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
import com.example.workleap.ui.viewmodel.AuthViewModel;

public class CompanySignup extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText etEmail, etPassword, etCompanyName, etAddress, etEstablishedYear, etTaxCode;
    private Button btnRegister;
    private TextView tvLoginRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.company_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etCompanyName = findViewById(R.id.etCompanyName);
        etAddress = findViewById(R.id.etAddress);
        etEstablishedYear = findViewById(R.id.etEstablishedYear);
        etTaxCode = findViewById(R.id.etTaxCode);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        btnRegister.setOnClickListener(v -> {
            Signup();
        });

        //ket qua dang ki
        authViewModel.getRegisterResult().observe(this, result -> {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

            if (result.contains("success")) {
                navigateToLogin();
            }
        });

        tvLoginRedirect.setOnClickListener(v -> {
            navigateToLogin();
        });

    }
    private void Signup() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etCompanyName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String year = etEstablishedYear.getText().toString().trim();
        String tax = etTaxCode.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || address.isEmpty() || year.isEmpty() || tax.isEmpty()) {
            Toast.makeText(CompanySignup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.register(name, password, email, "", "COMPANY");
    }
    private void navigateToLogin(){
        Intent intent = new Intent(CompanySignup.this, Login.class);
        startActivity(intent);
        finish();  // Đảm bảo không quay lại màn hình đăng ký khi nhấn nút quay lại
    }
}