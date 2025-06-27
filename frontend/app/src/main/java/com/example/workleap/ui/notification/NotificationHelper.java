package com.example.workleap.ui.notification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.workleap.R;
import com.example.workleap.ui.view.main.NavigationActivity;

import java.util.Random;

public class NotificationHelper {

    public static void showNotification(Context context, String title, String message, String type) {
        // Nếu chưa có quyền, không hiển thị
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.w("NotificationHelper", "Chưa có quyền POST_NOTIFICATIONS, bỏ qua notify()");
                return;
            }
        }

        Intent intent;
        if ("both".equals(type)) {
            intent = new Intent(context, NavigationActivity.class);
            //intent.putExtra("jobId", id);
        } else {
            intent = new Intent(context, NavigationActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Tạo kênh nếu cần (API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "my_channel",
                    "Thông báo chung",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(new Random().nextInt(), builder.build());
    }
}


