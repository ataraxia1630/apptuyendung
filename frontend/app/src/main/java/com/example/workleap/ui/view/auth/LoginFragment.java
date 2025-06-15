package com.example.workleap.ui.view.auth;

import android.content.Intent;
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
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.AuthViewModel;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private EditText etUsername, etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignup;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());

        etUsername = view.findViewById(R.id.username_edittext);
        //etEmail = view.findViewById(R.id.email_edittext);
        etPassword = view.findViewById(R.id.password_edittext);
        btnLogin = view.findViewById(R.id.login_button);
        tvSignup = view.findViewById(R.id.signup_textview);

        btnLogin.setOnClickListener(v -> loginWithEmail());

        tvSignup.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.roleSelectionFragment);
        });

        authViewModel.getLoginResult().observe(getViewLifecycleOwner(), result ->
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show()
        );

        authViewModel.getLoginUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Log.e("LoginFragment", "user null");
            } else {
                Intent intent = new Intent(requireActivity(), NavigationActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    private void loginWithEmail() {

        String userName = etUsername.getText().toString().trim();
        //String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.login( userName, null, password);
    }
}