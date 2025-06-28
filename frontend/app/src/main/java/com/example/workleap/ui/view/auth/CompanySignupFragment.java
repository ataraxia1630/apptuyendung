package com.example.workleap.ui.view.auth;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;

public class CompanySignupFragment extends Fragment {

    private AuthViewModel authViewModel;
    private CompanyViewModel companyViewModel;

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
        companyViewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
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
            if (!isAdded() || getView() == null) return;

            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            if (result.contains("successfully")) {
                Log.d("ApplicantSignupFragment", "Register success");
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });

        // Observe
        authViewModel.getCheckExistData().observe(getViewLifecycleOwner(), data -> {
            if (data == 1) {
                String email = etEmail.getText().toString().trim();
                authViewModel.sendOtp(email);
            }
            else
                Toast.makeText(getContext(), "User already exists or wrong confirm password", Toast.LENGTH_LONG).show();
        });
        authViewModel.getCheckExistResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                Log.d("ApplicantSignupFragment", "Check exist result:" + result);
            } else {
                Toast.makeText(getContext(), "User already exists, please login or use another account", Toast.LENGTH_LONG).show();
            }
        });

        authViewModel.getSendOtpResult().observe(getViewLifecycleOwner(), result -> {
            if (!isAdded() || getView() == null) return;

            if (result != null) {
                String email = etEmail.getText().toString().trim();
                showOtpDialog(email);
            }
        });

        authViewModel.getVerifyOtpData().observe(getViewLifecycleOwner(), data -> {
            if (data == 1) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String name = etCompanyName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String year = etEstablishedYear.getText().toString().trim();
                String tax = etTaxCode.getText().toString().trim();
                String phone = etPhoneNumber.getText().toString().trim();

                authViewModel.register(name, password, confirmPassword, email, phone, "COMPANY");
                Toast.makeText(getContext(), "OTP verified successfully", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "OTP verification failed", Toast.LENGTH_SHORT).show();
        });
        authViewModel.getVerifyOtpResult().observe(getViewLifecycleOwner(), result -> {
            if (!isAdded() || getView() == null) return;
            if (result != null) {
                Log.d("ApplicantSignupFragment", "Verify otp result:" + result);
            }
        });

        authViewModel.getReSendOtpResult().observe(getViewLifecycleOwner(), result -> {
            if (!isAdded() || getView() == null) return;

            if (result != null) {
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
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
                TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)|| TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(year) || TextUtils.isEmpty(tax)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi check user exist
        authViewModel.checkUserExist(name, password, confirmPassword, email, phone, "COMPANY");
    }

    private void showOtpDialog(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.dialog_otp, null);
        builder.setView(dialogView);

        EditText etOtp = dialogView.findViewById(R.id.et_otp);
        Button btnVerify = dialogView.findViewById(R.id.btn_verify);
        Button btnResend = dialogView.findViewById(R.id.btn_resend);
        ImageButton btnBack = dialogView.findViewById(R.id.btn_back);

        AlertDialog otpDialog = builder.create();
        otpDialog.setCancelable(false);

        btnVerify.setOnClickListener(v -> {
            String otp = etOtp.getText().toString().trim();
            if (!otp.isEmpty()) {
                authViewModel.verifyOtp(email, otp);
                otpDialog.dismiss();
            } else {
                etOtp.setError("Please enter OTP");
            }
        });

        btnResend.setOnClickListener(v -> {
            authViewModel.reSendOtp(email);
        });

        btnBack.setOnClickListener(v -> {
            otpDialog.dismiss();
        });

        otpDialog.show();
    }
}