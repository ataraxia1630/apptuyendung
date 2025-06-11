package com.example.workleap.ui.view.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.workleap.R;

public class LoginSignupFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle b) {
        View v = inf.inflate(R.layout.fragment_login_signup, c, false);
        NavController nav = NavHostFragment.findNavController(this);

        Button btnLogin = v.findViewById(R.id.buttonLogin);
        Button btnSignup = v.findViewById(R.id.buttonSignup);

        btnLogin.setOnClickListener(x ->
                nav.navigate(R.id.loginFragment)
        );
        btnSignup.setOnClickListener(x ->
                nav.navigate(R.id.roleSelectionFragment)
        );
        return v;
    }
}
