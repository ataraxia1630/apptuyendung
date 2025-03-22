package com.example.workleap;

import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Log (chỉ chạy 1 lần khi app khởi động)
        Log.d("App", "Ứng dụng đã khởi chạy!");
    }
}
