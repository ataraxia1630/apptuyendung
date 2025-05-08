package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
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

public class CompanySignupFragment extends Fragment {

    private AuthViewModel authViewModel;

    private EditText etEmail, etPassword, etConfirmPassword, etCompanyName, etAddress, etEstablishedYear, etTaxCode, etPhoneNumber;
    private Button btnRegister;
    private TextView tvLoginRedirect;

    public CompanySignupFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        etCompanyName = view.findViewById(R.id.etCompanyName);
        etAddress = view.findViewById(R.id.etAddress);
        etEstablishedYear = view.findViewById(R.id.etEstablishedYear);
        etTaxCode = view.findViewById(R.id.etTaxCode);
        etPhoneNumber = view.findViewById(R.id.etPhone);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvLoginRedirect = view.findViewById(R.id.tvLoginRedirect);

        btnRegister.setOnClickListener(v -> signup());

        tvLoginRedirect.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.loginFragment));

        authViewModel.getRegisterResult().observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            if (result.contains("success")) {
                Log.d("CompanySignupFragment", "Register success");
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });
    }

    private void signup() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String name = etCompanyName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String year = etEstablishedYear.getText().toString().trim();
        String tax = etTaxCode.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(name) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(year) || TextUtils.isEmpty(tax)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.register(name, password, confirmPassword, email, phone, "COMPANY");
    }
}