package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.AuthViewModel;

public class ApplicantSignupFragment extends Fragment {

    private AuthViewModel authViewModel;

    private EditText etFullName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    private Button btnSignUp;
    private TextView tvLogIn;

    EditProfileDialogFragment dialog;

    public ApplicantSignupFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_applicant_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        etFullName = view.findViewById(R.id.editTextFullName);
        etEmail = view.findViewById(R.id.editTextEmail);
        etPassword = view.findViewById(R.id.editTextPassword);
        etConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        etPhoneNumber = view.findViewById(R.id.editTextPhone);
        btnSignUp = view.findViewById(R.id.buttonSignUp);
        tvLogIn = view.findViewById(R.id.textViewLogIn);

        btnSignUp.setOnClickListener(v -> signup());

        tvLogIn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        });

        authViewModel.getRegisterResult().observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            if (result.contains("successfully")) {
                Log.d("ApplicantSignupFragment", "Register success");
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });
    }

    private void signup() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.register(fullName, password, confirmPassword, email, phone, "APPLICANT");
    }
}