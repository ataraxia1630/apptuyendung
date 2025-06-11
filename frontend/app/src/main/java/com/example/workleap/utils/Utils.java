package com.example.workleap.utils;

import android.widget.EditText;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    // Chuyển EditText sang BigDecimal
    public static BigDecimal getDecimal(EditText editText) {
        try {
            return new BigDecimal(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    // Chuyển EditText sang int
    public static int getInt(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Chuyển EditText sang Date (định dạng mặc định: "dd/MM/yyyy")
    public static Date getDate(EditText editText) {
        String input = editText.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return sdf.parse(input);
        } catch (ParseException e) {
            return null; // hoặc trả về new Date() nếu muốn tránh null
        }
    }

    // Có thể thêm hàm getDate với định dạng tùy chọn
    public static Date getDate(EditText editText, String pattern) {
        String input = editText.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return sdf.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    //Chuyen string timestamp ve string date
    public static String formatDate(String timestampStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = inputFormat.parse(timestampStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
