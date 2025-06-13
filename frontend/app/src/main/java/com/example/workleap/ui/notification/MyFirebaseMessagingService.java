package com.example.workleap.ui.notification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workleap.BuildConfig;
import com.example.workleap.data.repository.PreferencesManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String urlSupabaseUserToken = "https://epuxazakjgtmjuhuwkza.supabase.co/rest/v1/user_tokens";
    private String key = BuildConfig.SUPABASE_ANON_KEY;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("MyFirebase", "FCM Token: " + token);

        // Luu token vao SharedPreferences
        /*PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());
        preferencesManager.saveFcmToken(token);*/

        //sendTokenToSupabase();
    }

    public void sendTokenToSupabase(Context context) {
        PreferencesManager prefs = new PreferencesManager(context);

        String userId = prefs.getUserId();
        String token = prefs.getFcmToken();
        String jwt = prefs.getToken();
        Log.e("MyFirebase", "jwt: "+ jwt);

        if (userId == null || token == null || jwt == null) {
            if (userId == null) {
                Log.e("MyFirebase", "Thiếu userId (null)");
            }
            if (token == null) {
                Log.e("MyFirebase", "Thiếu token (null)");
            }
            if (jwt == null) {
                Log.e("MyFirebase", "Thiếu JWT (null)");
            }
            return;
        }


        JSONObject json = new JSONObject();
        try {
            json.put("user_id", userId);
            json.put("fcm_token", token);

            Log.d("SUPABASE_JSON_BODY", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                urlSupabaseUserToken,
                json,
                response -> Log.d("MyFirebase", "Supabase Token saved"),
                error -> {
                    if (error.networkResponse != null) {
                        Log.e("Supabase", "Error code: " + error.networkResponse.statusCode);
                        Log.e("Supabase", "Error body: " + new String(error.networkResponse.data));
                    } else {
                        Log.e("Supabase", "Unknown error: " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", key);
                headers.put("Authorization", "Bearer " + jwt);
                headers.put("Content-Type", "application/json");
                headers.put("Prefer", "resolution=merge-duplicates");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(request);
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
