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

public class CompanyProfile extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private TextView tvCompanyName, tvCompanyEmail, tvCompanyAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.company_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        tvCompanyName = findViewById(R.id.companyName);
        tvCompanyEmail = findViewById(R.id.companyEmail);
        tvCompanyAddress = findViewById(R.id.companyAddress);

        authViewModel.getLoginUser().observe(this, company -> {
            if (company != null) {
                tvCompanyName.setText(company.getUsername());
                tvCompanyEmail.setText(company.getEmail());
            }
        });
    }
}