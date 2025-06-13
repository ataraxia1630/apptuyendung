package com.example.workleap.ui.view.auth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.repository.PreferencesManager;
import com.example.workleap.ui.notification.MyFirebaseMessagingService;
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private UserViewModel userViewModel;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //quyen thong bao
        requestNotificationPermissionIfNeeded();

        //gui fcm_token cho supabase
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String fcmToken = task.getResult();
                        Log.e("MainActivity", "FCM Token: " + fcmToken);

                        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
                        PreferencesManager preferencesManager = new PreferencesManager(this);
                        preferencesManager.saveFcmToken(fcmToken);
                        myFirebaseMessagingService.sendTokenToSupabase(this); // Truyền context ở đây
                    } else {
                        Log.e("MainActivity", "FCM Không lấy được token", task.getException());
                    }
                });;

        //Check token
        PreferencesManager preferencesManager = new PreferencesManager(this);
        if (preferencesManager.isTokenValid()) {
            String userId = preferencesManager.getUserId();
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.InitiateRepository(this);
            String token = preferencesManager.getToken();
            Log.e("hi", String.valueOf(token));
            Log.e("userId", String.valueOf(userId));

            userViewModel.getGetUserResult().observe(this, res -> {
                if(res != null)
                Log.e("getUserResult", res);
                else
                    Log.e("result", "no result");
            });

            userViewModel.getGetUserData().observe(this, user -> {
                if (user == null) {
                    Log.e("AutoLogin", "user null");
                } else {
                    Intent intent = new Intent(this, NavigationActivity.class);
                    Log.e("Welcome", "wellcome");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            });
            userViewModel.getUser(userId);
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        //get token to test firebase
        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.e("FCM", "Token: " + token);
                        // Gửi về server tương tự
                    }
                });*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, " Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 trở lên
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Yêu cầu quyền
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }
    }
}