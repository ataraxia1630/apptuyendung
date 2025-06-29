package com.example.workleap.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;

public class ToastUtil {

    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_WARNING = 3;

    public static void showToast(Context context, String message, int type) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView text = layout.findViewById(R.id.toast_text);
        ImageView icon = layout.findViewById(R.id.toast_icon);

        text.setText(message);

        // Chọn icon và màu nền dựa theo loại
        int backgroundColor;
        int iconRes;
        switch (type) {
            case TYPE_SUCCESS:
                iconRes = R.drawable.ic_success; // Bạn cần thêm icon phù hợp
               // backgroundColor = Color.parseColor("#4CAF50"); // Màu xanh lá
                break;
            case TYPE_ERROR:
                iconRes = R.drawable.ic_cancel; // Icon lỗi
                backgroundColor = Color.parseColor("#F44336"); // Màu đỏ
                break;
            case TYPE_WARNING:
                iconRes = R.drawable.ic_bell; // Icon cảnh báo
                //backgroundColor = Color.parseColor("#FF9800"); // Màu cam
                break;
            default:
                iconRes = R.drawable.ic_edit_pen; // Mặc định
                //backgroundColor = Color.DKGRAY;
                break;
        }

        icon.setImageResource(iconRes);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 120);
        toast.show();
    }
}
