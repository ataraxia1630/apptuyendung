package com.example.workleap.ui.view.auth;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;


import com.example.workleap.R;
import com.example.workleap.ui.view.main.profile.EditProfileDialogFragment;
import com.example.workleap.ui.viewmodel.AuthViewModel;

public class ApplicantSignupFragment extends Fragment {

    private AuthViewModel authViewModel;

    private EditText etUserName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    private Button btnSignUp;
    private TextView tvLogIn;

    EditProfileDialogFragment dialog;
    private boolean isOtpDialogShown = false;


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
        authViewModel.InitiateRepository(getContext());

        etUserName = view.findViewById(R.id.editTextUserName);
        etEmail = view.findViewById(R.id.editTextEmail);
        etPassword = view.findViewById(R.id.editTextPassword);
        etConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        etPhoneNumber = view.findViewById(R.id.editTextPhone);
        btnSignUp = view.findViewById(R.id.buttonSignUp);
        tvLogIn = view.findViewById(R.id.textViewLogIn);

        btnSignUp.setOnClickListener(v -> signup());

        tvLogIn.setOnClickListener(v -> {
            NavHostFragment.findNavController(ApplicantSignupFragment.this).navigate(R.id.loginFragment);
        });

        authViewModel.getRegisterResult().observe(getViewLifecycleOwner(), result -> {
            Log.e("fffffff", "mmmmm");
            if (!isAdded() || getView() == null) return;
            if(result==null)
            {
                Log.e("ApplicantSignupFrag", "getRegisterResult NULL");
                return;
            }

            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            if (result.contains("successfully")) {
                Log.d("ApplicantSignupFragment", "Register success");
                NavHostFragment.findNavController(ApplicantSignupFragment.this).navigate(R.id.loginFragment);

                authViewModel.ResetRegisterResult();
            }
            else
            {
                Log.e("ApplicantSignupFragment", "getRegisterResult "+ result);
            }
        });

        // Observe
        authViewModel.getCheckExistData().observe(getViewLifecycleOwner(), data -> {
            if(data==null) return;

            if (data == 1) {
                String email = etEmail.getText().toString().trim();
                authViewModel.sendOtp(email);

                authViewModel.ResetGetCheckExistData();
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
            if (!isAdded() || getView() == null || isOtpDialogShown || result==null) return;

            if (result != null) {
                String email = etEmail.getText().toString().trim();
                isOtpDialogShown = true; // Đánh dấu đã hiển thị
                showOtpDialog(email);

                authViewModel.ResetSendOtpResult();
            }
        });

        authViewModel.getVerifyOtpData().observe(getViewLifecycleOwner(), data -> {
            if(data==null)
            {
                Log.e("ApplicantSignupFragment", "getVerifyOtpData NULL");
                return;
            }

            if (data == 1) {
                String fullName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhoneNumber.getText().toString().trim();

                authViewModel.register(fullName, password, confirmPassword, email, phone, "APPLICANT");
                Toast.makeText(getContext(), "OTP verified successfully", Toast.LENGTH_SHORT).show();
                authViewModel.ResetVerifyOtpData();
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
        String userName = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userName.length() < 6) {
            Toast.makeText(getContext(), "Username must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userName.length() > 30) { // khớp với Joi
            Toast.makeText(getContext(), "Username must be at most 30 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email must be a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(getContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() > 30) {
            Toast.makeText(getContext(), "Password must be at most 30 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.isEmpty()) {
            String vietnamPhoneRegex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$";
            if (!phone.matches(vietnamPhoneRegex)) {
                Toast.makeText(getContext(), "Phone Number must be a valid 10-digit number", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        authViewModel.checkUserExist(userName, password, confirmPassword, email, phone, "APPLICANT");
    }

    private void showOtpDialog(String email) {
        Log.e("applicantsignup ","showdialog");
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
            isOtpDialogShown = false;
        });

        otpDialog.show();
    }
}
