package com.example.workleap.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workleap.R;

public class RoleSelection extends AppCompatActivity {

    private Button buttonJobSeeker, buttonCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_role_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.role_selection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonJobSeeker = findViewById(R.id.buttonJobSeeker);
        buttonCompany = findViewById(R.id.buttonCompany);

        buttonJobSeeker.setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelection.this, UserSignup.class);
            startActivity(intent);
        });

        buttonCompany.setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelection.this, CompanySignup.class);
            startActivity(intent);
        });
    }
}