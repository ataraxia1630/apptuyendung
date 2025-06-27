package com.example.workleap.ui.notification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import com.example.workleap.BuildConfig;
import com.example.workleap.data.repository.PreferencesManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("MyFirebase", "FCM Token: " + token);

        // Luu token vao SharedPreferences
        /*PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());
        preferencesManager.saveFcmToken(token);*/

        //sendTokenToSupabase();
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = "Thông báo";
        String message = "Bạn có thông báo mới";
        String type = "type";

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
        } else if (remoteMessage.getData().size() > 0) {
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            type = remoteMessage.getData().get("type");
        }

        NotificationHelper.showNotification(this, title, message, type);
    }
}
