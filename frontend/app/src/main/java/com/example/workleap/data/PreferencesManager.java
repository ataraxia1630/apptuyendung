package com.example.workleap.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "WorkLeapPrefs";
    private final SharedPreferences prefs;

    public PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Lưu token và thời gian
    public void saveToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("auth_token", token);
        editor.putLong("token_timestamp", System.currentTimeMillis());
        editor.apply();
    }

    // Lấy token
    public String getToken() {
        return prefs.getString("auth_token", null);
    }

    // Xóa phiên đăng nhập
    public void clearSession(String applicantId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("auth_token");
        editor.remove("token_timestamp");
        editor.remove("skills_cache_" + applicantId);
        editor.apply();
    }

    // Kiểm tra token có tồn tại
    public boolean isTokenValid() {
        String token = getToken();
        if (token == null) return false;

        long timestamp = prefs.getLong("token_timestamp", 0);
        long currentTime = System.currentTimeMillis();
        long tokenAge = currentTime - timestamp;
        long maxAge = 7 * 24 * 60 * 60 * 1000; // 7 ngày

        return tokenAge <= maxAge;
    }
}